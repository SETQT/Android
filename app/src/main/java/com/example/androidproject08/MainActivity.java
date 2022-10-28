package com.example.androidproject08;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    /*GridView gridview;
    int logos[] = {R.drawable.mono1, R.drawable.ao1, R.drawable.ao2, R.drawable.mono1, R.drawable.ao1, R.drawable.ao2, R.drawable.mono1, R.drawable.ao1, R.drawable.mono1, R.drawable.ao2};
    String names[] = {"Áo khoác cực chất","Waiting for you", "Áo khoác cực chất","Waiting for you", "Áo khoác cực chất","Waiting for you", "Áo khoác cực chất","Waiting for you",
            "Áo khoác cực chất","Waiting for you"};
    String costs[] = {"đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000"};
    String costs_sale[] = {"đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000"};
    String percent_sale[] = {"-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        gridview = (GridView) findViewById(R.id.gridview);

        CustomProductLabelAdapter customAdapter = new CustomProductLabelAdapter(getApplicationContext(), logos, names, costs, costs_sale, percent_sale);
        gridview.setAdapter(customAdapter);

    }*/

    ListView listNotification;
    ArrayList<Notify> notifiesArray =new ArrayList<Notify>();
    String[] content ={
            "đã được giao thành công"
    };

    String[] product ={
            "Áo thun cực chất"
    };
    String[] title ={
            "Giao kiện hàng thành công"
    };

    String[] date ={
            "09:54 12-10-2022"
    };

    Integer[] image={
            R.drawable.mono1

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_cart);
        setContentView(R.layout.activity_notify);


        listNotification = (ListView) findViewById(R.id.idListView);

        for (int i=0;i<10;i++){
            notifiesArray.add(new Notify(i,title[0],content[0], product[0],date[0],image[0]));
        }
        CustomNotifyListViewAdapter myAdapter=new CustomNotifyListViewAdapter(this,R.layout.custom_notify_listview,notifiesArray);
        listNotification.setAdapter(myAdapter);

    }
}