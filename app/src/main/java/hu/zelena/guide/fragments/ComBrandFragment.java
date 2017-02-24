package hu.zelena.guide.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hu.zelena.guide.R;

/**
 * Created by patrik on 2017.02.24..
 */

public class ComBrandFragment extends Fragment {

    public ComBrandFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_searchbrand, container, false);

        Bundle bundle = this.getArguments();
        String name = bundle.getString("name");

        TextView current = (TextView) rootView.findViewById(R.id.currentDevice);
        current.setText(name);

        return rootView;
    }
}
