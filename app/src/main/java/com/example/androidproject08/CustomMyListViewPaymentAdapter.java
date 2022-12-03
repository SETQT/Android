package com.example.androidproject08;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomMyListViewPaymentAdapter extends ArrayAdapter<Myorder> {

    ArrayList<Myorder> orders = new ArrayList<>();
    Context curContext;

    public CustomMyListViewPaymentAdapter(Context context, int resource, ArrayList<Myorder> objects) {
        super(context, resource, objects);
        this.orders = objects;
        this.curContext = context;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        name.setText(orders.get(position).getName());
        old_cost.setText("đ" + orders.get(position).getOldCost().toString());
        new_cost.setText("đ" + orders.get(position).getNewCost().toString());
        number.setText("Số lượng: " + orders.get(position).getCount().toString());
        size_color.setText("Kích thước: " + orders.get(position).getSize()+", Màu sắc: "+orders.get(position).getColor());
        charge_tranfer.setText("đ" + 30000);
        cost_final.setText("đ" + orders.get(position).getTotal().toString());

        Picasso.with(curContext).load(orders.get(position).getImage()).into(img);
        return v;
    }
}