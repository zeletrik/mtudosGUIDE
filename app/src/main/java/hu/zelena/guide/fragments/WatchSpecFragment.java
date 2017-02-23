package hu.zelena.guide.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.zelena.guide.ErrorActivity;
import hu.zelena.guide.R;
import hu.zelena.guide.modell.WatchModell;
import hu.zelena.guide.util.WatchSpecReader;

/**
 * Created by patrik on 2017.02.19..
 */

public class WatchSpecFragment extends Fragment {


    View rootView;
    private String watch;
    private String baseURL;
    private boolean offline;

    public WatchSpecFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_watch_specs, container, false);

        Bundle bundle = this.getArguments();
        watch = bundle.getString("watch");

        getActivity().setTitle("Specifikáció");


        PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        offline = preferences.getBoolean("offline", false);

        if (offline) {
            baseURL = Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/data/offline/watch/specs/" + watch + ".xml";
        } else {
            baseURL = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/phones/watch/specs/" + watch + ".xml";
        }
        new GetWatchSpecs().execute(baseURL);

        return rootView;
    }


    private class GetWatchSpecs extends AsyncTask<String, Void, WatchModell> {
        @Override
        protected WatchModell doInBackground(String... params) {
            try {
                WatchSpecReader reader = new WatchSpecReader(baseURL, offline);
                WatchModell spec = reader.getWatchSpecs();
                return spec;
            } catch (Exception e) {
                Intent i = new Intent(getActivity(), ErrorActivity.class);
                i.putExtra("darkMode", false);
                i.putExtra("error", "SAXAsyncTask: " + e.getMessage());
                startActivity(i);
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(WatchModell specs) {

            getActivity().setTitle(specs.getName());

            TextView DisplayType = (TextView) rootView.findViewById(R.id.watchAndroidComp);
            DisplayType.setText(specs.getWatchAndroidVersion());

            TextView DisplaySize = (TextView) rootView.findViewById(R.id.watchIOSComp);
            DisplaySize.setText(specs.getWatchIosVersion());

            TextView DisplayRes = (TextView) rootView.findViewById(R.id.watchWPComp);
            DisplayRes.setText(specs.getWatchWinVersion());

            TextView DisplayProt = (TextView) rootView.findViewById(R.id.watchIPtxt);
            DisplayProt.setText(specs.getWatchIPcert());

            TextView OsSpec = (TextView) rootView.findViewById(R.id.watchIPothertxt);
            OsSpec.setText(specs.getWatchIPother());

            TextView Chipset = (TextView) rootView.findViewById(R.id.watchDesc);
            Chipset.setText(specs.getWatchDesc());

        }
    }

}