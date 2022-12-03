package com.example.androidproject08;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomMyorderListViewAdapter extends ArrayAdapter<Myorder> {
    Context curContext;
    ArrayList<Myorder> orders = new ArrayList<>();

    public CustomMyorderListViewAdapter(Context context, int resource, ArrayList<Myorder> objects) {
        super(context, resource, objects);
        this.orders = objects;
        this.curContext = context;
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

        name.setText(orders.get(position).getName());
        size.setText("Kích thước: " + orders.get(position).getSize() + ", Màu sắc: " + orders.get(position).getColor());
        old_cost.setText("đ" + orders.get(position).getOldCost().toString());
        count.setText("Số lượng: " + orders.get(position).getCount().toString());
        new_cost.setText("đ" + orders.get(position).getNewCost().toString());
        total.setText("đ" + orders.get(position).getTotal().toString());
        Picasso.with(curContext).load(orders.get(position).getImage()).into(img);

        return v;

    }
}