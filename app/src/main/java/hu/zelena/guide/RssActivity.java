package hu.zelena.guide;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import hu.zelena.guide.sax_adapter.RssAdapter;
import hu.zelena.guide.modell.RssItem;
import hu.zelena.guide.rss.RssReader;
import hu.zelena.guide.util.ActivityHelper;

/**
 * Created by patrik on 2016.11.08..
 */

public class RssActivity extends AppCompatActivity {

    private ListView mList;
    Context context = this;
    RssAdapter adapter;
    RssReader rssReader;
    List<RssItem> aList = new ArrayList<RssItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle mainBundle  = getIntent().getExtras();
        Boolean isDark = mainBundle.getBoolean("darkMode");

        ActivityHelper.initialize(this);

        if(isDark){
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
        // Inflate the menu; this adds items to the action bar if it is present.
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
                i.putExtra("error", "Feldolgozási hiba: "+e.getMessage());
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