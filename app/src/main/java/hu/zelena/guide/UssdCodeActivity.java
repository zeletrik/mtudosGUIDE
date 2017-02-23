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
 Copyright Patrik G. Zelena

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

public class UssdCodeActivity extends ActionBarActivity {

    private boolean tabletMode = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle = getIntent().getExtras();
        Boolean isDark = mainBundle.getBoolean("darkMode");

        ActivityHelper.initialize(this);

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