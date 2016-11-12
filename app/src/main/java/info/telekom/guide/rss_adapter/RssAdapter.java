package info.telekom.guide.rss_adapter;

/**
 * Created by patrik on 2016.11.12..
 */

import info.telekom.guide.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import info.telekom.guide.rss_modell.RssItem;

public class RssAdapter extends ArrayAdapter<RssItem> {
    private Activity myContext;
   // private RssItem[] datas;
    private  List<RssItem> datas;

    public RssAdapter(Context context, int textViewResourceId,
                      List<RssItem> List) {
        super(context, textViewResourceId, List);
        myContext = (Activity) context;
        datas =  List;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.rss_list_item, null);

        TextView postTitleView = (TextView) rowView
                .findViewById(R.id.postTitleLabel);
        postTitleView.setText(datas.get(position).getTitle());

        TextView postCategoryView = (TextView) rowView
                .findViewById(R.id.postCategoryLabel);
        postCategoryView.setText(datas.get(position).getCategory());

        TextView postDateView = (TextView) rowView
                .findViewById(R.id.postDateLabel);
        postDateView.setText(datas.get(position).getPubDate());

        return rowView;
    }

}