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
        addSlide(AppIntroFragment.newInstance(" Title 1", "description 1", R.drawable.ic_launcher, Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance(" Title 2", "description 2", R.drawable.ic_launcher, Color.MAGENTA));

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
    public void  onSkipPressed(){
        super.finish();
    }
}