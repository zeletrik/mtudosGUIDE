package hu.zelena.guide;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import hu.zelena.guide.fragments.WatchSpecFragment;
import hu.zelena.guide.util.ActivityHelper;

/**
 * Created by patrik on 2017.02.19..
 */

public class WatchAvtivity extends AppCompatActivity {

    String name;

    public WatchAvtivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        ActivityHelper.initialize(this);
        Bundle mainBundle = getIntent().getExtras();
        Boolean isDark = mainBundle.getBoolean("darkMode");

        if (isDark) {
            setTheme(R.style.Main2DarkTheme);
        }

        PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("darkMode", false)) {
            setTheme(R.style.Main2DarkTheme);
        }

        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new WatchSpecFragment();

        mainBundle = getIntent().getExtras();
        name = mainBundle.getString("watch");

        Bundle bundle = new Bundle();
        bundle.putString("watch", name);


        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
