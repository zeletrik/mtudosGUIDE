package hu.zelena.guide.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import hu.zelena.guide.BuildConfig;
import hu.zelena.guide.ChangeLogActivity;
import hu.zelena.guide.ErrorActivity;
import hu.zelena.guide.R;
import hu.zelena.guide.TutorialActivity;
import hu.zelena.guide.UserSettingsActivity;
import hu.zelena.guide.modell.DbVersion;
import hu.zelena.guide.modell.Version;
import hu.zelena.guide.util.DBVersionReader;
import hu.zelena.guide.util.DeleteOfflineDir;
import hu.zelena.guide.util.DownloadActivity;

/**
 * Created by patrik on 2016.11.27..
 */

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private DbVersion  offlineVer;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_settings);

        Preference offlinePref = findPreference("offline");
        Preference saverPref = findPreference("dataSaver");
        Preference updatePref = findPreference("update");

        Bundle bundle = this.getArguments();
        Boolean write = bundle.getBoolean("writeStorage");

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        if (write) {
            if (sharedPrefs.getBoolean("offline", false)) {
                saverPref.setEnabled(false);
                offlinePref.setEnabled(true);
                updatePref.setEnabled(true);
            } else if (sharedPrefs.getBoolean("dataSaver", false)) {
                saverPref.setEnabled(true);
                offlinePref.setEnabled(false);
                updatePref.setEnabled(false);
            }
        } else {
            offlinePref.setEnabled(false);
            updatePref.setEnabled(false);
        }


        Preference fPref = findPreference("feedback");
        fPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                String version = BuildConfig.VERSION_NAME;

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"zelena.patrikgergo@ext.telekom.hu"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Mobiltudós Guide - Visszajelző");
                intent.putExtra(Intent.EXTRA_TEXT, "Verzió: " + version);
                startActivity(Intent.createChooser(intent, ""));
                return true;
            }
        });

        Preference vPref = findPreference("version");
        vPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                checkVersion();
                return true;
            }
        });

        Preference tutPref = findPreference("tutorial");
        tutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                startActivity(intent);
                return true;
            }
        });

        Preference clPref = findPreference("changelog");
        clPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {

                Intent intent = new Intent(getActivity(), ChangeLogActivity.class);
                intent.putExtra("darkMode", false);
                intent.putExtra("tabMode", false);
                startActivity(intent);
                return true;
            }
        });

        Preference offPref = findPreference("offline");
        offPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(getActivity(), DownloadActivity.class);
                startActivity(i);
                return true;
            }
        });

        Preference updPref = findPreference("update");
        updPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                new HttpRequestDBVersion().execute();
                return true;
            }
        });

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("offline")) {
            Preference pr = findPreference("update");
            Preference pref = findPreference("dataSaver");
            if (sharedPreferences.getBoolean("offline", false)) {
                pr.setEnabled(true);
                pref.setEnabled(false);
                restartApp();
            } else {
                pr.setEnabled(false);
                pref.setEnabled(true);
                restartApp();
            }
        }

        if (key.equals("dataSaver")) {
            Preference pref = findPreference("offline");

            if (sharedPreferences.getBoolean("dataSaver", false)) {
                pref.setEnabled(false);
            } else {
                pref.setEnabled(true);
            }
            restartApp();
        }

        if (key.equals("darkMode")) {
            restartApp();
        }

    }

    public void restartApp() {
        Intent i = getActivity().getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent in = getActivity().getIntent();
        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        startActivity(in);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen()
                .getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen()
                .getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private class HttpRequestVersion extends AsyncTask<Void, Void, Version> {
        @Override
        protected Version doInBackground(Void... params) {
            try {
                final String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/version/current/ver";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Version ver = restTemplate.getForObject(url, Version.class);
                return ver;
            } catch (Exception e) {
                Intent i = new Intent(getActivity(), ErrorActivity.class);
                i.putExtra("darkMode", false);
                i.putExtra("error", e.getMessage());
                startActivity(i);
                Log.e("Settings", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Version ver) {
            String verName = ver.getVersion();

            if (Integer.valueOf(verName) > Integer.valueOf(BuildConfig.VERSION_CODE)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Új verzió")
                        .setMessage("Új verzió érhető el. Frissítsünk?")
                        .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/version/current/guide.apk";
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            } else {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Legfrissebb verzió")
                        .setMessage("Az alkalmazás naprakész")
                        .setNegativeButton("Rendben", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        }
    }

    private class HttpRequestDBVersion extends AsyncTask<Void, Void, DbVersion> {
        @Override
        protected DbVersion doInBackground(Void... params) {
            try {
                final String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/version/offline/currentVer";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                DbVersion currentVer = restTemplate.getForObject(url, DbVersion.class);

                DBVersionReader dbRead = new DBVersionReader(Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/version/ver.xml");
                offlineVer= dbRead.getDBVer();

                return currentVer;
            } catch (Exception e) {
                Intent i = new Intent(getActivity(), ErrorActivity.class);
                i.putExtra("darkMode", false);
                i.putExtra("error", e.getMessage());
                startActivity(i);
                Log.e("Settings", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(DbVersion currVer) {
            String curverName = currVer.getVersion();
            String offlineVerName = offlineVer.getVersion();

            if (Integer.valueOf(curverName) > Integer.valueOf(offlineVerName)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Új verzió")
                        .setMessage("Új adatbázis verzió érhető el. Frissítsünk?")
                        .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                new DeleteOfflineDir(Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide");
                                Intent i = new Intent(getActivity(), DownloadActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            } else {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Legfrissebb adatbázis")
                        .setMessage("Az alkalmazás naprakész")
                        .setNegativeButton("Rendben", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
            }
        }
    }

    public void checkVersion() {

        if (checkInternet() == true) {
            new HttpRequestVersion().execute();
        } else {

            new AlertDialog.Builder(getActivity())
                    .setTitle("Nincs elérhető hálózat")
                    .setMessage("Internet elérés nélkül nem lehetséges ellenőrizni a frissítéseket")
                    .setNegativeButton("Rendben", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        }

    }

    public boolean checkInternet() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
