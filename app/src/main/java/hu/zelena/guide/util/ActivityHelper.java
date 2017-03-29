package hu.zelena.guide.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

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
public class ActivityHelper {


    public static void initialize(Activity activity) {

        if (tabMode(activity)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            // kijelző DP meghatározás
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);

        if (smallestWidth > 720) {
            // Ha több mint 720 DP (~Tablet) akkor landscape
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            // Különben portrait
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        }
    }

    public static boolean darkMode(Activity activity) {
        boolean isDark = false;
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity);
        if (sharedPrefs.getBoolean("darkMode", false)) {
            isDark = true;
        }
        return isDark;
    }

    public static boolean tabMode(Activity activity) {
        boolean isTab = false;
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity);
        if (sharedPrefs.getBoolean("tabMode", false)) {
            isTab = true;
        }
        return isTab;
    }
}