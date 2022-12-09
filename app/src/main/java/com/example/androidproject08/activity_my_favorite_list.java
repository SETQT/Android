package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class activity_my_favorite_list extends Activity implements View.OnClickListener {

    View icon_back, icon_cart;

    ListView listview_my_favorite;

    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> new_cost = new ArrayList<>();
    ArrayList<Integer> percent_sale = new ArrayList<>();

    ArrayList<Product> product = new ArrayList<Product>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite_list);

        listview_my_favorite = (ListView) findViewById(R.id.listview_my_favorite);

        name.add("Áo khoác hồng");
        new_cost.add(300000);
        percent_sale.add(20);

        Product product1 = new Product("1",name.get(0), new_cost.get(0),10,percent_sale.get(0),"","",5,null, null);
        product.add(product1);

        Log.i("TAG", "onCreate: "+ product.get(0).getName());

        CustomMyFavoriteListViewAdapter customAdapter = new CustomMyFavoriteListViewAdapter(this, R.layout.custom_my_favorite_list_listview, product);
        listview_my_favorite.setAdapter(customAdapter);

    }

    @Override
    public void onClick(View view){
        if (view.getId() == icon_back.getId()){
            Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
            startActivity(moveActivity);
        }

        if (view.getId() == icon_cart.getId()){
            Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
            startActivity(moveActivity);
        }
    }

}
