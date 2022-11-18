package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.CallbackManager;

import java.io.File;
import java.util.ArrayList;

public class activity_dashboard extends FragmentActivity implements MainCallbacks {
    // khai báo biến UI
    RelativeLayout icon_scan, icon_notify, icon_profile;
    View icon_cart;

    FragmentTransaction ft; DashboardFragmentFirst firstFrag; DashboardFragmentSecond secondFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = DashboardFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.dashboard_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = DashboardFragmentSecond.newInstance("");
        ft.replace(R.id.dashboard_fragment_second, secondFrag);
        ft.commit();


        icon_scan = (RelativeLayout) findViewById(R.id.icon_scan);
        icon_notify = (RelativeLayout) findViewById(R.id.icon_notify);
        icon_profile = (RelativeLayout) findViewById(R.id.icon_profile);

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
        icon_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện thông báo
        icon_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_notify.class);
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện profile
        icon_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
                startActivity(moveActivity);
            }
        });

    }

    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {
//        Toast.makeText(getApplication(), " MAIN GOT>> " + sender + "\n" + strValue, Toast.LENGTH_LONG).show();
        if (sender.equals("RED-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                firstFrag.onMsgFromMainToFragment( strValue);
            }
            catch (Exception e) { Log.e("ERROR", "onStrFromFragToMain " + e.getMessage()); }
        }
        if (sender.equals("BLUE-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                secondFrag.onMsgFromMainToFragment( strValue);
            }
            catch (Exception e) { Log.e("ERROR", "onStrFromFragToMain " + e.getMessage()); }
        }
    }
}

