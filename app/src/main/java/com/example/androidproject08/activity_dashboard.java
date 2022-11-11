package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import java.io.File;

public class activity_dashboard extends Activity {
    GridView gridview;
    int logos[] = {R.drawable.mono1, R.drawable.ao1, R.drawable.ao2, R.drawable.mono1, R.drawable.ao1, R.drawable.ao2, R.drawable.mono1, R.drawable.ao1, R.drawable.mono1, R.drawable.ao2};
    String names[] = {"Áo khoác cực chất", "Waiting for you", "Áo khoác cực chất", "Waiting for you", "Áo khoác cực chất", "Waiting for you", "Áo khoác cực chất", "Waiting for you",
            "Áo khoác cực chất", "Waiting for you"};
    String costs[] = {"đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000"};
    String costs_sale[] = {"đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000"};
    String percent_sale[] = {"-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%"};

    // khai báo biến UI
    View dashboard_ic_scan, dashboard_ic_notify, dashboard_ic_profile, icon_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard_ic_scan = (View) findViewById(R.id.icon_scan);
        dashboard_ic_notify = (View) findViewById(R.id.icon_notify);
        dashboard_ic_profile = (View) findViewById(R.id.icon_profile);
        icon_cart = (View) findViewById(R.id.icon_cart);

        // chuyển sang giao diện my cart
        icon_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
                moveActivity.putExtra("name_activity", "activity_dashboard");
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện scan mã qr
        dashboard_ic_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện thông báo
        dashboard_ic_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_notify.class);
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện profile
        dashboard_ic_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
                startActivity(moveActivity);
            }
        });

        gridview = (GridView) findViewById(R.id.dashboard_gridview);
        CustomProductLabelAdapter customAdapter = new CustomProductLabelAdapter(getApplicationContext(), logos, names, costs, costs_sale, percent_sale);
        gridview.setAdapter(customAdapter);
    }
}
