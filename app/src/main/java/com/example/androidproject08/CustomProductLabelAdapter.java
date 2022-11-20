package com.example.androidproject08;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomProductLabelAdapter extends ArrayAdapter<Product> {
    ArrayList<Product> products;
    Context curContext;

    public CustomProductLabelAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        this.products = objects;
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
        v = inflater.inflate(R.layout.custom_product_gridview, null);

        ImageView picture = (ImageView) v.findViewById(R.id.dashboard_custom_picture_product); // get the reference of ImageView
        TextView name = (TextView) v.findViewById(R.id.dashboard_custom_name_product);
        TextView price = (TextView) v.findViewById(R.id.dashboard_custom_cost_product);
        TextView old_price = (TextView) v.findViewById(R.id.dashboard_custom_cost_sale_product);
        TextView percent_sale = (TextView) v.findViewById(R.id.dashboard_custom_percent_sale_product);

        Integer oldPrice = (products.get(position).getPrice() / (100 - products.get(position).getSale())) * 100;

        name.setText(products.get(position).getName());
        price.setText("đ" + products.get(position).getPrice().toString());
        old_price.setText("đ" + oldPrice.toString());
        percent_sale.setText("-"+products.get(position).getSale().toString() + "%");

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(curContext, activity_view_product.class);
                moveActivity.putExtra("idDoc", products.get(position).getIdDoc());
                curContext.startActivity(moveActivity);
            }
        });

        return v;
    }
}