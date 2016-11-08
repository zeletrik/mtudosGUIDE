package info.telekom.guide;

/**
 * Created by patrik on 2016.02.13..
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SpecsFragment extends Fragment {

    private String brand;
    private String phone;

    public SpecsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_specs, container, false);

        Bundle bundle = this.getArguments();
        brand = bundle.getString("brand");
        phone = bundle.getString("phone");

        getActivity().setTitle(brand +"/"+phone);

        return rootView;
    }

}
