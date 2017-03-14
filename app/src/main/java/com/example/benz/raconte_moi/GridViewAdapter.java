package com.example.benz.raconte_moi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nadia on 08/03/2017.
 */

public class GridViewAdapter extends ArrayAdapter {
    public Context context;
    public int layoutResourceId;
    public ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId,ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }



        ImageItem item = (ImageItem) data.get(position);
        holder.imageTitle.setText(item.getTitle());
        //holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        public TextView imageTitle;
        //public TextView idHistory;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
}