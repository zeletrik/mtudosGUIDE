package hu.zelena.guide.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;

/**
 * Created by patrik on 2016.11.27..
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
}