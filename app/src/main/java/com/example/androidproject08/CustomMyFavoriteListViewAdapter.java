package com.example.androidproject08;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomMyFavoriteListViewAdapter extends ArrayAdapter<Product> {

    ArrayList<Product> products;
    Context curContext;

    public CustomMyFavoriteListViewAdapter(Context context, int resource, ArrayList<Product> objects) {
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
        v = inflater.inflate(R.layout.custom_my_favorite_list_gridview, null);

        ImageView picture = (ImageView) v.findViewById(R.id.custom_favorite_picture); // get the reference of ImageView
        TextView name = (TextView) v.findViewById(R.id.custom_favorite_name_product);
        TextView price = (TextView) v.findViewById(R.id.custom_favorite_new_cost_product);
        TextView old_price = (TextView) v.findViewById(R.id.custom_favorite_old_cost_product);
        TextView percent_sale = (TextView) v.findViewById(R.id.custom_favorite_percent);

        Integer oldPrice = (products.get(position).getPrice() / (100 - products.get(position).getSale())) * 100;

        name.setText(products.get(position).getName());
        old_price.setText("đ" + products.get(position).getPrice().toString());
        price.setText("đ" + oldPrice.toString());
        percent_sale.setText("-"+products.get(position).getSale().toString() + "%");
         Picasso.with(curContext).load(products.get(position).getImage()).into(picture);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(curContext, activity_view_product.class);
                moveActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                moveActivity.putExtra("idDoc", products.get(position).getIdDoc());
                curContext.startActivity(moveActivity);
            }
        });

        return v;
    }

}
