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

public class CustomMyListViewPaymentAdapter extends ArrayAdapter<Order> {

    ArrayList<Order> order = new ArrayList<Order>();


    public CustomMyListViewPaymentAdapter(Context context, int resource, ArrayList<Order> objects) {
        super(context, resource, objects);
        this.order = objects;
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
        v = inflater.inflate(R.layout.custom_listview_payment, null);

        TextView name = (TextView) v.findViewById(R.id.custom_name_product);
        TextView old_cost = (TextView) v.findViewById(R.id.custom_old_cost_product_payment);
        TextView new_cost = (TextView) v.findViewById(R.id.custom_new_cost_product_payment);
        TextView number = (TextView) v.findViewById(R.id.soluong_payment);
        TextView size_color = (TextView) v.findViewById(R.id.size_color_product_payment);
        TextView charge_tranfer = (TextView) v.findViewById(R.id.cost_tranfer_payment);
        TextView cost_final = (TextView) v.findViewById(R.id.total_cost_product_payment);
        ImageView img = (ImageView) v.findViewById(R.id.custom_picture_payment) ;

        name.setText(order.get(position).getName());
        old_cost.setText(order.get(position).getOld_cost());
        new_cost.setText(order.get(position).getNew_cost());
        number.setText(order.get(position).getNumber());
        size_color.setText(order.get(position).getSize()+","+order.get(position).getColor());
        charge_tranfer.setText(order.get(position).get_charge_tranfer());
        cost_final.setText(order.get(position).get_cost_final());
        img.setImageResource(order.get(position).getImage());
        return v;
    }
}