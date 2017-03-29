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


public class UserSettingsActivity extends ActionBarActivity {

    private boolean writeStorage = true;
    public UserSettingsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle = getIntent().getExtras();
        Bundle bundle = new Bundle();
        writeStorage = mainBundle.getBoolean("writeStorage");
        bundle.putBoolean("writeStorage", writeStorage);

        Boolean isDark = ActivityHelper.darkMode(this);
        if (isDark) {
            setTheme(R.style.SettingsDarkTheme);
        }

        ActivityHelper.initialize(this);

        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


}
