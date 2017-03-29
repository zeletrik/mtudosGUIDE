package hu.zelena.guide.adapter_sax;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import hu.zelena.guide.R;
import hu.zelena.guide.modell.PhonesModell;

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

public class PhonesAdapter extends ArrayAdapter<PhonesModell> {
    private Activity myContext;
    private List<PhonesModell> datas;
    private boolean saver;
    private boolean offline = false;
    private String basePath;
    private Context mContext;


    public PhonesAdapter(Context context, int textViewResourceId,
                         List<PhonesModell> List) {
        super(context, textViewResourceId, List);
        myContext = (Activity) context;
        datas = List;

        this.mContext=context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.phone_list_item, null);


        TextView nameText = (TextView) rowView
                .findViewById(R.id.name);
        nameText.setText(datas.get(position).getName());

        TextView simText = (TextView) rowView
                .findViewById(R.id.sim);
        simText.setText(datas.get(position).getSim());

        TextView osText = (TextView) rowView
                .findViewById(R.id.os);
        osText.setText(datas.get(position).getOs());

        TextView maText = (TextView) rowView
                .findViewById(R.id.ma_rate);
        maText.setText(datas.get(position).getMobilarena());

        PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        offline = preferences.getBoolean("offline", false);

        if(offline){
            basePath = Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/data/offline/";
        }else{
            basePath = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/phones";
        }


        saver = preferences.getBoolean("dataSaver", false);

        viewHolder = new ViewHolder();
        viewHolder.imgView = (ImageView) rowView.findViewById(R.id.pic);

        PhonesModell post = datas.get(position);
        if (post.getPicURL() != null) {
                viewHolder.imgPath = basePath + post.getPicURL();
                new DownloadImage().execute(viewHolder);
        } else {
                viewHolder.imgView.setImageResource(R.drawable.ic_load_pic);
        }


        return rowView;
    }
    
    static class ViewHolder {
        public Bitmap bitmap;
        ImageView imgView;
        String imgPath;
    }

    private class DownloadImage extends AsyncTask<ViewHolder, Void, ViewHolder> {

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            ViewHolder viewHolder = params[0];
            try {
                if(offline){
                    viewHolder.bitmap = BitmapFactory.decodeFile(viewHolder.imgPath);
                }else {
                    URL imageURL = new URL(viewHolder.imgPath);
                    viewHolder.bitmap = BitmapFactory.decodeStream((InputStream)imageURL.getContent());
                }
            } catch (IOException e) {
                Log.e("error", "Downloading Image Failed: " + e);
                viewHolder.bitmap = null;
            }
            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder result) {

           if(!saver){
               if (result.bitmap == null) {
                   result.imgView.setImageResource(R.drawable.ic_load_pic);
               } else {
                   result.imgView.setImageBitmap(result.bitmap);
               }
           }else{
               result.imgView.setImageResource(R.drawable.ic_deviceoffline);
           }
        }
    }
}