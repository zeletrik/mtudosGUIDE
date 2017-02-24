package hu.zelena.guide.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hu.zelena.guide.CompareActivity;
import hu.zelena.guide.ErrorActivity;
import hu.zelena.guide.MainActivity;
import hu.zelena.guide.R;
import hu.zelena.guide.SpecsAvtivity;
import hu.zelena.guide.adapter_sax.PhonesAdapter;
import hu.zelena.guide.modell.PhonesModell;
import hu.zelena.guide.util.PhoneListReader;

/**
 * Created by patrik on 2017.02.24..
 */

public class ComDeviceFragment extends Fragment {

    PhonesAdapter adapter;
    PhoneListReader phoneListReader;
    String path;
    List<PhonesModell> aList = new ArrayList<PhonesModell>();
    private String brand;
    private boolean offline;
    private ListView mList;
    private String currentBrand;
    private String currentPhone;

    public ComDeviceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_searchphone, container, false);
        mList = (ListView) rootView.findViewById(R.id.list);

        Bundle bundle = this.getArguments();
        brand = bundle.getString("brand");
        currentBrand = bundle.getString("currentBrand");
        currentPhone = bundle.getString("currentPhone");

        String name = bundle.getString("name");

        path = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/phones/" + brand + "/phones.xml";

        new GetPhoneList().execute(path);


        TextView current = (TextView) rootView.findViewById(R.id.currentDevice);
        current.setText(name);

        return rootView;
    }


    private class GetPhoneList extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                phoneListReader = new PhoneListReader(params[0], offline);
                aList = phoneListReader.getItems();
            } catch (Exception e) {
                Intent i = new Intent(getActivity(), ErrorActivity.class);
                i.putExtra("error", "Feldolgozási hiba: " + e.getMessage());
                startActivity(i);
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (getActivity() != null) {
                adapter = new PhonesAdapter(getActivity(), R.layout.phone_list_item, aList);
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
                mList.setAdapter(adapter);

                AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        PhonesModell data = aList.get(arg2);
                        //  String phone = String.valueOf(arg2 + 1);
                        String phone = aList.get(arg2).getName();

                        Intent i = new Intent(getActivity(), CompareActivity.class);
                        i.putExtra("brand", brand);
                        i.putExtra("phone", phone);
                        i.putExtra("currentBrand", currentBrand);
                        i.putExtra("currentPhone", currentPhone);
                        startActivity(i);
                    }
                };
                mList.setOnItemClickListener(onItemClickListener);
            }
        }
    }
}