package hu.zelena.guide;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import hu.zelena.guide.fragments.SpecsFragment;
import hu.zelena.guide.util.ActivityHelper;

/**
 * Created by patrik on 2016.11.04..
 */

public class SpecsAvtivity extends ActionBarActivity {

    String brand;
    String phone;

    public SpecsAvtivity(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {


        ActivityHelper.initialize(this);
        Bundle mainBundle  = getIntent().getExtras();
        Boolean isDark = mainBundle.getBoolean("darkMode");

        if(isDark){
            setTheme(R.style.SpecDarkTheme);
        }

        PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean("darkMode",false)){
            setTheme(R.style.SpecDarkTheme);
        }

        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new SpecsFragment();

        mainBundle  = getIntent().getExtras();
        brand = mainBundle.getString("brand");
        phone = mainBundle.getString("phone");

        Bundle bundle = new Bundle();
        bundle.putString("brand", brand);
        bundle.putString("phone", phone);



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