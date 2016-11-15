package info.telekom.guide;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import info.telekom.guide.fragments.SpecsFragment;
import info.telekom.guide.fragments.UssdFragment;

/**
 * Created by patrik on 2016.11.15..
 */

public class UssdCodeActivity extends ActionBarActivity {

    private boolean tabletMode =false;

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

        Fragment fragment = new UssdFragment();

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

    public void ussdCode(View view){
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("URL","http://magicbook.telekom.intra/mb/tmobile/keszulekek/gsmkodok/lcc/ussd.html");
        i.putExtra("title","USSD Kódok");
        i.putExtra("tabMode",tabletMode);
        startActivity(i);
    }

    public void gsmCode(View view){
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("URL","http://magicbook.telekom.intra/mb/tmobile/keszulekek/gsmkodok/gsmkod.html");
        i.putExtra("title","GSM Kódok");
        i.putExtra("tabMode",tabletMode);
        startActivity(i);
    }
 }