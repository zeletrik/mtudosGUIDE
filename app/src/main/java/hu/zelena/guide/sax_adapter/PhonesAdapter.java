package hu.zelena.guide.sax_adapter;

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

import hu.zelena.guide.MainActivity;
import hu.zelena.guide.R;
import hu.zelena.guide.modell.PhonesModell;

/**
 * Created by patrik on 2016.11.25..
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

        if(preferences.getBoolean("offline",false)){
            offline = true;
        }else offline = false;

        if(offline){
            basePath = Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/offline/phones";
        }else{
            basePath = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/phones";
        }


        if(preferences.getBoolean("dataSaver",false)){
            saver = true;
        }else saver = false;

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
        ImageView imgView;
        String imgPath;
        public Bitmap bitmap;
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