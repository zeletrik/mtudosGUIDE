package hu.zelena.guide.adapter_sax;

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