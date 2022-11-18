package com.example.androidproject08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomMyorderListViewAdapter extends ArrayAdapter<Myorder> {

    ArrayList<Myorder> myorder = new ArrayList<Myorder>();


    public CustomMyorderListViewAdapter(Context context, int resource, ArrayList<Myorder> objects) {
        super(context, resource, objects);
        this.myorder = objects;
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
        v = inflater.inflate(R.layout.custom_myorder_listview, null);

        TextView name = (TextView) v.findViewById(R.id.myorder_custom_name);
        TextView size = (TextView) v.findViewById(R.id.myorder_custom_size);
        TextView old_cost = (TextView) v.findViewById(R.id.myorder_custom_old_cost);
        TextView new_cost = (TextView) v.findViewById(R.id.myorder_custom_new_cost);
        TextView count = (TextView) v.findViewById(R.id.myorder_custom_count);
        TextView total = (TextView) v.findViewById(R.id.myorder_custom_total);
        ImageView img = (ImageView) v.findViewById(R.id.myorder_custom_picture) ;

        name.setText(myorder.get(position).getName());
        size.setText(myorder.get(position).getSize());
        old_cost.setText(myorder.get(position).getOld_cost());
        new_cost.setText(myorder.get(position).getNew_cost());
        count.setText(myorder.get(position).getCount());
        total.setText(myorder.get(position).getTotal());

        img.setImageResource(myorder.get(position).getImage());

        return v;

    }
}