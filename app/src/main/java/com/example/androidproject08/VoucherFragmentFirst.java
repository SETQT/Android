package com.example.androidproject08;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class VoucherFragmentFirst extends Fragment implements View.OnClickListener {
    // this fragment shows a ListView
    activity_voucher main;
    String message = "";

    // khai báo biến UI
    TextView txtMsg, number_cart;
    TextView voucher_option_tat_ca, voucher_option_shop, voucher_option_free_ship;
    View icon_back, icon_cart;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    // sqlite
    SQLiteDatabase sqlite;

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static VoucherFragmentFirst newInstance(String strArg) {
        VoucherFragmentFirst fragment = new VoucherFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_voucher) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.custom_voucher_layout_fragment_first, null);

        number_cart = (TextView) layout_first.findViewById(R.id.number_cart);
        voucher_option_tat_ca = (TextView) layout_first.findViewById(R.id.voucher_option_tat_ca);
        voucher_option_shop = (TextView) layout_first.findViewById(R.id.voucher_option_shop);
        voucher_option_free_ship = (TextView) layout_first.findViewById(R.id.voucher_option_free_ship);
        icon_back = (View) layout_first.findViewById(R.id.icon_back);
        icon_cart = (View) layout_first.findViewById(R.id.icon_cart);
        icon_cart.setOnClickListener(this);
        icon_back.setOnClickListener(this);
        voucher_option_shop.setOnClickListener(this);
        voucher_option_tat_ca.setOnClickListener(this);
        voucher_option_free_ship.setOnClickListener(this);
        voucher_option_tat_ca.setTextAppearance(getActivity(), R.style.setTextAfterClick);

        try {
            // kết nối sqlite
            File storagePath = main.getFilesDir();
            String myDbPath = storagePath + "/" + "loginDb";
            sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

            String mySQL = "select * from USER";
            Cursor c1 = sqlite.rawQuery(mySQL, null);
            c1.moveToPosition(0);
            String username = c1.getString(0);

            // lấy số lượng sản phẩm
            usersRef.whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    number_cart.setText(user.getCart().get("amount").toString());
                                    break;
                                }
                            }
                        }
                    });
        }catch (Exception error) {
            Log.e("ERROR", "VoucherFragmentFirst onCreateView: ", error);
        }

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == icon_back.getId()) {
            Intent moveActivity = new Intent(main, activity_profile.class);
            startActivity(moveActivity);
        }

        if(view.getId() == voucher_option_tat_ca.getId()) {
            voucher_option_tat_ca.setTextAppearance(getActivity(), R.style.setTextAfterClick);
            voucher_option_shop.setTextAppearance(getActivity(), R.style.setTextNotClick);
            voucher_option_free_ship.setTextAppearance(getActivity(), R.style.setTextNotClick);
            String dataSend = "All";
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }

        if(view.getId() == voucher_option_shop.getId()) {
            voucher_option_tat_ca.setTextAppearance(getActivity(), R.style.setTextNotClick);
            voucher_option_shop.setTextAppearance(getActivity(), R.style.setTextAfterClick);
            voucher_option_free_ship.setTextAppearance(getActivity(), R.style.setTextNotClick);
            String dataSend = "Shop";
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }

        if(view.getId() == voucher_option_free_ship.getId()) {
            voucher_option_tat_ca.setTextAppearance(getActivity(), R.style.setTextNotClick);
            voucher_option_shop.setTextAppearance(getActivity(), R.style.setTextNotClick);
            voucher_option_free_ship.setTextAppearance(getActivity(), R.style.setTextAfterClick);
            String dataSend = "FreeShip";
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }

        if(view.getId() == icon_cart.getId()) {
            Intent moveActivity = new Intent(main, activity_mycart.class);
            moveActivity.putExtra("name_activity", "activity_voucher");
            startActivity(moveActivity);
        }
    }
}// class
