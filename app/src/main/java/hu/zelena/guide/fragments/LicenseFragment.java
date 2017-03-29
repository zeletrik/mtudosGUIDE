package hu.zelena.guide.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.zelena.guide.R;

/**
 * Created by patrik on 2017.03.29..
 */

public class LicenseFragment extends Fragment {

    public LicenseFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.license_layout, container, false);

        return rootView;
    }
}
