package com.example.androidproject08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomSelectVoucherListViewAdapter extends ArrayAdapter<Voucher> {
    ArrayList<Voucher> vouchers = new ArrayList<Voucher>();

    public CustomSelectVoucherListViewAdapter(Context context, int resource, ArrayList<Voucher> objects) {
        super(context, resource, objects);
        this.vouchers = objects;
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
        v = inflater.inflate(R.layout.custom_select_voucher_listview, null);

        TextView title = (TextView) v.findViewById(R.id.custom_select_voucher_title);
        TextView free_cost = (TextView) v.findViewById(R.id.custom_select_voucher_free_cost);
        CheckBox checkbox = (CheckBox) v.findViewById(R.id.custom_select_voucher_checkbox);
        TextView start_date = (TextView) v.findViewById(R.id.custom_select_voucher_start_date);
        TextView expiry_date = (TextView) v.findViewById(R.id.custom_select_voucher_expiry_date);

        ImageView img = (ImageView) v.findViewById(R.id.custom_voucher_picture) ;

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

        title.setText(vouchers.get(position).getTitle());
        free_cost.setText("Tối đa " + Handle.kFortmatter(vouchers.get(position).getMoneyDeals().toString()));
        start_date.setText("NBD: " + formatDate.format(vouchers.get(position).getStartedAt()).toString());
        expiry_date.setText("HSD: " + formatDate.format(vouchers.get(position).getFinishedAt()).toString());
        img.setImageResource(R.drawable.img_voucher);

        return v;
    }
}
