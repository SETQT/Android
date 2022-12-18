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

public class MyorderFragmentFirst extends Fragment implements View.OnClickListener {
    // this fragment shows a ListView
    activity_myorder main;

    // khai báo biến UI
    TextView myorder_option_cho_xac_nhan, myorder_option_dang_giao_hang, myorder_option_da_giao, number_cart;
    View icon_back, icon_cart;

    // sqlite
    SQLiteDatabase sqlite;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    // biến xử lý
    String username;
    String stateMyOder;

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static MyorderFragmentFirst newInstance(String strArg) {
        MyorderFragmentFirst fragment = new MyorderFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_myorder) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_first = (LinearLayout) inflater.inflate(R.layout.custom_myorder_layout_fragment_first, null);

        icon_back = (View) layout_first.findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);
        icon_cart = (View) layout_first.findViewById(R.id.icon_cart);
        icon_cart.setOnClickListener(this);
        number_cart = (TextView) layout_first.findViewById(R.id.number_cart);

        myorder_option_cho_xac_nhan = (TextView) layout_first.findViewById(R.id.myorder_option_cho_xac_nhan);
        myorder_option_cho_xac_nhan.setOnClickListener(this);
        myorder_option_dang_giao_hang = (TextView) layout_first.findViewById(R.id.myorder_option_dang_giao_hang);
        myorder_option_dang_giao_hang.setOnClickListener(this);
        myorder_option_da_giao = (TextView) layout_first.findViewById(R.id.myorder_option_da_giao);
        myorder_option_da_giao.setOnClickListener(this);
        myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextAfterClick);

        // kết nối sqlite
        File storagePath = main.getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        username = c1.getString(0);

        // lấy số lượng sản phẩm
        usersRef.whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                number_cart.setText(user.getCart().get("amount").toString());
                                break;
                            }
                        }
                    }
                });

        if(stateMyOder != null) {
            switch (stateMyOder) {
                case "1":
                    myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                    myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    break;
                case "2":
                    myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                    myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    break;
                case "3":
                    myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
                    myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextAfterClick);
                    break;
                default:
                    break;
            }
        }

        return layout_first;
    }// onCreateView

    public void onMsgFromMainToFragment(String strValue) {
        stateMyOder = strValue;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == icon_back.getId()) {
            // chuyển về giao diện profile
            Intent moveActivity = new Intent(getActivity(), activity_profile.class);
            startActivity(moveActivity);
        }

        if (view.getId() == icon_cart.getId()) {
            // chuyển đến giao diện my cart
            Intent moveActivity = new Intent(getActivity(), activity_mycart.class);
            moveActivity.putExtra("name_activity", "activity_myorder");
            startActivity(moveActivity);
        }

        if (view.getId() == myorder_option_cho_xac_nhan.getId()) {
            myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextAfterClick);
            myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
            myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
            String dataSend = "1";
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }

        if (view.getId() == myorder_option_dang_giao_hang.getId()) {
            myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
            myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextAfterClick);
            myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextNotClick);
            String dataSend = "2";
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }

        if (view.getId() == myorder_option_da_giao.getId()) {
            myorder_option_cho_xac_nhan.setTextAppearance(getActivity(), R.style.setTextNotClick);
            myorder_option_dang_giao_hang.setTextAppearance(getActivity(), R.style.setTextNotClick);
            myorder_option_da_giao.setTextAppearance(getActivity(), R.style.setTextAfterClick);
            String dataSend = "3";
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }
    }
}// class
