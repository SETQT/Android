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

public class CustomNotifyListViewAdapter extends ArrayAdapter<Notify> {

    ArrayList<Notify> my_notify = new ArrayList<Notify>();


    public CustomNotifyListViewAdapter(Context context, int resource, ArrayList<Notify> objects) {
        super(context, resource, objects);
        this.my_notify = objects;
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
        v = inflater.inflate(R.layout.custom_notify_listview, null);

        TextView textTitle = (TextView) v.findViewById(R.id.itemTitle);
        ImageView imageView = (ImageView) v.findViewById(R.id.icon);
        TextView textContent = (TextView) v.findViewById(R.id.content);
        TextView textProduct = (TextView) v.findViewById(R.id.product);
        TextView textDate = (TextView) v.findViewById(R.id.date);

        textTitle.setText(my_notify.get(position).getTitle());

        textContent.setText(my_notify.get(position).getContent());
        textProduct.setText(my_notify.get(position).getProduct());
        textDate.setText(my_notify.get(position).getDate());
        imageView.setImageResource(my_notify.get(position).getImage());

        return v;

    }
}