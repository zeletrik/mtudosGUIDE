package hu.zelena.guide.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import hu.zelena.guide.ErrorActivity;
import hu.zelena.guide.R;
import hu.zelena.guide.modell.Specs;
import hu.zelena.guide.util.SpecsReader;

/**
 * Created by patrik on 2017.02.24..
 */

/**
 Copyright (C) <2017>  <Patrik G. Zelena>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
public class CompareFragment extends Fragment {

    View rootView;
    private String brand;
    private String brand_1;
    private Specs specs_1;
    private Specs specs;
    private String phone;
    private String phone_1;
    private String baseURL;
    private String baseURL_1;
    private boolean offline;

    public CompareFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_compare, container, false);

        Bundle bundle = this.getArguments();
        brand_1 = bundle.getString("currentBrand");
        phone_1 = bundle.getString("currentPhone");

        brand = bundle.getString("brand");
        phone = bundle.getString("phone");

        Log.d("Extra2: " + brand + phone + brand_1 + phone_1, "2");

      /*  brand = "Apple";
        phone = "iPhone 7";
        brand_1 = "Apple";
        phone_1 = "iPhone SE";*/

        getActivity().setTitle("Összehasonlítás");


        PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        offline = preferences.getBoolean("offline", false);

        if (offline) {
            baseURL = Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/data/offline/" + brand + "/specs/" + phone + ".xml";
            new GetOfflineSpecs().execute(baseURL);

            baseURL_1 = Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/data/offline/" + brand_1 + "/specs/" + phone_1 + ".xml";
            new GetOfflineSpecs_1().execute(baseURL_1);
        } else {
            baseURL = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/phones/" + brand + "/specs/" + phone;
            new HttpRequestSpecs().execute();

            baseURL_1 = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/phones/" + brand_1 + "/specs/" + phone_1;
            new HttpRequestSpecs_1().execute();
        }


        return rootView;
    }

    private class HttpRequestSpecs extends AsyncTask<Void, Void, Specs> {
        @Override
        protected Specs doInBackground(Void... params) {
            try {
                final String url = baseURL;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Specs specs = restTemplate.getForObject(url, Specs.class);
                return specs;
            } catch (Exception e) {
                Intent i = new Intent(getActivity(), ErrorActivity.class);
                i.putExtra("error", "HTTPAsyncTask: " + e.getMessage());
                startActivity(i);
                Log.e("Async ERROR:", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Specs specs) {

            //  getActivity().setTitle(specs.getName());
            TextView name = (TextView) rootView.findViewById(R.id.device_2);
            name.setText(specs.getName());
            // Display
            TextView DisplayType = (TextView) rootView.findViewById(R.id.displayType);
            DisplayType.setText(specs.getDisplayType());

            TextView DisplaySize = (TextView) rootView.findViewById(R.id.displaySize);
            DisplaySize.setText(specs.getDisplaySize());

            TextView DisplayRes = (TextView) rootView.findViewById(R.id.displayRes);
            DisplayRes.setText(specs.getDisplayRes());

            TextView DisplayProt = (TextView) rootView.findViewById(R.id.displayProtect);
            DisplayProt.setText(specs.getDisplayProtect());

            //Platform
            TextView OsSpec = (TextView) rootView.findViewById(R.id.os_spec);
            OsSpec.setText(specs.getOs_spec());

            TextView Chipset = (TextView) rootView.findViewById(R.id.chipset);
            Chipset.setText(specs.getChipset());

            TextView CPU = (TextView) rootView.findViewById(R.id.cpu);
            CPU.setText(specs.getCpu());

            TextView GPU = (TextView) rootView.findViewById(R.id.gpu);
            GPU.setText(specs.getGpu());


            //Memroy
            TextView RAM = (TextView) rootView.findViewById(R.id.ram);
            RAM.setText(specs.getRam());

            TextView ROM = (TextView) rootView.findViewById(R.id.rom);
            ROM.setText(specs.getRom());

            TextView expand = (TextView) rootView.findViewById(R.id.expand);
            expand.setText(specs.getExpand());

            //Kamera
            TextView rCam = (TextView) rootView.findViewById(R.id.rcam);
            rCam.setText(specs.getrCam());

            TextView fCam = (TextView) rootView.findViewById(R.id.fcam);
            fCam.setText(specs.getfCam());

            //Other
            TextView batt = (TextView) rootView.findViewById(R.id.batt);
            batt.setText(specs.getBatt());

            TextView speaker = (TextView) rootView.findViewById(R.id.speaker);
            speaker.setText(specs.getSpeaker());

            TextView NFC = (TextView) rootView.findViewById(R.id.nfc);
            NFC.setText(specs.getNfc());

            TextView radio = (TextView) rootView.findViewById(R.id.radio);
            radio.setText(specs.getRadio());

            TextView IPcer = (TextView) rootView.findViewById(R.id.ipcertified);
            IPcer.setText(specs.getIpCertified());

        }
    }

    private class GetOfflineSpecs extends AsyncTask<String, Void, Specs> {
        @Override
        protected Specs doInBackground(String... params) {
            try {
                SpecsReader specsReader = new SpecsReader(baseURL);
                Specs spec = specsReader.getSpecs();
                return spec;
            } catch (Exception e) {
                Intent i = new Intent(getActivity(), ErrorActivity.class);
                i.putExtra("error", "SAXAsyncTask: " + e.getMessage());
                startActivity(i);
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Specs specs) {

            getActivity().setTitle(specs.getName());

            // Display
            TextView DisplayType = (TextView) rootView.findViewById(R.id.displayType);
            DisplayType.setText(specs.getDisplayType());

            TextView DisplaySize = (TextView) rootView.findViewById(R.id.displaySize);
            DisplaySize.setText(specs.getDisplaySize());

            TextView DisplayRes = (TextView) rootView.findViewById(R.id.displayRes);
            DisplayRes.setText(specs.getDisplayRes());

            TextView DisplayProt = (TextView) rootView.findViewById(R.id.displayProtect);
            DisplayProt.setText(specs.getDisplayProtect());

            //Platform
            TextView OsSpec = (TextView) rootView.findViewById(R.id.os_spec);
            OsSpec.setText(specs.getOs_spec());

            TextView Chipset = (TextView) rootView.findViewById(R.id.chipset);
            Chipset.setText(specs.getChipset());

            TextView CPU = (TextView) rootView.findViewById(R.id.cpu);
            CPU.setText(specs.getCpu());

            TextView GPU = (TextView) rootView.findViewById(R.id.gpu);
            GPU.setText(specs.getGpu());


            //Memroy
            TextView RAM = (TextView) rootView.findViewById(R.id.ram);
            RAM.setText(specs.getRam());

            TextView ROM = (TextView) rootView.findViewById(R.id.rom);
            ROM.setText(specs.getRom());

            TextView expand = (TextView) rootView.findViewById(R.id.expand);
            expand.setText(specs.getExpand());

            //Kamera
            TextView rCam = (TextView) rootView.findViewById(R.id.rcam);
            rCam.setText(specs.getrCam());

            TextView fCam = (TextView) rootView.findViewById(R.id.fcam);
            fCam.setText(specs.getfCam());

            //Other
            TextView batt = (TextView) rootView.findViewById(R.id.batt);
            batt.setText(specs.getBatt());

            TextView speaker = (TextView) rootView.findViewById(R.id.speaker);
            speaker.setText(specs.getSpeaker());

            TextView NFC = (TextView) rootView.findViewById(R.id.nfc);
            NFC.setText(specs.getNfc());

            TextView radio = (TextView) rootView.findViewById(R.id.radio);
            radio.setText(specs.getRadio());

            TextView IPcer = (TextView) rootView.findViewById(R.id.ipcertified);
            IPcer.setText(specs.getIpCertified());

        }
    }

    private class HttpRequestSpecs_1 extends AsyncTask<Void, Void, Specs> {
        @Override
        protected Specs doInBackground(Void... params) {
            try {
                final String url = baseURL_1;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Specs specs_1 = restTemplate.getForObject(url, Specs.class);
                return specs_1;
            } catch (Exception e) {
                Intent i = new Intent(getActivity(), ErrorActivity.class);
                i.putExtra("error", "HTTPAsyncTask: " + e.getMessage());
                startActivity(i);
                Log.e("Async ERROR:", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Specs specs) {

            //  getActivity().setTitle(specs.getName());
            TextView name = (TextView) rootView.findViewById(R.id.device_1);
            name.setText(specs.getName());

            // Display
            TextView DisplayType = (TextView) rootView.findViewById(R.id.displayType_1);
            DisplayType.setText(specs.getDisplayType());

            TextView DisplaySize = (TextView) rootView.findViewById(R.id.displaySize_1);
            DisplaySize.setText(specs.getDisplaySize());

            TextView DisplayRes = (TextView) rootView.findViewById(R.id.displayRes_1);
            DisplayRes.setText(specs.getDisplayRes());

            TextView DisplayProt = (TextView) rootView.findViewById(R.id.displayProtect_1);
            DisplayProt.setText(specs.getDisplayProtect());

            //Platform
            TextView OsSpec = (TextView) rootView.findViewById(R.id.os_spec_1);
            OsSpec.setText(specs.getOs_spec());

            TextView Chipset = (TextView) rootView.findViewById(R.id.chipset_1);
            Chipset.setText(specs.getChipset());

            TextView CPU = (TextView) rootView.findViewById(R.id.cpu_1);
            CPU.setText(specs.getCpu());

            TextView GPU = (TextView) rootView.findViewById(R.id.gpu_1);
            GPU.setText(specs.getGpu());


            //Memroy
            TextView RAM = (TextView) rootView.findViewById(R.id.ram_1);
            RAM.setText(specs.getRam());

            TextView ROM = (TextView) rootView.findViewById(R.id.rom_1);
            ROM.setText(specs.getRom());

            TextView expand = (TextView) rootView.findViewById(R.id.expand_1);
            expand.setText(specs.getExpand());

            //Kamera
            TextView rCam = (TextView) rootView.findViewById(R.id.rcam_1);
            rCam.setText(specs.getrCam());

            TextView fCam = (TextView) rootView.findViewById(R.id.fcam_1);
            fCam.setText(specs.getfCam());

            //Other
            TextView batt = (TextView) rootView.findViewById(R.id.batt_1);
            batt.setText(specs.getBatt());

            TextView speaker = (TextView) rootView.findViewById(R.id.speaker_1);
            speaker.setText(specs.getSpeaker());

            TextView NFC = (TextView) rootView.findViewById(R.id.nfc_1);
            NFC.setText(specs.getNfc());

            TextView radio = (TextView) rootView.findViewById(R.id.radio_1);
            radio.setText(specs.getRadio());

            TextView IPcer = (TextView) rootView.findViewById(R.id.ipcertified_1);
            IPcer.setText(specs.getIpCertified());

        }
    }

    private class GetOfflineSpecs_1 extends AsyncTask<String, Void, Specs> {
        @Override
        protected Specs doInBackground(String... params) {
            try {
                SpecsReader specsReader = new SpecsReader(baseURL);
                Specs spec = specsReader.getSpecs();
                return spec;
            } catch (Exception e) {
                Intent i = new Intent(getActivity(), ErrorActivity.class);
                i.putExtra("error", "SAXAsyncTask: " + e.getMessage());
                startActivity(i);
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Specs specs) {

            getActivity().setTitle(specs.getName());

            // Display
            TextView DisplayType = (TextView) rootView.findViewById(R.id.displayType);
            DisplayType.setText(specs.getDisplayType());

            TextView DisplaySize = (TextView) rootView.findViewById(R.id.displaySize);
            DisplaySize.setText(specs.getDisplaySize());

            TextView DisplayRes = (TextView) rootView.findViewById(R.id.displayRes);
            DisplayRes.setText(specs.getDisplayRes());

            TextView DisplayProt = (TextView) rootView.findViewById(R.id.displayProtect);
            DisplayProt.setText(specs.getDisplayProtect());

            //Platform
            TextView OsSpec = (TextView) rootView.findViewById(R.id.os_spec);
            OsSpec.setText(specs.getOs_spec());

            TextView Chipset = (TextView) rootView.findViewById(R.id.chipset);
            Chipset.setText(specs.getChipset());

            TextView CPU = (TextView) rootView.findViewById(R.id.cpu);
            CPU.setText(specs.getCpu());

            TextView GPU = (TextView) rootView.findViewById(R.id.gpu);
            GPU.setText(specs.getGpu());


            //Memroy
            TextView RAM = (TextView) rootView.findViewById(R.id.ram);
            RAM.setText(specs.getRam());

            TextView ROM = (TextView) rootView.findViewById(R.id.rom);
            ROM.setText(specs.getRom());

            TextView expand = (TextView) rootView.findViewById(R.id.expand);
            expand.setText(specs.getExpand());

            //Kamera
            TextView rCam = (TextView) rootView.findViewById(R.id.rcam);
            rCam.setText(specs.getrCam());

            TextView fCam = (TextView) rootView.findViewById(R.id.fcam);
            fCam.setText(specs.getfCam());

            //Other
            TextView batt = (TextView) rootView.findViewById(R.id.batt);
            batt.setText(specs.getBatt());

            TextView speaker = (TextView) rootView.findViewById(R.id.speaker);
            speaker.setText(specs.getSpeaker());

            TextView NFC = (TextView) rootView.findViewById(R.id.nfc);
            NFC.setText(specs.getNfc());

            TextView radio = (TextView) rootView.findViewById(R.id.radio);
            radio.setText(specs.getRadio());

            TextView IPcer = (TextView) rootView.findViewById(R.id.ipcertified);
            IPcer.setText(specs.getIpCertified());

        }
    }

}
