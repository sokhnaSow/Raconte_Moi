package com.example.benz.raconte_moi;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by mouna on 28/02/2017.
 */

public class ListEnfantAdapter extends SimpleAdapter {

    public ListEnfantAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = super.getView(position,convertView,parent);
        return v;
    }
}
