package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class activity_profile extends Activity {

    //khai báo biến UI
    RelativeLayout icon_home, icon_scan, icon_notify;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");
    ArrayList<User> usernameList;

    // sqlite
    SQLiteDatabase sqlite;

    // khai báo biến UI
    TextView username_profile, id_cycle_red_giohang;
    Button dangxuat;
    View icon_cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        icon_home = (RelativeLayout) findViewById(R.id.icon_home);
        icon_scan = (RelativeLayout) findViewById(R.id.icon_scan);
        icon_notify = (RelativeLayout) findViewById(R.id.icon_notify);

        username_profile = (TextView) findViewById(R.id.username_profile);
        id_cycle_red_giohang = (TextView) findViewById(R.id.id_cycle_red_giohang);
        dangxuat = (Button) findViewById(R.id.dangxuat);
        icon_cart = (View) findViewById(R.id.icon_cart);

        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        String username = c1.getString(0);

        // kiểm tra với database
        usernameList = new ArrayList<>();

        // lấy thông tin người dùng
        readData(new FirestoreCallBack() {
            @Override
            public void onCallBack(List<User> list) {
                if (usernameList.get(0).getFullname() == null) {
                    username_profile.setText(usernameList.get(0).getUsername());
                } else {
                    username_profile.setText(usernameList.get(0).getFullname());
                }
            }
        }, username);

        // chuyển sang giao diện my cart
        icon_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
                moveActivity.putExtra("name_activity", "activity_profile");
                startActivity(moveActivity);
            }
        });

        // đăng xuất tài khoản
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlite.execSQL("DROP TABLE IF EXISTS USER; "); // xóa bảng <=> xóa phiên đăng nhập hiện tại
                Intent moveActivity = new Intent(getApplicationContext(), activity_login.class);
                startActivity(moveActivity);
            }
        });

        // trở về giao diện dashboard
        icon_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện scan mã qr
        icon_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện thông báo
        icon_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_notify.class);
                startActivity(moveActivity);
            }
        });
    }

    // truy vấn dữ liệu từ database với username mà người dùng nhập
    private void readData(FirestoreCallBack firestoreCallBack, String tk) {
        usersRef
                .whereEqualTo("username", tk)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                usernameList.add(user);
                            }

                            firestoreCallBack.onCallBack(usernameList);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
