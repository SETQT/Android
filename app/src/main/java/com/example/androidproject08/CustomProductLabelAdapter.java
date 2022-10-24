package com.example.androidproject08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomProductLabelAdapter extends BaseAdapter {
    Context context;
    int logos[];
    String names[];
    String costs[];
    LayoutInflater inflter;
    public CustomProductLabelAdapter(Context applicationContext, int[] logos, String[] names, String[] costs) {
        this.context = applicationContext;
        this.logos = logos;
        this.names = names;
        this.costs = costs;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_product_gridview, null); // inflate the layout
        ImageView picture = (ImageView) view.findViewById(R.id.picture_product); // get the reference of ImageView
        TextView name = (TextView) view.findViewById(R.id.name_product);
        TextView cost = (TextView) view.findViewById(R.id.cost_product);
        picture.setImageResource(logos[i]); // set logo images
        name.setText(names[i]);
        cost.setText(costs[i]);
        return view;
    }
}