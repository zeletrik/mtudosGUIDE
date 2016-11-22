package hu.zelena.guide.fragments;

/**
 * Created by patrik on 2016.11.07..
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.zelena.guide.R;

public class TabletFragment extends Fragment {

    public TabletFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tablet, container, false);

        return rootView;
    }
}