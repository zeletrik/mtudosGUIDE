package hu.zelena.guide.fragments;

/**
 * Created by patrik on 2016.02.13..
 */

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;


import hu.zelena.guide.MainActivity;
import hu.zelena.guide.R;
import hu.zelena.guide.rest_modell.Counter;
import hu.zelena.guide.rest_modell.Info;

public class ListFragment extends Fragment {

    private String brand;
    private int index;
    private String imgURL;
    private String baseURL = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/phones/";

    View rootView;
    ImageView img;
    Bitmap bitmap;
    private int counts = -1;
    private boolean isLoaded = false;
    private boolean isLoaded2 = false;

    private boolean first = true;
    private boolean offline = false;

    public ListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();

        if (activity.checkInternet() == false) {
           rootView = inflater.inflate(R.layout.error_layout, container, false);
          activity.sendSnack("No internet connection - (MA - I01)");

           return rootView;
       }

        if (activity.getSaverMode()){
            offline = true;
        }

        Bundle bundle = this.getArguments();
        brand = bundle.getString("brand");

        rootView = inflater.inflate(R.layout.phone_list_layout, container, false);

         super.onStart();

        /**
         *  Get counter from server
         */

         new HttpRequestCounter().execute();

        if (counts < 16) {
            /**
             * Set Action Bar Title
             */
            getActivity().setTitle(brand);

            new HttpRequestInfo().execute();

            /**
             * Load Images until Counter
             */


           imgURL = baseURL + brand + "/1.jpg";
           index = 1;
           new LoadImage().execute(imgURL);

        }else{
            rootView = inflater.inflate(R.layout.error_layout, container, false);
            activity.sendSnack("Error creating Fragment (FR-01)");
            Log.e("List Fragment", "Error creating Fragment (FR-01)");
           return rootView;
       }

