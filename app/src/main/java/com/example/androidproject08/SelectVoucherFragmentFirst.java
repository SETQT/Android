package com.example.androidproject08;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;

public class SelectVoucherFragmentFirst extends Fragment implements View.OnClickListener, FragmentCallbacks {
    // this fragment shows a ListView
    activity_select_voucher main;

    // khai báo biến UI
    EditText edittext_select_voucher;
    View icon_search;
    Button btn_confirm_select_voucher;

    // biến xử lý
    ArrayList<Myorder> ListOrderArray = new ArrayList<>();
    Voucher usedVoucher;

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static SelectVoucherFragmentFirst newInstance(String strArg) {
        SelectVoucherFragmentFirst fragment = new SelectVoucherFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_select_voucher) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.custom_select_voucher_layout_fragment_first, null);

        edittext_select_voucher = (EditText) layout_first.findViewById(R.id.edittext_select_voucher);
        btn_confirm_select_voucher = (Button) layout_first.findViewById(R.id.btn_confirm_select_voucher);
        icon_search = (View) layout_first.findViewById(R.id.icon_search);
        icon_search.setOnClickListener(this);
        btn_confirm_select_voucher.setOnClickListener(this);

        return layout_first;
    }// onCreateView

    @Override
    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
    }

    @Override
    public void onObjectFromMainToFragment(Object value) {
        if(value != null) {
            if(value.getClass() == Voucher.class) {
                usedVoucher = (Voucher) value;
            }else {
                ListOrderArray = (ArrayList<Myorder>) value;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == icon_search.getId()) {
            String dataSend = edittext_select_voucher.getText().toString();
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }

        if(view.getId() == btn_confirm_select_voucher.getId()) {
            btn_confirm_select_voucher.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
            Intent moveActivity = new Intent(main, activity_payment.class);

            if(usedVoucher != null) {
                moveActivity.putExtra("voucher", usedVoucher);
            }

            moveActivity.putExtra("products", ListOrderArray);
            moveActivity.putExtra("name_activity", "activity_select_voucher");
            startActivity(moveActivity);
        }
    }
}// class
