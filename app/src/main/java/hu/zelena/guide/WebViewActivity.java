package hu.zelena.guide;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hu.zelena.guide.util.ActivityHelper;

/**
 Copyright Patrik G. Zelena

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

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
