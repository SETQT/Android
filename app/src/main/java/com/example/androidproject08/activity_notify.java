package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.androidproject08.activities.ChatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.ArrayList;

public class activity_notify extends Activity {
    ListView listNotification;
    ArrayList<Notify> notifiesArray = new ArrayList<>();

    Integer[] CheckProduct = {1, 1, 1, 0, 1, 0, 1, 1};
    String[] title = {"Đang giao hàng", "Giao kiện hàng thành công", "Giao kiện hàng không thành công", "Mã quà tặng từ shop",
            "Giao kiện hàng không thành công", "Mã miễn phí vận chuyển", "Giao kiện hàng thành công", "Giao kiện hàng thành công"};
    Integer[] status = {2, 1, 0, 1, 0, 1, 1, 1};
    String[] product = {"Áo thun cực chất", "Áo MU", "Giày độn", "", "Áo khoác", "", "Mũ lưỡi trai", "Áo len"};
    String[] content = {"", "", "", "Bạn nhận được mã voucher là 3TMAIDINH, mã voucher có giá trị sử dụng đến ngày 31-10-2022", "", "Bạn có mã freeship là GROUP8MOBILE, vui lòng sử dụng trong vòng 5 ngày", "", ""};
    String[] date = {"09:54 12-10-2022", "06:50 15-10-2022", "09:04 18-10-2022", "16:35 22-10-2022"
            , "09:00 23-10-2022", "16:35 24-10-2022", "08:20 25-10-2022", "16:47 26-10-2022"};
    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1,
            R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1};

    // khai báo biến UI
    RelativeLayout icon_home, icon_scan, icon_profile;

    View icon_cart, icon_chat;
    TextView number_cart;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    // sqlite
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);


        listNotification = (ListView) findViewById(R.id.notify_listview);

        for (int i = 0; i < 7; i++) {
            notifiesArray.add(new Notify(i, CheckProduct[i], title[i], status[i], product[i], date[i], content[i], image[i]));
        }
        CustomNotifyListViewAdapter myAdapter = new CustomNotifyListViewAdapter(this, R.layout.custom_notify_listview, notifiesArray);
        listNotification.setAdapter(myAdapter);

        icon_home = (RelativeLayout) findViewById(R.id.icon_home);
        icon_scan = (RelativeLayout) findViewById(R.id.icon_scan);
        icon_profile = (RelativeLayout) findViewById(R.id.icon_profile);

        icon_cart = (View) findViewById(R.id.icon_cart);
        icon_chat = (View) findViewById(R.id.icon_chat);

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
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                number_cart.setText(user.getCart().get("amount").toString());
                                break;
                            }
                        }
                    }
                });

        // chuyển sang giao diện my cart
        icon_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
                moveActivity.putExtra("name_activity", "activity_notify");
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện chat
        icon_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent);
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

        // chuyển sang giao diện profile
        icon_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
                startActivity(moveActivity);
            }
        });
    }
}
