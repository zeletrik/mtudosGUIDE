package hu.zelena.guide;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hu.zelena.guide.fragments.UssdFragment;
import hu.zelena.guide.util.ActivityHelper;
/**
 Copyright (C) <2017>  <Patrik G. Zelena>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */

public class UssdCodeActivity extends ActionBarActivity {

    private boolean tabletMode = false;

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

    public void ussdCode(View view) {
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("URL", "http://magicbook.telekom.intra/mb/tmobile/keszulekek/gsmkodok/lcc/ussd.html");
        i.putExtra("title", "USSD Kódok");
        i.putExtra("tabMode", tabletMode);
        startActivity(i);
    }

    public void gsmCode(View view) {
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("URL", "http://magicbook.telekom.intra/mb/tmobile/keszulekek/gsmkodok/gsmkod.html");
        i.putExtra("title", "GSM Kódok");
        i.putExtra("tabMode", tabletMode);
        startActivity(i);
    }
}