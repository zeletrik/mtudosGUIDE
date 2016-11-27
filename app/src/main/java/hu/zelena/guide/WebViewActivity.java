package hu.zelena.guide;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import hu.zelena.guide.util.ActivityHelper;

/**
 * Created by patrik on 2016.11.08..
 */


public class WebViewActivity extends ActionBarActivity {

    private WebView webView;
    private String URL;

    public void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle  = getIntent().getExtras();

        ActivityHelper.initialize(this);


        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.webview);

        String title = mainBundle.getString("title");
        URL = mainBundle.getString("URL");

        setTitle(title);


        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        webView.setWebViewClient(new Callback());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
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
