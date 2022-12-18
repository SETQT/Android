package com.example.androidproject08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomVoucherListViewAdapter extends ArrayAdapter<Voucher> {
    ArrayList<Voucher> vouchers = new ArrayList<Voucher>();
    Context curContext;

    public CustomVoucherListViewAdapter(Context context, int resource, ArrayList<Voucher> objects) {
        super(context, resource, objects);
        this.vouchers = objects;
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
        v = inflater.inflate(R.layout.custom_voucher_listview, null);

        TextView title = (TextView) v.findViewById(R.id.custom_voucher_title);
        TextView min_cost = (TextView) v.findViewById(R.id.custom_voucher_min_cost);
        TextView free_cost = (TextView) v.findViewById(R.id.custom_voucher_free_cost);
        TextView count = (TextView) v.findViewById(R.id.custom_voucher_count);
        TextView start_date = (TextView) v.findViewById(R.id.custom_voucher_start_date);
        TextView expiry_date = (TextView) v.findViewById(R.id.custom_voucher_expiry_date);

        ImageView img = (ImageView) v.findViewById(R.id.custom_voucher_picture) ;

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

        title.setText(vouchers.get(position).getTitle());
        min_cost.setText("Đơn tối thiểu " + Handle.kFortmatter(vouchers.get(position).getMinimumCost().toString()));
        free_cost.setText("Tối đa " + Handle.kFortmatter(vouchers.get(position).getMoneyDeals().toString()));
        count.setText("Số lượng: " + vouchers.get(position).getAmount().toString());
        start_date.setText("NBD: " + formatDate.format(vouchers.get(position).getStartedAt()).toString());
        expiry_date.setText("HSD: " + formatDate.format(vouchers.get(position).getFinishedAt()).toString());
        Picasso.with(curContext).load(vouchers.get(position).getImage()).into(img);

        return v;
    }
}