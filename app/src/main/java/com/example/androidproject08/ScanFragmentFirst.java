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
import android.widget.RelativeLayout;
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

public class ScanFragmentFirst extends Fragment implements View.OnClickListener{
    activity_scan_pay main;

    // khai báo biến UI
    TextView scan_pay_option_momo, scan_pay_option_zalopay, scan_pay_option_banking;

    // kết nối firestore
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    CollectionReference usersRef = db.collection("users");

    // sqlite
    SQLiteDatabase sqlite;

    // convenient constructor(accept arguments, copy them to a bundle, binds bundle to fragment)
    public static ScanFragmentFirst newInstance(String strArg) {
        ScanFragmentFirst fragment = new ScanFragmentFirst();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            main = (activity_scan_pay) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout_first = (RelativeLayout) inflater.inflate(R.layout.custom_scan_pay_layout_fragment_first, null);

        scan_pay_option_momo = (TextView) layout_first.findViewById(R.id.scan_pay_option_momo);
        scan_pay_option_zalopay = (TextView) layout_first.findViewById(R.id.scan_pay_option_zalopay);
        scan_pay_option_banking = (TextView) layout_first.findViewById(R.id.scan_pay_option_banking);

        scan_pay_option_momo.setOnClickListener(this);
        scan_pay_option_zalopay.setOnClickListener(this);
        scan_pay_option_banking.setOnClickListener(this);
        scan_pay_option_momo.setTextAppearance(getActivity(), R.style.setTextAfterClick);

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

        if(view.getId() == scan_pay_option_momo.getId()) {
            scan_pay_option_momo.setTextAppearance(getActivity(), R.style.setTextAfterClick);
            scan_pay_option_zalopay.setTextAppearance(getActivity(), R.style.setTextNotClick);
            scan_pay_option_banking.setTextAppearance(getActivity(), R.style.setTextNotClick);
            String dataSend = "momo";
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }

        if(view.getId() == scan_pay_option_zalopay.getId()) {
            scan_pay_option_momo.setTextAppearance(getActivity(), R.style.setTextNotClick);
            scan_pay_option_zalopay.setTextAppearance(getActivity(), R.style.setTextAfterClick);
            scan_pay_option_banking.setTextAppearance(getActivity(), R.style.setTextNotClick);
            String dataSend = "zalopay";
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }

        if(view.getId() == scan_pay_option_banking.getId()) {
            scan_pay_option_momo.setTextAppearance(getActivity(), R.style.setTextNotClick);
            scan_pay_option_zalopay.setTextAppearance(getActivity(), R.style.setTextNotClick);
            scan_pay_option_banking.setTextAppearance(getActivity(), R.style.setTextAfterClick);
            String dataSend = "banking";
            main.onMsgFromFragToMain("BLUE-FRAG", dataSend);
        }

    }
}
