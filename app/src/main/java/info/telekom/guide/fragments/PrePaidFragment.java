package info.telekom.guide.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.telekom.guide.R;

/**
 * Created by patrik on 2016.11.06..
 */
public class PrePaidFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_prepaid, container, false);
        return rootView;
    }
}
