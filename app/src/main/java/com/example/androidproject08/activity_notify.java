package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.androidproject08.activities.ChatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class activity_notify extends Activity implements View.OnClickListener {
    // khai báo biến xử lý
    ArrayList<Notification> listNotify = new ArrayList<>();
    String username;

    // khai báo biến UI
    ListView listNotification;
    RelativeLayout icon_home, icon_scan, icon_profile;
    View icon_cart, icon_chat;
    TextView number_cart;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");
    CollectionReference notifyRef = db.collection("notifications");

    // sqlite
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        icon_home = (RelativeLayout) findViewById(R.id.icon_home);
        icon_home.setOnClickListener(this);
        icon_scan = (RelativeLayout) findViewById(R.id.icon_scan);
        icon_scan.setOnClickListener(this);
        icon_profile = (RelativeLayout) findViewById(R.id.icon_profile);
        icon_profile.setOnClickListener(this);
        icon_cart = (View) findViewById(R.id.icon_cart);
        icon_cart.setOnClickListener(this);
        icon_chat = (View) findViewById(R.id.icon_chat);
        icon_chat.setOnClickListener(this);

        number_cart = (TextView) findViewById(R.id.number_cart);

        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
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

        listNotification = (ListView) findViewById(R.id.notify_listview);

        notify_asyntask n_at = new notify_asyntask();
        n_at.execute();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == icon_cart.getId()) {
            // chuyển sang giao diện my cart
            Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
            moveActivity.putExtra("name_activity", "activity_notify");
            startActivity(moveActivity);
        }

        if (view.getId() == icon_chat.getId()) {
            // chuyển sang giao diện chat
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            startActivity(intent);
        }

        if (view.getId() == icon_home.getId()) {
            // trở về giao diện dashboard
            Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
            startActivity(moveActivity);
        }

        if (view.getId() == icon_scan.getId()) {
            // chuyển sang giao diện scan mã qr
            Intent moveActivity = new Intent(getApplicationContext(), activity_scan_pay.class);
            startActivity(moveActivity);
        }

        if (view.getId() == icon_profile.getId()) {
            // chuyển sang giao diện profile
            Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
            startActivity(moveActivity);
        }
    }

    private class notify_asyntask extends AsyncTask<Void, Notification, Notification> {
        notify_asyntask() {
        }

        @Override
        protected Notification doInBackground(Void... voids) {
            try {
                notifyRef
                        .whereEqualTo("receiver", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        Notification notification = document.toObject(Notification.class);
                                        notification.setIdDoc(document.getId());

                                        publishProgress(notification);
                                    }
                                } else {
                                }
                            }
                        });
            } catch (Exception error) {
                return null;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Notification... notifications) {
            super.onProgressUpdate(notifications);
            listNotify.add(notifications[0]);

            SortArrayList(listNotify);

            CustomNotifyListViewAdapter myAdapter = new CustomNotifyListViewAdapter(getApplicationContext(), R.layout.custom_notify_listview, listNotify);
            listNotification.setAdapter(myAdapter);
        }
    }

    class sortCompare implements Comparator<Notification> {
        public int compare(Notification s1, Notification s2) {
            return s2.getDate().compareTo(s1.getDate());
        }
    }

    public void SortArrayList(ArrayList<Notification> notify){
        Collections.sort(notify, new sortCompare());
    }
}
