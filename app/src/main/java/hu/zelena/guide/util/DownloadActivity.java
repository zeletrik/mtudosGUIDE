package hu.zelena.guide.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import hu.zelena.guide.ErrorActivity;

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

public class DownloadActivity  extends ActionBarActivity {

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;


    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startDownload();
    }

    private void startDownload() {
        new DeleteOfflineDir(Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide");
        String url = "http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/offline/data.zip";
        new DownloadFileAsync().execute(url);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Letöltés..");
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
                Log.d("Input stream", "OK");

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

        @Override
        protected void onPostExecute(String unused) {
            Log.d("ANDRO_ASYNC", "Finished");
            String zipFile = Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/test.zip";
            String unzipLocation = Environment.getExternalStorageDirectory() + "/Android/data/hu.zelena.guide/";

            Decompress d = new Decompress(zipFile, unzipLocation);
            d.unzip();
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            finish();
        }
    }


}
