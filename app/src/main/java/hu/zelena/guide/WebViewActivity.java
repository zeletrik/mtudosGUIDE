package hu.zelena.guide;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hu.zelena.guide.util.ActivityHelper;

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


public class WebViewActivity extends ActionBarActivity {

    private WebView webView;
    private String URL;

    public void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle = getIntent().getExtras();

        ActivityHelper.initialize(this);


        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.webview);

        String title = mainBundle.getString("title");
        URL = mainBundle.getString("URL");

        setTitle(title);


        webView = (WebView) findViewById(R.id.webView1);
        //  webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.setWebViewClient(new Callback());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private class Callback extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }


}
