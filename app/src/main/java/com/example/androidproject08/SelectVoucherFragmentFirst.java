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

public class SelectVoucherFragmentFirst extends Fragment implements View.OnClickListener {
    // this fragment shows a ListView
    activity_select_voucher main;

    // khai báo biến UI
    EditText edittext_select_voucher;
    View icon_search;
    Button btn_confirm_select_voucher;

    // kết nối firestore
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference usersRef = db.collection("users");
//
//    // sqlite
//    SQLiteDatabase sqlite;

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

        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataSend = edittext_select_voucher.getText().toString();
                main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
            }
        });

        btn_confirm_select_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_confirm_select_voucher.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                Intent moveActivity = new Intent(main, activity_payment.class);
                startActivity(moveActivity);
            }
        });
        //btn_confirm_select_voucher.setOnClickListener(this);

//        try {
//            // kết nối sqlite
//            File storagePath = main.getFilesDir();
//            String myDbPath = storagePath + "/" + "loginDb";
//            sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db
//
//            String mySQL = "select * from USER";
//            Cursor c1 = sqlite.rawQuery(mySQL, null);
//            c1.moveToPosition(0);
//            String username = c1.getString(0);
//
//            // lấy số lượng sản phẩm
//            usersRef.whereEqualTo("username", username)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if(task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    User user = document.toObject(User.class);
//                                    number_cart.setText(user.getCart().get("amount").toString());
//                                    break;
//                                }
//                            }
//                        }
//                    });
//        }catch (Exception error) {
//            Log.e("ERROR", "VoucherFragmentFirst onCreateView: ", error);
//        }

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        String dataSend = strValue;
        main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
    }

    @Override
    public void onClick(View view) {
//        if(view.getId() == btn_confirm_select_voucher.getId()) {
//            btn_confirm_select_voucher.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
//            String dataSend = edittext_select_voucher.getText().toString();
//            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
//        }
    }
}// class
