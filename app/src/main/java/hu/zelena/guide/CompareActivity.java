package hu.zelena.guide;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import hu.zelena.guide.fragments.CompareFragment;
import hu.zelena.guide.util.ActivityHelper;

/**
 * Created by patrik on 2017.02.24..
 */

public class CompareActivity extends AppCompatActivity {

    String brand;
    String phone;
    String currentBrand;
    String currentPhone;

    public CompareActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Boolean isDark = ActivityHelper.darkMode(this);

        if (isDark) {
            setTheme(R.style.Main2DarkTheme);
        }

        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new CompareFragment();

        Bundle mainBundle = getIntent().getExtras();
        brand = mainBundle.getString("brand");
        phone = mainBundle.getString("phone");
        currentBrand = mainBundle.getString("currentBrand");
        currentPhone = mainBundle.getString("currentPhone");

        Bundle bundle = new Bundle();
        bundle.putString("brand", brand);
        bundle.putString("phone", phone);
        bundle.putString("currentBrand", currentBrand);
        bundle.putString("currentPhone", currentPhone);

        Log.d("Extra: " + brand + phone + currentBrand + currentPhone, "!");


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
                Intent i = new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }
}