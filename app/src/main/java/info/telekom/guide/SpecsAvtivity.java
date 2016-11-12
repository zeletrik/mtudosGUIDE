package info.telekom.guide;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by patrik on 2016.11.04..
 */

public class SpecsAvtivity extends ActionBarActivity {

    View rootView;
    String brand;
    String phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle  = getIntent().getExtras();
        Boolean isDark = mainBundle.getBoolean("darkMode");

        boolean tabletMode = mainBundle.getBoolean("tabMode");

        if (tabletMode) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if(isDark){
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