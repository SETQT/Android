package com.example.androidproject08;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class activity_scan_pay extends FragmentActivity implements MainCallbacks, View.OnClickListener{
    FragmentTransaction ft; ScanFragmentFirst firstFrag; ScanFragmentSecond secondFrag;
    RelativeLayout icon_home, icon_notify, icon_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_pay);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = ScanFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.scan_pay_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = ScanFragmentSecond.newInstance("");
        ft.replace(R.id.scan_pay_fragment_second, secondFrag);
        ft.commit();

        icon_home = (RelativeLayout) findViewById(R.id.icon_home);
        icon_home.setOnClickListener(this);
        icon_notify = (RelativeLayout) findViewById(R.id.icon_notify);
        icon_notify.setOnClickListener(this);
        icon_profile = (RelativeLayout) findViewById(R.id.icon_profile);
        icon_profile.setOnClickListener(this);
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

    @Override
    public void onObjectFromFragToMain(String sender, Object value) {

    }

    @Override
    public void onClick(View view) {
        // chuyển sang giao diện home
        if (view.getId() == icon_home.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
            startActivity(moveActivity);
        }

        // chuyển sang giao diện thông báo
        if (view.getId() == icon_notify.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_notify.class);
            startActivity(moveActivity);
        }

        // chuyển sang giao diện profile
        if (view.getId() == icon_profile.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
            startActivity(moveActivity);
        }
    }

}
