package hu.zelena.guide;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import hu.zelena.guide.fragments.SettingsFragment;
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


public class UserSettingsActivity extends ActionBarActivity {

    private boolean darkMode = false;
    private boolean writeStorage = true;
    private boolean tabletMode = false;

    public UserSettingsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle = getIntent().getExtras();
        Boolean isDark = mainBundle.getBoolean("darkMode");
        writeStorage = mainBundle.getBoolean("writeStorage");

        Bundle bundle = new Bundle();
        bundle.putBoolean("writeStorage", writeStorage);

        if (isDark) {
            setTheme(R.style.SettingsDarkTheme);
            darkMode = true;
        }

        tabletMode = mainBundle.getBoolean("tabMode");
        ActivityHelper.initialize(this);

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_settings);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content
        Fragment fragment = new SettingsFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


}
