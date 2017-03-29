package hu.zelena.guide;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import hu.zelena.guide.fragments.SpecsFragment;
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

public class SpecsAvtivity extends AppCompatActivity {

    String brand;
    String phone;

    public SpecsAvtivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        ActivityHelper.initialize(this);

        Boolean isDark = ActivityHelper.darkMode(this);

        if (isDark) {
            setTheme(R.style.Main2DarkTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_specs);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new SpecsFragment();

        Bundle mainBundle = getIntent().getExtras();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("Orientation: ", "landscape");

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("Orientation: ", "portrait");

        }
    }
}