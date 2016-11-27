package hu.zelena.guide.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.support.v7.app.ActionBarActivity;

import hu.zelena.guide.ErrorActivity;

/**
 * Created by patrik on 2016.11.22..
 */

public class DownloadActivity  extends ActionBarActivity {

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startDownload();
    }

    private void startDownload() {
        String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/offline/test.zip";
        new DownloadFileAsync().execute(url);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Letöltés..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            OutputStream output = null;

            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                Log.d("Input strem", "OK");

                File folder = new File(Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide");
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdir();
                }
                if (success) {
                    Log.d("MKDIR: ","OK");
                } else {
                    Log.d("MKDIR: ","FAIL");
                }
                output = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "Android/data/hu.zelena.guide/test.zip"));
                Log.d("Output strem", "OK");
                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Intent i = new Intent(DownloadActivity.this, ErrorActivity.class);
                i.putExtra("darkMode", false);
                i.putExtra("error", "Letöltési hiba: "+e.getMessage());
                startActivity(i);

            }
            return null;

        }
        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            Log.d("ANDRO_ASYNC", "Finished");
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);

            String zipFile = Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/test.zip";
            String unzipLocation = Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/";

            Decompress d = new Decompress(zipFile, unzipLocation);
            d.unzip();

            finish();
        }
    }
}
