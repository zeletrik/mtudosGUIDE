package hu.zelena.guide.sax_adapter;

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

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hu.zelena.guide.R;
import hu.zelena.guide.modell.RssItem;

public class RssAdapter extends ArrayAdapter<RssItem> {
    private Activity myContext;
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