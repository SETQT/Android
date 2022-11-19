package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;

public class activity_mycart extends Activity {
    // khai báo biến UI
    View icon_back;
    ListView listMyCart;

    // sqlite
    SQLiteDatabase sqlite;

    // biến xử lý

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        listMyCart = (ListView) findViewById(R.id.MyCart_listview);
        icon_back = (View) findViewById(R.id.icon_back);

        // get tên của activity trước đỏ để back khi nhấn button back lại đúng vị trí
        Intent intent = getIntent();
        String previousActivity = intent.getStringExtra("name_activity");

        // get username từ sqlite
        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        String username = c1.getString(0);

        if (username == null) {
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cartsRef = db.collection("carts");
        cartsRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                // query dữ liệu cho qua listview
                mycart_asynctask mc_at = new mycart_asynctask(activity_mycart.this, username);
                mc_at.execute();
            }
        });


        // trở lại activity trước đó
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent();

                switch (previousActivity) {
                    case "activity_dashboard":
                        moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                        break;
                    case "activity_notify":
                        moveActivity = new Intent(getApplicationContext(), activity_notify.class);
                        break;
                    case "activity_profile":
                        moveActivity = new Intent(getApplicationContext(), activity_profile.class);
                        break;
                    case "activity_voucher":
                        moveActivity = new Intent(getApplicationContext(), activity_voucher.class);
                        break;
                    case "activity_myorder":
                        moveActivity = new Intent(getApplicationContext(), activity_myorder.class);
                        break;
                    default:
                        break;
                }

                startActivity(moveActivity);
            }
        });
    }
}