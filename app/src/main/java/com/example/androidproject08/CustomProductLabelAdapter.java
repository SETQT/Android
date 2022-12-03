package com.example.androidproject08;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CustomProductLabelAdapter extends ArrayAdapter<Product> {
    ArrayList<Product> products;
    Context curContext;
    String nameActivity;

    public CustomProductLabelAdapter(Context context, int resource, ArrayList<Product> objects, String nameActivity) {
        super(context, resource, objects);
        this.products = objects;
        this.curContext = context;
        this.nameActivity = nameActivity;
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
        old_price.setText("đ" + products.get(position).getPrice().toString());
        price.setText("đ" + oldPrice.toString());
        percent_sale.setText("-"+products.get(position).getSale().toString() + "%");

        Picasso.with(curContext).load(products.get(position).getImage()).into(picture);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(curContext, activity_view_product.class);
                moveActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                moveActivity.putExtra("name_activity", nameActivity);
                moveActivity.putExtra("idDoc", products.get(position).getIdDoc());
                curContext.startActivity(moveActivity);
            }
        });

        return v;
    }
}