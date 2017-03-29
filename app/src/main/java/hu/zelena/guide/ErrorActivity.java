package hu.zelena.guide;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import hu.zelena.guide.fragments.ErrorFragment;
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

public class ErrorActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle = getIntent().getExtras();
        String msg = mainBundle.getString("error");
        Boolean isDark = ActivityHelper.darkMode(this);

        Bundle bundle = new Bundle();
        bundle.putString("error", msg);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (isDark) {
            setTheme(R.style.Main2DarkTheme);
        }

        ActivityHelper.initialize(this);

        super.onCreate(savedInstanceState);

        Fragment fragment = new ErrorFragment();
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
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }
}
