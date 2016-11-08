package info.telekom.guide;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by patrik on 2016.10.09..
 */

public class ChangeLogFragment extends Fragment{

    public ChangeLogFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_changelog, container, false);

        return rootView;
    }
}
