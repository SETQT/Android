package com.example.androidproject08;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomProductLabelAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> logos;
    ArrayList<String> names;
    ArrayList<String> costs;
    ArrayList<String> costs_sale;
    ArrayList<String> percents_sale;
    LayoutInflater inflter;

    public CustomProductLabelAdapter(Context applicationContext, ArrayList<Integer> logos, ArrayList<String> names, ArrayList<String> costs, ArrayList<String> costs_sale, ArrayList<String> percents_sale) {
        this.context = applicationContext;
        this.logos = logos;
        this.names = names;
        this.costs = costs;
        this.costs_sale = costs_sale;
        this.percents_sale = percents_sale;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.size();
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
        ImageView picture = (ImageView) view.findViewById(R.id.dashboard_custom_picture_product); // get the reference of ImageView
        TextView name = (TextView) view.findViewById(R.id.dashboard_custom_name_product);
        TextView cost = (TextView) view.findViewById(R.id.dashboard_custom_cost_product);
        TextView cost_sale = (TextView) view.findViewById(R.id.dashboard_custom_cost_sale_product);
        TextView percent_sale = (TextView) view.findViewById(R.id.dashboard_custom_percent_sale_product);
        picture.setImageResource(logos.get(i)); // set logo images
        name.setText(names.get(i));
        cost.setText(costs.get(i));
        cost_sale.setText(costs_sale.get(i));
        percent_sale.setText(percents_sale.get(i));
        return view;
    }
}