package hu.zelena.guide;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hu.zelena.guide.adapter_sax.RssAdapter;
import hu.zelena.guide.modell.RssItem;
import hu.zelena.guide.util.ActivityHelper;
import hu.zelena.guide.util.RssReader;

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

public class RssActivity extends AppCompatActivity {

    private ListView mList;
    Context context = this;
    RssAdapter adapter;
    RssReader rssReader;
    List<RssItem> aList = new ArrayList<RssItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle = getIntent().getExtras();
        Boolean isDark = mainBundle.getBoolean("darkMode");

        ActivityHelper.initialize(this);

        if (isDark) {
            setTheme(R.style.SpecDarkTheme);
        }

        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.rss_feed_main);
        mList = (ListView) findViewById(R.id.list);
        new GetRssFeed().execute("http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/rss.xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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

    private class GetRssFeed extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                rssReader = new RssReader(params[0]);
                aList = rssReader.getItems();
            } catch (Exception e) {
                Intent i = new Intent(RssActivity.this, ErrorActivity.class);
                i.putExtra("darkMode", false);
                i.putExtra("error", "Feldolgozási hiba: " + e.getMessage());
                startActivity(i);
                Log.v("Error Parsing Data", e + "");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter = new RssAdapter(context, R.layout.rss_list_item, aList);
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            mList.setAdapter(adapter);

            AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    RssItem data = aList.get(arg2);

                    Bundle postInfo = new Bundle();
                    postInfo.putString("URL", data.getLink());
                    postInfo.putString("title", data.getTitle());

                    Intent postviewIntent = new Intent(RssActivity.this, WebViewActivity.class);
                    postviewIntent.putExtras(postInfo);
                    startActivity(postviewIntent);
                }
            };

            mList.setOnItemClickListener(onItemClickListener);
        }
    }


}
