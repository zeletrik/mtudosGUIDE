package hu.zelena.guide.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by patrik on 2017.03.29..
 */

public class MyScrollView extends ScrollView {

    private static final String TAG = "MyScrollView";
    private Runnable scrollerTask;
    private int initialPosition;
    private int newCheck = 100;
    private OnScrollStoppedListener onScrollStoppedListener;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        scrollerTask = new Runnable() {

            public void run() {

                int newPosition = getScrollY();
                if (initialPosition - newPosition == 0) {//has stopped

                    if (onScrollStoppedListener != null) {

                        onScrollStoppedListener.onScrollStopped();
                    }
                } else {
                    initialPosition = getScrollY();
                    MyScrollView.this.postDelayed(scrollerTask, newCheck);
                }
            }
        };
    }

    public void setOnScrollStoppedListener(MyScrollView.OnScrollStoppedListener listener) {
        onScrollStoppedListener = listener;
    }

    public void startScrollerTask() {

        initialPosition = getScrollY();
        MyScrollView.this.postDelayed(scrollerTask, newCheck);
    }

    public interface OnScrollStoppedListener {
        void onScrollStopped();
    }

}