package com.example.androidproject08;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class activity_voucher extends FragmentActivity implements MainCallbacks, View.OnClickListener{
    FragmentTransaction ft; VoucherFragmentFirst firstFrag; VoucherFragmentSecond secondFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = VoucherFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.voucher_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = VoucherFragmentSecond.newInstance("");
        ft.replace(R.id.voucher_fragment_second, secondFrag);
        ft.commit();
    }
    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {
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

    }
}