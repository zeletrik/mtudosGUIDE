package hu.zelena.guide;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hu.zelena.guide.fragments.ComBrandFragment;
import hu.zelena.guide.util.ActivityHelper;


/**
 * Created by patrik on 2017.02.24..
 */

public class SearchBrandActivity extends ActionBarActivity {

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
        currentBrand = mainBundle.getString("brand");

        Bundle bundle = new Bundle();
        bundle.putString("name", name);

        Fragment fragment = new ComBrandFragment();
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

    public void selectBrand(View view) {
        brand = view.getTag().toString();
        Log.d("selectBrand:", brand);
        Intent i = new Intent(this, SearchDeviceActivity.class);
        i.putExtra("name", name);
        i.putExtra("device", device);
        i.putExtra("currentBrand", currentBrand);
        i.putExtra("brand", brand);
        startActivity(i);
    }
}
