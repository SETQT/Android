package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.checkerframework.checker.units.qual.A;

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
    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.notify_img_voucher,
            R.drawable.mono1, R.drawable.notify_img_voucher, R.drawable.mono1, R.drawable.mono1};

    // khai báo biến UI
    View dashboard_ic_home, dashboard_ic_scan, dashboard_ic_profile;

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

        dashboard_ic_home = (View) findViewById(R.id.icon_home);
        dashboard_ic_scan = (View) findViewById(R.id.icon_scan);
        dashboard_ic_profile = (View) findViewById(R.id.icon_profile);

        // trở về giao diện dashboard
        dashboard_ic_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện scan mã qr
        dashboard_ic_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                startActivity(moveActivity);
            }
        });

        // chuyển sang giao diện profile
        dashboard_ic_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
                startActivity(moveActivity);
            }
        });
    }
}
