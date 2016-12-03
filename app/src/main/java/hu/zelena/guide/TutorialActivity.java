package hu.zelena.guide;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

import hu.zelena.guide.util.ActivityHelper;

/**
 * Created by patrik on 2016.10.11..
 */

public class TutorialActivity extends AppIntro2 {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        ActivityHelper.initialize(this);
        super.onCreate(savedInstanceState);


        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest
        addSlide(AppIntroFragment.newInstance("MOBILTUDÓS GUIDE", "Gyors válaszok, hogy ne a neted bújd a válaszokat keresve", R.drawable.splash, Color.parseColor("#f51e97")));
        addSlide(AppIntroFragment.newInstance("Specifikációk", "Az értékesített készülékek alap specifikációja, hogy midnig kéznél legyen", R.drawable.tut_spec, Color.MAGENTA));
        addSlide(AppIntroFragment.newInstance("Data kímélő mód", "Ha kevés a net, kapcsold be, hogy ne töltse le a képeket így spórolhatsz az adatforgalommal", R.drawable.tut_datasave, Color.parseColor("#8e24aa")));
        addSlide(AppIntroFragment.newInstance("Offline mód", "Ha elszakadnál a világhálótól", R.drawable.tut_offline, Color.parseColor("#8bc34a")));
        addSlide(AppIntroFragment.newInstance("INTRANET", "Ne feledd, bizonyos funkicók eléréséhez szükség van VPN kapcsolatra!", R.drawable.tut_vpn, Color.parseColor("#FF5722")));
        addSlide(AppIntroFragment.newInstance("MOBILTUDÓS GUIDE", "Jó munkát, kedves ügyfeleket!", R.drawable.splash, Color.parseColor("#f51e97")));

        // OPTIONAL METHODS

        // SHOW or HIDE the statusbar
        showStatusBar(true);

        // Edit the color of the nav bar on Lollipop+ devices
        // setNavBarColor(Color.MAGENTA);

        // Turn vibration on and set intensity
        // NOTE: you will need to ask VIBRATE permission in Manifest if you haven't already
        setVibrate(true);
        setVibrateIntensity(30);

        // Animations -- use only one of the below. Using both could cause errors.
        setFadeAnimation(); // OR
     /*   setZoomAnimation(); // OR
        setFlowAnimation(); // OR
        setSlideOverAnimation(); // OR
        setDepthAnimation(); // OR
        setCustomTransformer(yourCustomTransformer);*/

        // Permissions -- takes a permission and slide number
        //  askForPermissions(new String[]{Manifest.permission.CAMERA}, 2);
    }

    @Override
    public void onNextPressed() {
        //   super.onNextPressed(oldFragment, newFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        super.finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onSkipPressed() {
        super.finish();
    }
}