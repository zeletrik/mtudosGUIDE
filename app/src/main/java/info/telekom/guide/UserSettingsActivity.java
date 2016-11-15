package info.telekom.guide;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import info.telekom.guide.rest_modell.Version;

/**
 * Created by patrik on 2016.11.03..
 */


public class UserSettingsActivity  extends ActionBarActivity {

    private int saverMode = 0;
    private  int offline = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle  = getIntent().getExtras();
        Boolean isDark = mainBundle.getBoolean("darkMode");

        if(isDark){
            setTheme(R.style.SettingsDarkTheme);
        }

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_settings);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public class SettingsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            addPreferencesFromResource(R.xml.user_settings);

            Preference offlinePref = findPreference("offline");
            Preference saverPref = findPreference("dataSaver");
            Preference updatePref = findPreference("update");

            SharedPreferences sharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(UserSettingsActivity.this);

            if(sharedPrefs.getBoolean("offline",false)){
                saverPref.setEnabled(false);
                offlinePref.setEnabled(true);
                updatePref.setEnabled(true);
            }else if(sharedPrefs.getBoolean("dataSaver",false)){
                saverPref.setEnabled(true);
                offlinePref.setEnabled(false);
                updatePref.setEnabled(false);
            }


            Preference myPref = findPreference("feedback");
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
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



        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if (key.equals("offline")) {
                Preference pr = findPreference("update");
                Preference pref = findPreference("dataSaver");

               if(sharedPreferences.getBoolean("offline",false)){
                   pr.setEnabled(true);
                   pref.setEnabled(false);
               }else{
                   pr.setEnabled(false);
                   pref.setEnabled(true);
               }
            }

            if (key.equals("dataSaver")) {
                Preference pref = findPreference("offline");

                if(sharedPreferences.getBoolean("dataSaver",false)){
                    pref.setEnabled(false);
                    saverMode = 1;
                }else{
                    pref.setEnabled(true);
                    saverMode = -1;
                }
                restartApp();
            }

            if (key.equals("darkMode")) {
                restartApp();
            }

        }

        public void restartApp(){
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Intent in = getIntent();
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                    finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

        }

    private class HttpRequestVersion extends AsyncTask<Void, Void, Version> {
        @Override
        protected Version doInBackground(Void... params) {
            try {
                final String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/version/current/ver";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Version ver= restTemplate.getForObject(url, Version.class);
                return ver;
            } catch (Exception e) {
                Log.e("List Fragment", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Version ver) {
            String verName = ver.getVersion();

            if (Integer.valueOf(verName) > Integer.valueOf(BuildConfig.VERSION_CODE)){
                new AlertDialog.Builder(UserSettingsActivity.this)
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
            }else{

                new AlertDialog.Builder(UserSettingsActivity.this)
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

    private void checkVersion(){

       if (checkInternet() == true) {
            new HttpRequestVersion().execute();
        } else {

           new AlertDialog.Builder(UserSettingsActivity.this)
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

    public boolean checkInternet(){

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