        return rootView;
    }

    /**
     * REST - Counter
     */
    private class HttpRequestCounter  extends AsyncTask<Void, Void, Counter> {
        @Override
        protected Counter doInBackground(Void... params) {
            try {
                final String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/phones/"+brand+"/document";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Counter count= restTemplate.getForObject(url, Counter.class);
                return count;
            } catch (Exception e) {
                Log.e("List Fragment", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Counter count) {
            counts = Integer.valueOf(count.getQuantity());

           isLoaded2 = true;
        }
    }

    /**
     * REST - Info
     */
    private class HttpRequestInfo  extends AsyncTask<Void, Void, Info> {
        @Override
        protected Info doInBackground(Void... params) {
            try {

                    final String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/phones/"+brand+"/info";
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    Info inf = restTemplate.getForObject(url, Info.class);
                    return inf;

            } catch (Exception e) {
                Log.e("List Fragment", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Info inf) {
            TextView NameText = null;
            TextView SimText = null;
            TextView OsText = null;
            TextView MaText = null;
            RelativeLayout rl = null;


            switch(counts){

                case 1:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_2);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_3);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_4);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_5);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_6);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_7);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_8);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_9);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_10);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    break;

                case 2:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_3);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_4);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_5);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_6);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_7);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_8);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_9);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_10);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    break;

                case 3:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_4);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_5);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_6);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_7);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_8);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_9);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_10);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);


                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    break;

                case 4:


                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_5);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_6);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_7);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_8);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_9);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_10);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);


                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    break;

                case 5:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_6);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_7);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_8);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_9);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_10);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);


                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    break;


                case 6:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_7);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_8);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_9);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_10);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    break;

                case 7:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_8);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_9);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_10);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    // Phone 7

                    NameText = (TextView) rootView.findViewById(R.id.name_7);
                    NameText.setText(inf.getName_6());

                    SimText = (TextView) rootView.findViewById(R.id.sim_7);
                    SimText.setText(inf.getSim_6());

                    OsText = (TextView) rootView.findViewById(R.id.os_7);
                    OsText.setText(inf.getOs_6());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_7);
                    MaText.setText(inf.getMobilarena_6());

                    break;

                case 8:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_9);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_10);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    // Phone 7

                    NameText = (TextView) rootView.findViewById(R.id.name_7);
                    NameText.setText(inf.getName_6());

                    SimText = (TextView) rootView.findViewById(R.id.sim_7);
                    SimText.setText(inf.getSim_6());

                    OsText = (TextView) rootView.findViewById(R.id.os_7);
                    OsText.setText(inf.getOs_6());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_7);
                    MaText.setText(inf.getMobilarena_6());

                    // Phone 8

                    NameText = (TextView) rootView.findViewById(R.id.name_8);
                    NameText.setText(inf.getName_7());

                    SimText = (TextView) rootView.findViewById(R.id.sim_8);
                    SimText.setText(inf.getSim_7());

                    OsText = (TextView) rootView.findViewById(R.id.os_8);
                    OsText.setText(inf.getOs_7());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_8);
                    MaText.setText(inf.getMobilarena_7());

                    break;

                case 9:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_10);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    // Phone 7

                    NameText = (TextView) rootView.findViewById(R.id.name_7);
                    NameText.setText(inf.getName_6());

                    SimText = (TextView) rootView.findViewById(R.id.sim_7);
                    SimText.setText(inf.getSim_6());

                    OsText = (TextView) rootView.findViewById(R.id.os_7);
                    OsText.setText(inf.getOs_6());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_7);
                    MaText.setText(inf.getMobilarena_6());

                    // Phone 8

                    NameText = (TextView) rootView.findViewById(R.id.name_8);
                    NameText.setText(inf.getName_7());

                    SimText = (TextView) rootView.findViewById(R.id.sim_8);
                    SimText.setText(inf.getSim_7());

                    OsText = (TextView) rootView.findViewById(R.id.os_8);
                    OsText.setText(inf.getOs_7());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_8);
                    MaText.setText(inf.getMobilarena_7());

                    // Phone 9

                    NameText = (TextView) rootView.findViewById(R.id.name_9);
                    NameText.setText(inf.getName_8());

                    SimText = (TextView) rootView.findViewById(R.id.sim_9);
                    SimText.setText(inf.getSim_8());

                    OsText = (TextView) rootView.findViewById(R.id.os_9);
                    OsText.setText(inf.getOs_8());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_9);
                    MaText.setText(inf.getMobilarena_8());

                    break;

                case 10:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_11);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    // Phone 7

                    NameText = (TextView) rootView.findViewById(R.id.name_7);
                    NameText.setText(inf.getName_6());

                    SimText = (TextView) rootView.findViewById(R.id.sim_7);
                    SimText.setText(inf.getSim_6());

                    OsText = (TextView) rootView.findViewById(R.id.os_7);
                    OsText.setText(inf.getOs_6());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_7);
                    MaText.setText(inf.getMobilarena_6());

                    // Phone 8

                    NameText = (TextView) rootView.findViewById(R.id.name_8);
                    NameText.setText(inf.getName_7());

                    SimText = (TextView) rootView.findViewById(R.id.sim_8);
                    SimText.setText(inf.getSim_7());

                    OsText = (TextView) rootView.findViewById(R.id.os_8);
                    OsText.setText(inf.getOs_7());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_8);
                    MaText.setText(inf.getMobilarena_7());

                    // Phone 9

                    NameText = (TextView) rootView.findViewById(R.id.name_9);
                    NameText.setText(inf.getName_8());

                    SimText = (TextView) rootView.findViewById(R.id.sim_9);
                    SimText.setText(inf.getSim_8());

                    OsText = (TextView) rootView.findViewById(R.id.os_9);
                    OsText.setText(inf.getOs_8());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_9);
                    MaText.setText(inf.getMobilarena_8());

                    // Phone 10

                    NameText = (TextView) rootView.findViewById(R.id.name_10);
                    NameText.setText(inf.getName_9());

                    SimText = (TextView) rootView.findViewById(R.id.sim_10);
                    SimText.setText(inf.getSim_9());

                    OsText = (TextView) rootView.findViewById(R.id.os_10);
                    OsText.setText(inf.getOs_9());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_10);
                    MaText.setText(inf.getMobilarena_9());

                    break;

                case 11:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_12);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    // Phone 7

                    NameText = (TextView) rootView.findViewById(R.id.name_7);
                    NameText.setText(inf.getName_6());

                    SimText = (TextView) rootView.findViewById(R.id.sim_7);
                    SimText.setText(inf.getSim_6());

                    OsText = (TextView) rootView.findViewById(R.id.os_7);
                    OsText.setText(inf.getOs_6());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_7);
                    MaText.setText(inf.getMobilarena_6());

                    // Phone 8

                    NameText = (TextView) rootView.findViewById(R.id.name_8);
                    NameText.setText(inf.getName_7());

                    SimText = (TextView) rootView.findViewById(R.id.sim_8);
                    SimText.setText(inf.getSim_7());

                    OsText = (TextView) rootView.findViewById(R.id.os_8);
                    OsText.setText(inf.getOs_7());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_8);
                    MaText.setText(inf.getMobilarena_7());

                    // Phone 9

                    NameText = (TextView) rootView.findViewById(R.id.name_9);
                    NameText.setText(inf.getName_8());

                    SimText = (TextView) rootView.findViewById(R.id.sim_9);
                    SimText.setText(inf.getSim_8());

                    OsText = (TextView) rootView.findViewById(R.id.os_9);
                    OsText.setText(inf.getOs_8());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_9);
                    MaText.setText(inf.getMobilarena_8());

                    // Phone 10

                    NameText = (TextView) rootView.findViewById(R.id.name_10);
                    NameText.setText(inf.getName_9());

                    SimText = (TextView) rootView.findViewById(R.id.sim_10);
                    SimText.setText(inf.getSim_9());

                    OsText = (TextView) rootView.findViewById(R.id.os_10);
                    OsText.setText(inf.getOs_9());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_10);
                    MaText.setText(inf.getMobilarena_9());

                    // Phone 11

                    NameText = (TextView) rootView.findViewById(R.id.name_11);
                    NameText.setText(inf.getName_10());

                    SimText = (TextView) rootView.findViewById(R.id.sim_11);
                    SimText.setText(inf.getSim_10());

                    OsText = (TextView) rootView.findViewById(R.id.os_11);
                    OsText.setText(inf.getOs_10());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_11);
                    MaText.setText(inf.getMobilarena_10());

                    break;

                case 12:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_13);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    // Phone 7

                    NameText = (TextView) rootView.findViewById(R.id.name_7);
                    NameText.setText(inf.getName_6());

                    SimText = (TextView) rootView.findViewById(R.id.sim_7);
                    SimText.setText(inf.getSim_6());

                    OsText = (TextView) rootView.findViewById(R.id.os_7);
                    OsText.setText(inf.getOs_6());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_7);
                    MaText.setText(inf.getMobilarena_6());

                    // Phone 8

                    NameText = (TextView) rootView.findViewById(R.id.name_8);
                    NameText.setText(inf.getName_7());

                    SimText = (TextView) rootView.findViewById(R.id.sim_8);
                    SimText.setText(inf.getSim_7());

                    OsText = (TextView) rootView.findViewById(R.id.os_8);
                    OsText.setText(inf.getOs_7());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_8);
                    MaText.setText(inf.getMobilarena_7());

                    // Phone 9

                    NameText = (TextView) rootView.findViewById(R.id.name_9);
                    NameText.setText(inf.getName_8());

                    SimText = (TextView) rootView.findViewById(R.id.sim_9);
                    SimText.setText(inf.getSim_8());

                    OsText = (TextView) rootView.findViewById(R.id.os_9);
                    OsText.setText(inf.getOs_8());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_9);
                    MaText.setText(inf.getMobilarena_8());

                    // Phone 10

                    NameText = (TextView) rootView.findViewById(R.id.name_10);
                    NameText.setText(inf.getName_9());

                    SimText = (TextView) rootView.findViewById(R.id.sim_10);
                    SimText.setText(inf.getSim_9());

                    OsText = (TextView) rootView.findViewById(R.id.os_10);
                    OsText.setText(inf.getOs_9());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_10);
                    MaText.setText(inf.getMobilarena_9());

                    // Phone 11

                    NameText = (TextView) rootView.findViewById(R.id.name_11);
                    NameText.setText(inf.getName_10());

                    SimText = (TextView) rootView.findViewById(R.id.sim_11);
                    SimText.setText(inf.getSim_10());

                    OsText = (TextView) rootView.findViewById(R.id.os_11);
                    OsText.setText(inf.getOs_10());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_11);
                    MaText.setText(inf.getMobilarena_10());

                    // Phone 12

                    NameText = (TextView) rootView.findViewById(R.id.name_12);
                    NameText.setText(inf.getName_11());

                    SimText = (TextView) rootView.findViewById(R.id.sim_12);
                    SimText.setText(inf.getSim_11());

                    OsText = (TextView) rootView.findViewById(R.id.os_12);
                    OsText.setText(inf.getOs_11());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_12);
                    MaText.setText(inf.getMobilarena_11());

                    break;

                case 13:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_14);
                    rl.setVisibility(View.GONE);
                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    // Phone 7

                    NameText = (TextView) rootView.findViewById(R.id.name_7);
                    NameText.setText(inf.getName_6());

                    SimText = (TextView) rootView.findViewById(R.id.sim_7);
                    SimText.setText(inf.getSim_6());

                    OsText = (TextView) rootView.findViewById(R.id.os_7);
                    OsText.setText(inf.getOs_6());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_7);
                    MaText.setText(inf.getMobilarena_6());

                    // Phone 8

                    NameText = (TextView) rootView.findViewById(R.id.name_8);
                    NameText.setText(inf.getName_7());

                    SimText = (TextView) rootView.findViewById(R.id.sim_8);
                    SimText.setText(inf.getSim_7());

                    OsText = (TextView) rootView.findViewById(R.id.os_8);
                    OsText.setText(inf.getOs_7());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_8);
                    MaText.setText(inf.getMobilarena_7());

                    // Phone 9

                    NameText = (TextView) rootView.findViewById(R.id.name_9);
                    NameText.setText(inf.getName_8());

                    SimText = (TextView) rootView.findViewById(R.id.sim_9);
                    SimText.setText(inf.getSim_8());

                    OsText = (TextView) rootView.findViewById(R.id.os_9);
                    OsText.setText(inf.getOs_8());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_9);
                    MaText.setText(inf.getMobilarena_8());

                    // Phone 10

                    NameText = (TextView) rootView.findViewById(R.id.name_10);
                    NameText.setText(inf.getName_9());

                    SimText = (TextView) rootView.findViewById(R.id.sim_10);
                    SimText.setText(inf.getSim_9());

                    OsText = (TextView) rootView.findViewById(R.id.os_10);
                    OsText.setText(inf.getOs_9());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_10);
                    MaText.setText(inf.getMobilarena_9());

                    // Phone 11

                    NameText = (TextView) rootView.findViewById(R.id.name_11);
                    NameText.setText(inf.getName_10());

                    SimText = (TextView) rootView.findViewById(R.id.sim_11);
                    SimText.setText(inf.getSim_10());

                    OsText = (TextView) rootView.findViewById(R.id.os_11);
                    OsText.setText(inf.getOs_10());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_11);
                    MaText.setText(inf.getMobilarena_10());

                    // Phone 12

                    NameText = (TextView) rootView.findViewById(R.id.name_12);
                    NameText.setText(inf.getName_11());

                    SimText = (TextView) rootView.findViewById(R.id.sim_12);
                    SimText.setText(inf.getSim_11());

                    OsText = (TextView) rootView.findViewById(R.id.os_12);
                    OsText.setText(inf.getOs_11());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_12);
                    MaText.setText(inf.getMobilarena_11());

                    // Phone 13

                    NameText = (TextView) rootView.findViewById(R.id.name_13);
                    NameText.setText(inf.getName_12());

                    SimText = (TextView) rootView.findViewById(R.id.sim_13);
                    SimText.setText(inf.getSim_12());

                    OsText = (TextView) rootView.findViewById(R.id.os_13);
                    OsText.setText(inf.getOs_12());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_13);
                    MaText.setText(inf.getMobilarena_12());

                    break;

                case 14:

                    rl = (RelativeLayout) rootView.findViewById(R.id.phone_15);
                    rl.setVisibility(View.GONE);

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    // Phone 7

                    NameText = (TextView) rootView.findViewById(R.id.name_7);
                    NameText.setText(inf.getName_6());

                    SimText = (TextView) rootView.findViewById(R.id.sim_7);
                    SimText.setText(inf.getSim_6());

                    OsText = (TextView) rootView.findViewById(R.id.os_7);
                    OsText.setText(inf.getOs_6());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_7);
                    MaText.setText(inf.getMobilarena_6());

                    // Phone 8

                    NameText = (TextView) rootView.findViewById(R.id.name_8);
                    NameText.setText(inf.getName_7());

                    SimText = (TextView) rootView.findViewById(R.id.sim_8);
                    SimText.setText(inf.getSim_7());

                    OsText = (TextView) rootView.findViewById(R.id.os_8);
                    OsText.setText(inf.getOs_7());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_8);
                    MaText.setText(inf.getMobilarena_7());

                    // Phone 9

                    NameText = (TextView) rootView.findViewById(R.id.name_9);
                    NameText.setText(inf.getName_8());

                    SimText = (TextView) rootView.findViewById(R.id.sim_9);
                    SimText.setText(inf.getSim_8());

                    OsText = (TextView) rootView.findViewById(R.id.os_9);
                    OsText.setText(inf.getOs_8());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_9);
                    MaText.setText(inf.getMobilarena_8());

                    // Phone 10

                    NameText = (TextView) rootView.findViewById(R.id.name_10);
                    NameText.setText(inf.getName_9());

                    SimText = (TextView) rootView.findViewById(R.id.sim_10);
                    SimText.setText(inf.getSim_9());

                    OsText = (TextView) rootView.findViewById(R.id.os_10);
                    OsText.setText(inf.getOs_9());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_10);
                    MaText.setText(inf.getMobilarena_9());

                    // Phone 11

                    NameText = (TextView) rootView.findViewById(R.id.name_11);
                    NameText.setText(inf.getName_10());

                    SimText = (TextView) rootView.findViewById(R.id.sim_11);
                    SimText.setText(inf.getSim_10());

                    OsText = (TextView) rootView.findViewById(R.id.os_11);
                    OsText.setText(inf.getOs_10());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_11);
                    MaText.setText(inf.getMobilarena_10());

                    // Phone 12

                    NameText = (TextView) rootView.findViewById(R.id.name_12);
                    NameText.setText(inf.getName_11());

                    SimText = (TextView) rootView.findViewById(R.id.sim_12);
                    SimText.setText(inf.getSim_11());

                    OsText = (TextView) rootView.findViewById(R.id.os_12);
                    OsText.setText(inf.getOs_11());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_12);
                    MaText.setText(inf.getMobilarena_11());

                    // Phone 13

                    NameText = (TextView) rootView.findViewById(R.id.name_13);
                    NameText.setText(inf.getName_12());

                    SimText = (TextView) rootView.findViewById(R.id.sim_13);
                    SimText.setText(inf.getSim_12());

                    OsText = (TextView) rootView.findViewById(R.id.os_13);
                    OsText.setText(inf.getOs_12());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_13);
                    MaText.setText(inf.getMobilarena_12());

                    // Phone 14

                    NameText = (TextView) rootView.findViewById(R.id.name_14);
                    NameText.setText(inf.getName_13());

                    SimText = (TextView) rootView.findViewById(R.id.sim_14);
                    SimText.setText(inf.getSim_13());

                    OsText = (TextView) rootView.findViewById(R.id.os_14);
                    OsText.setText(inf.getOs_13());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_14);
                    MaText.setText(inf.getMobilarena_13());

                    break;

                case 15:

                    // List

                    NameText = (TextView) rootView.findViewById(R.id.name_1);
                    NameText.setText(inf.getName_0());

                    SimText = (TextView) rootView.findViewById(R.id.sim_1);
                    SimText.setText(inf.getSim_0());

                    OsText = (TextView) rootView.findViewById(R.id.os_1);
                    OsText.setText(inf.getOs_0());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_1);
                    MaText.setText(inf.getMobilarena_0());

                    // Phone 2

                    NameText = (TextView) rootView.findViewById(R.id.name_2);
                    NameText.setText(inf.getName_1());

                    SimText = (TextView) rootView.findViewById(R.id.sim_2);
                    SimText.setText(inf.getSim_1());

                    OsText = (TextView) rootView.findViewById(R.id.os_2);
                    OsText.setText(inf.getOs_1());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_2);
                    MaText.setText(inf.getMobilarena_1());

                    // Phone 3

                    NameText = (TextView) rootView.findViewById(R.id.name_3);
                    NameText.setText(inf.getName_2());

                    SimText = (TextView) rootView.findViewById(R.id.sim_3);
                    SimText.setText(inf.getSim_2());

                    OsText = (TextView) rootView.findViewById(R.id.os_3);
                    OsText.setText(inf.getOs_2());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_3);
                    MaText.setText(inf.getMobilarena_2());

                    // Phone 4

                    NameText = (TextView) rootView.findViewById(R.id.name_4);
                    NameText.setText(inf.getName_3());

                    SimText = (TextView) rootView.findViewById(R.id.sim_4);
                    SimText.setText(inf.getSim_3());

                    OsText = (TextView) rootView.findViewById(R.id.os_4);
                    OsText.setText(inf.getOs_3());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_4);
                    MaText.setText(inf.getMobilarena_3());

                    // Phone 5

                    NameText = (TextView) rootView.findViewById(R.id.name_5);
                    NameText.setText(inf.getName_4());

                    SimText = (TextView) rootView.findViewById(R.id.sim_5);
                    SimText.setText(inf.getSim_4());

                    OsText = (TextView) rootView.findViewById(R.id.os_5);
                    OsText.setText(inf.getOs_4());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_5);
                    MaText.setText(inf.getMobilarena_4());

                    // Phone 6

                    NameText = (TextView) rootView.findViewById(R.id.name_6);
                    NameText.setText(inf.getName_5());

                    SimText = (TextView) rootView.findViewById(R.id.sim_6);
                    SimText.setText(inf.getSim_5());

                    OsText = (TextView) rootView.findViewById(R.id.os_6);
                    OsText.setText(inf.getOs_5());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_6);
                    MaText.setText(inf.getMobilarena_5());

                    // Phone 7

                    NameText = (TextView) rootView.findViewById(R.id.name_7);
                    NameText.setText(inf.getName_6());

                    SimText = (TextView) rootView.findViewById(R.id.sim_7);
                    SimText.setText(inf.getSim_6());

                    OsText = (TextView) rootView.findViewById(R.id.os_7);
                    OsText.setText(inf.getOs_6());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_7);
                    MaText.setText(inf.getMobilarena_6());

                    // Phone 8

                    NameText = (TextView) rootView.findViewById(R.id.name_8);
                    NameText.setText(inf.getName_7());

                    SimText = (TextView) rootView.findViewById(R.id.sim_8);
                    SimText.setText(inf.getSim_7());

                    OsText = (TextView) rootView.findViewById(R.id.os_8);
                    OsText.setText(inf.getOs_7());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_8);
                    MaText.setText(inf.getMobilarena_7());

                    // Phone 9

                    NameText = (TextView) rootView.findViewById(R.id.name_9);
                    NameText.setText(inf.getName_8());

                    SimText = (TextView) rootView.findViewById(R.id.sim_9);
                    SimText.setText(inf.getSim_8());

                    OsText = (TextView) rootView.findViewById(R.id.os_9);
                    OsText.setText(inf.getOs_8());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_9);
                    MaText.setText(inf.getMobilarena_8());

                    // Phone 10

                    NameText = (TextView) rootView.findViewById(R.id.name_10);
                    NameText.setText(inf.getName_9());

                    SimText = (TextView) rootView.findViewById(R.id.sim_10);
                    SimText.setText(inf.getSim_9());

                    OsText = (TextView) rootView.findViewById(R.id.os_10);
                    OsText.setText(inf.getOs_9());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_10);
                    MaText.setText(inf.getMobilarena_9());

                    // Phone 11

                    NameText = (TextView) rootView.findViewById(R.id.name_11);
                    NameText.setText(inf.getName_10());

                    SimText = (TextView) rootView.findViewById(R.id.sim_11);
                    SimText.setText(inf.getSim_10());

                    OsText = (TextView) rootView.findViewById(R.id.os_11);
                    OsText.setText(inf.getOs_10());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_11);
                    MaText.setText(inf.getMobilarena_10());

                    // Phone 12

                    NameText = (TextView) rootView.findViewById(R.id.name_12);
                    NameText.setText(inf.getName_11());

                    SimText = (TextView) rootView.findViewById(R.id.sim_12);
                    SimText.setText(inf.getSim_11());

                    OsText = (TextView) rootView.findViewById(R.id.os_12);
                    OsText.setText(inf.getOs_11());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_12);
                    MaText.setText(inf.getMobilarena_11());

                    // Phone 13

                    NameText = (TextView) rootView.findViewById(R.id.name_13);
                    NameText.setText(inf.getName_12());

                    SimText = (TextView) rootView.findViewById(R.id.sim_13);
                    SimText.setText(inf.getSim_12());

                    OsText = (TextView) rootView.findViewById(R.id.os_13);
                    OsText.setText(inf.getOs_12());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_13);
                    MaText.setText(inf.getMobilarena_12());

                    // Phone 14

                    NameText = (TextView) rootView.findViewById(R.id.name_14);
                    NameText.setText(inf.getName_13());

                    SimText = (TextView) rootView.findViewById(R.id.sim_14);
                    SimText.setText(inf.getSim_13());

                    OsText = (TextView) rootView.findViewById(R.id.os_14);
                    OsText.setText(inf.getOs_13());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_14);
                    MaText.setText(inf.getMobilarena_13());

                    // Phone 15

                    NameText = (TextView) rootView.findViewById(R.id.name_15);
                    NameText.setText(inf.getName_14());

                    SimText = (TextView) rootView.findViewById(R.id.sim_15);
                    SimText.setText(inf.getSim_14());

                    OsText = (TextView) rootView.findViewById(R.id.os_15);
                    OsText.setText(inf.getOs_14());

                    MaText = (TextView) rootView.findViewById(R.id.ma_rate_15);
                    MaText.setText(inf.getMobilarena_14());

                    break;

            }

        }
    }

    /**
     *  Async Image loader
     */

        private class LoadImage extends AsyncTask<String, String, Bitmap> {

       private final ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
         protected  void onPreExecute(){

                if (first) {
                dialog.setMessage("Betöltés...");
                dialog.show();
                    first = false;
            }
            }

            protected Bitmap doInBackground(String... args) {
                if(!offline){

                    try {

                        bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return bitmap;
                }else
                    return BitmapFactory.decodeResource(getResources(),
                            R.drawable.ic_deviceoffline);
            }
        protected void onPostExecute(Bitmap image) {

          /*  isLoaded = true;
            if(isLoaded)*/ dialog.dismiss();

            if(image != null){

                switch (index){

                    case 1:
                        img = (ImageView) rootView.findViewById(R.id.pic_1);
                        img.setImageBitmap(image);
                        break;
                    case 2:
                        img = (ImageView) rootView.findViewById(R.id.pic_2);
                        img.setImageBitmap(image);
                        break;
                    case 3:
                        img = (ImageView) rootView.findViewById(R.id.pic_3);
                        img.setImageBitmap(image);
                        break;
                    case 4:
                        img = (ImageView) rootView.findViewById(R.id.pic_4);
                        img.setImageBitmap(image);
                        break;
                    case 5:
                        img = (ImageView) rootView.findViewById(R.id.pic_5);
                        img.setImageBitmap(image);
                        break;
                    case 6:
                        img = (ImageView) rootView.findViewById(R.id.pic_6);
                        img.setImageBitmap(image);
                        break;
                    case 7:
                        img = (ImageView) rootView.findViewById(R.id.pic_7);
                        img.setImageBitmap(image);
                        break;
                    case 8:
                        img = (ImageView) rootView.findViewById(R.id.pic_8);
                        img.setImageBitmap(image);
                        break;
                    case 9:
                        img = (ImageView) rootView.findViewById(R.id.pic_9);
                        img.setImageBitmap(image);
                        break;
                    case 10:
                        img = (ImageView) rootView.findViewById(R.id.pic_10);
                        img.setImageBitmap(image);
                        break;
                    case 11:
                        img = (ImageView) rootView.findViewById(R.id.pic_11);
                        img.setImageBitmap(image);
                        break;
                    case 12:
                        img = (ImageView) rootView.findViewById(R.id.pic_12);
                        img.setImageBitmap(image);
                        break;
                    case 13:
                        img = (ImageView) rootView.findViewById(R.id.pic_13);
                        img.setImageBitmap(image);
                        break;
                    case 14:
                        img = (ImageView) rootView.findViewById(R.id.pic_14);
                        img.setImageBitmap(image);
                        break;
                    case 15:
                        img = (ImageView) rootView.findViewById(R.id.pic_15);
                        img.setImageBitmap(image);
                        break;
                    default: break;
                }
            }else{
                MainActivity activity = (MainActivity) getActivity();
                activity.sendSnack("Error loading phone no:" + index +" - (FR-02)");
                Log.e("List Fragment","Error loading phone no:" + index +" - (FR-02)");
            }

            if (index != counts) {
                index++;
                imgURL = baseURL + brand + "/" + Integer.toString(index) + ".jpg";
                new LoadImage().execute(imgURL);
            }else dialog.dismiss();
        }
    }
}
