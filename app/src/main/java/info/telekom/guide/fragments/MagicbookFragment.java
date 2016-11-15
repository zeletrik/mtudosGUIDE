package info.telekom.guide.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.telekom.guide.R;

/**
 * Created by patrik on 2016.11.08..
 */

public class MagicbookFragment extends Fragment {

    public MagicbookFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_magicbook, container, false);

        return rootView;
    }
}
