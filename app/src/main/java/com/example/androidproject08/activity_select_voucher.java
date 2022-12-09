package com.example.androidproject08;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class activity_select_voucher extends FragmentActivity implements MainCallbacks, View.OnClickListener{
    FragmentTransaction ft; SelectVoucherFragmentFirst firstFrag; SelectVoucherFragmentSecond secondFrag;

    View icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_voucher);

        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = SelectVoucherFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.select_voucher_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = SelectVoucherFragmentSecond.newInstance("");
        ft.replace(R.id.select_voucher_fragment_second, secondFrag);
        ft.commit();
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
    public void onClick(View view) {
        if(view.getId() == icon_back.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_payment.class);
            startActivity(moveActivity);
        }

    }
}