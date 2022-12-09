package com.example.androidproject08;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class activity_myorder extends FragmentActivity implements MainCallbacks {
    FragmentTransaction ft;
    MyorderFragmentFirst firstFrag;
    MyorderFragmentSecond secondFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = MyorderFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.voucher_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = MyorderFragmentSecond.newInstance("");
        ft.replace(R.id.voucher_fragment_second, secondFrag);
        ft.commit();

        Intent intent = getIntent();

        if(intent.hasExtra("stateMyOrder")) {
            String stateMyOrder = intent.getStringExtra("stateMyOrder");
            firstFrag.onMsgFromMainToFragment(stateMyOrder);
            secondFrag.onMsgFromMainToFragment(stateMyOrder);
        }
    }

    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {
        if (sender.equals("RED-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                firstFrag.onMsgFromMainToFragment(strValue);
            } catch (Exception e) {
                Log.e("ERROR", "onStrFromFragToMain " + e.getMessage());
            }
        }
        if (sender.equals("BLUE-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                secondFrag.onMsgFromMainToFragment(strValue);
            } catch (Exception e) {
                Log.e("ERROR", "onStrFromFragToMain " + e.getMessage());
            }
        }
    }
}