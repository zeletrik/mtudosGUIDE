package info.telekom.guide;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import info.telekom.guide.rss.RssHandler;
import info.telekom.guide.rss_adapter.RssAdapter;
import info.telekom.guide.rss_modell.RssItem;
import info.telekom.guide.rss.RssReader;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_feed_main);
        mList = (ListView) findViewById(R.id.list);
       // adapter = new ArrayAdapter<String>(this, R.layout.rss_list_item);
        new GetRssFeed().execute("http://users.iit.uni-miskolc.hu/~zelena5/work/telekom/mobiltud/rss2.xml");

    }

    private class GetRssFeed extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                rssReader = new RssReader(params[0]);
               // for (RssItem item : rssReader.getItems())
                         aList = rssReader.getItems();
                 //   adapter.add(item.getTitle());
            } catch (Exception e) {
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
