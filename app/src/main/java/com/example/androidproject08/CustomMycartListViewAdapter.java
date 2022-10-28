package com.example.androidproject08;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomMycartListViewAdapter extends ArrayAdapter<MyCart> {

    ArrayList<MyCart> my_cart = new ArrayList<MyCart>();


    public CustomMycartListViewAdapter(Context context, int resource, ArrayList<MyCart> objects) {
        super(context, resource, objects);
        this.my_cart = objects;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.custom_mycart_listview, null);

        TextView name = (TextView) v.findViewById(R.id.name_product);
        TextView old_cost = (TextView) v.findViewById(R.id.old_cost_product);
        TextView new_cost = (TextView) v.findViewById(R.id.new_cost_product);
        TextView number = (TextView) v.findViewById(R.id.number_product);
        ImageView img = (ImageView) v.findViewById(R.id.picture) ;

        name.setText(my_cart.get(position).getName());
        old_cost.setText(my_cart.get(position).getOld_cost());
        new_cost.setText(my_cart.get(position).getNew_cost());
        number.setText(my_cart.get(position).getNumber());
        img.setImageResource(my_cart.get(position).getImage());

        return v;

    }
}