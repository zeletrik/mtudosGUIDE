package hu.zelena.guide.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.zelena.guide.R;

/**
 * Created by patrik on 2016.11.27..
 */

public class ErrorFragment extends Fragment {

        public ErrorFragment(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

            Bundle bundle = this.getArguments();
            String msg = bundle.getString("error");

        View rootView = inflater.inflate(R.layout.fragment_error, container, false);

          TextView error =  (TextView) rootView.findViewById(R.id.errorDetails);
            error.setText(msg);

        return rootView;
    }
    }