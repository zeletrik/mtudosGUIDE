package hu.zelena.guide.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.zelena.guide.R;

/**
 * Created by patrik on 2016.10.24..
 */

public class WatchFragment extends Fragment {

    public WatchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_watch, container, false);

        return rootView;
    }
}
