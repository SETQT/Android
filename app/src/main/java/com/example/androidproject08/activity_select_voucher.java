package com.example.androidproject08;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class activity_select_voucher extends FragmentActivity implements MainCallbacks, View.OnClickListener {
    FragmentTransaction ft;
    SelectVoucherFragmentFirst firstFrag;
    SelectVoucherFragmentSecond secondFrag;

    // biến UI
    View icon_back;

    // biến xử lý
    ArrayList<Myorder> ListOrderArray = new ArrayList<>();
    Voucher usedVoucher;

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

        Intent intent = getIntent();
        ListOrderArray = (ArrayList<Myorder>) intent.getExtras().getSerializable("products");
        if(intent.hasExtra("voucher")) {
            usedVoucher = (Voucher) intent.getExtras().getSerializable("voucher");
            firstFrag.onObjectFromMainToFragment(usedVoucher);
            secondFrag.onObjectFromMainToFragment(usedVoucher);
        }
        firstFrag.onObjectFromMainToFragment(ListOrderArray);
        secondFrag.onObjectFromMainToFragment(ListOrderArray);
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

    @Override
    public void onObjectFromFragToMain(String sender, Object value) {
        if (sender.equals("RED-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                firstFrag.onObjectFromMainToFragment(value);
            } catch (Exception e) {
                Log.e("ERROR", "onStrFromFragToMain " + e.getMessage());
            }
        }
        if (sender.equals("BLUE-FRAG")) {
            try { // forward blue-data to redFragment using its callback method
                secondFrag.onObjectFromMainToFragment(value);
            } catch (Exception e) {
                Log.e("ERROR", "onStrFromFragToMain " + e.getMessage());
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == icon_back.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_payment.class);
            if(usedVoucher != null) {
                moveActivity.putExtra("voucher", usedVoucher);
            }
            moveActivity.putExtra("products", ListOrderArray);
            moveActivity.putExtra("name_activity", "activity_select_voucher");
            startActivity(moveActivity);
        }
    }
}