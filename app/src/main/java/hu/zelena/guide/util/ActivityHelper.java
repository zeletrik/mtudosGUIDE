package hu.zelena.guide.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import hu.zelena.guide.R;

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
public class ActivityHelper {

    public static void initialize(Activity activity) {
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
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
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
}