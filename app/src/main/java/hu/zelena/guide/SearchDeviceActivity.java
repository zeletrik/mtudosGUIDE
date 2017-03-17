package hu.zelena.guide;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hu.zelena.guide.fragments.ComDeviceFragment;
import hu.zelena.guide.util.ActivityHelper;

/**
 * Created by patrik on 2017.02.24..
 */

public class SearchDeviceActivity extends ActionBarActivity {

    private String name;
    private String device;
    private String currentBrand;
    private String brand;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        ActivityHelper.initialize(this);
        Boolean isDark = ActivityHelper.darkMode(this);
        if (isDark) {
            setTheme(R.style.Main2DarkTheme);
        }

        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle mainBundle = getIntent().getExtras();
        name = mainBundle.getString("name");

        device = mainBundle.getString("device");
        currentBrand = mainBundle.getString("currentBrand");
        brand = mainBundle.getString("brand");

        Bundle bundle = new Bundle();
        bundle.putString("brand", brand);
        bundle.putString("name", name);
        bundle.putString("currentBrand", currentBrand);
        bundle.putString("currentPhone", device);

        Fragment fragment = new ComDeviceFragment();
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

    @Override
    public void onBackPressed() {
        finish();
    }

}