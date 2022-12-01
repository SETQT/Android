package com.example.androidproject08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomVoucherListViewAdapter extends ArrayAdapter<Voucher> {

    ArrayList<Voucher> voucher = new ArrayList<Voucher>();


    public CustomVoucherListViewAdapter(Context context, int resource, ArrayList<Voucher> objects) {
        super(context, resource, objects);
        this.voucher = objects;
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
        v = inflater.inflate(R.layout.custom_voucher_listview, null);

        TextView title = (TextView) v.findViewById(R.id.custom_voucher_title);
        TextView free_cost = (TextView) v.findViewById(R.id.custom_voucher_free_cost);
        TextView count = (TextView) v.findViewById(R.id.custom_voucher_count);
        TextView start_date = (TextView) v.findViewById(R.id.custom_voucher_start_date);
        TextView expiry_date = (TextView) v.findViewById(R.id.custom_voucher_expiry_date);

        ImageView img = (ImageView) v.findViewById(R.id.custom_voucher_picture) ;

        title.setText(voucher.get(position).getTitle());
        free_cost.setText(voucher.get(position).getFree_cost());
        count.setText(voucher.get(position).getCount());
        start_date.setText(voucher.get(position).getStart_date());
        expiry_date.setText(voucher.get(position).getExpiry_date());

        img.setImageResource(voucher.get(position).getImage());

        return v;

    }
}