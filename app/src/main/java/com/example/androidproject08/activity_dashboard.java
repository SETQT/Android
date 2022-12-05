package com.example.androidproject08;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidproject08.activities.ChatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;

public class activity_dashboard extends FragmentActivity implements MainCallbacks, View.OnClickListener {
    // khai báo biến UI
    RelativeLayout icon_scan, icon_notify, icon_profile;
    View icon_cart, icon_search, icon_chat;
    TextView number_cart;
    EditText dashboard_id_search;
    FragmentTransaction ft;
    DashboardFragmentFirst firstFrag;
    DashboardFragmentSecond secondFrag;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    // sqlite
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ft = getSupportFragmentManager().beginTransaction();
        firstFrag = DashboardFragmentFirst.newInstance("first-frag");
        ft.replace(R.id.dashboard_fragment_first, firstFrag);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        secondFrag = DashboardFragmentSecond.newInstance("");
        ft.replace(R.id.dashboard_fragment_second, secondFrag);
        ft.commit();

        icon_scan = (RelativeLayout) findViewById(R.id.icon_scan);
        icon_scan.setOnClickListener(this);
        icon_notify = (RelativeLayout) findViewById(R.id.icon_notify);
        icon_notify.setOnClickListener(this);
        icon_profile = (RelativeLayout) findViewById(R.id.icon_profile);
        icon_profile.setOnClickListener(this);
        icon_cart = (View) findViewById(R.id.icon_cart);
        icon_cart.setOnClickListener(this);
        icon_search = (View) findViewById(R.id.icon_search);
        icon_search.setOnClickListener(this);
        icon_chat = (View) findViewById(R.id.icon_chat);
        icon_chat.setOnClickListener(this);

        dashboard_id_search = (EditText) findViewById(R.id.dashboard_id_search);
        number_cart = (TextView) findViewById(R.id.number_cart);

        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
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
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                number_cart.setText(user.getCart().get("amount").toString());
                                break;
                            }
                        }
                    }
                });
    }

    @Override
    public void onMsgFromFragToMain(String sender, String strValue) {
//        Toast.makeText(getApplication(), " MAIN GOT>> " + sender + "\n" + strValue, Toast.LENGTH_LONG).show();
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
    public void onClick(View view) {
        // chuyển sang giao diện my cart
        if (view.getId() == icon_cart.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
            moveActivity.putExtra("name_activity", "activity_dashboard");
            startActivity(moveActivity);
        }

        // chuyển sang giao diện scan mã qr
        if (view.getId() == icon_scan.getId()) {
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

        // Chuyển sang giao diện search + hiển thị kết quả
        if(view.getId() == icon_search.getId()) {
            String dataSearch = dashboard_id_search.getText().toString();

            Intent moveActivity = new Intent(getApplicationContext(), activity_search.class);
            moveActivity.putExtra("data_search", dataSearch);
            startActivity(moveActivity);
        }

        // Chuyển sang giao diện chat
        if(view.getId() == icon_chat.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), ChatActivity.class);
            startActivity(moveActivity);
        }

    }
}

