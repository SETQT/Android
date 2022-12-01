package com.example.androidproject08;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.GridView;

import java.util.ArrayList;


public class activity_search extends Activity {

    ArrayList<Product> finalList = new ArrayList<Product>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        GridView search_gridview = (GridView) findViewById(R.id.search_gridview);

        Product product = new Product("","Áo khoác mono", 100000, 10,10,"","",1,null, null);
        Product product1 = new Product("","Áo mono", 150000, 10,10,"","",1,null, null);

        finalList.add(product);finalList.add(product1);

        CustomProductLabelAdapter customAdapter = new CustomProductLabelAdapter(getApplicationContext(), R.layout.custom_product_gridview, finalList);
        search_gridview.setAdapter(customAdapter);

    }
}
