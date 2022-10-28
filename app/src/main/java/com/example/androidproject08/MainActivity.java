package com.example.androidproject08;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    /*GridView gridview;
    int logos[] = {R.drawable.mono1, R.drawable.ao1, R.drawable.ao2, R.drawable.mono1, R.drawable.ao1, R.drawable.ao2, R.drawable.mono1, R.drawable.ao1, R.drawable.mono1, R.drawable.ao2};
    String names[] = {"Áo khoác cực chất","Waiting for you", "Áo khoác cực chất","Waiting for you", "Áo khoác cực chất","Waiting for you", "Áo khoác cực chất","Waiting for you",
            "Áo khoác cực chất","Waiting for you"};
    String costs[] = {"đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000", "đ100.000", "đ500.000"};
    String costs_sale[] = {"đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000", "đ80.000", "đ400.000"};
    String percent_sale[] = {"-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%", "-20%"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        gridview = (GridView) findViewById(R.id.gridview);
        CustomProductLabelAdapter customAdapter = new CustomProductLabelAdapter(getApplicationContext(), logos, names, costs, costs_sale, percent_sale);
        gridview.setAdapter(customAdapter);
    }*/

    ListView listNotification;
    ArrayList<Notify> notifiesArray =new ArrayList<Notify>();

    Integer[] CheckProduct = {1, 1, 1, 0, 1, 0, 1, 1};
    String[] title = {"Đang giao hàng", "Giao kiện hàng thành công","Giao kiện hàng không thành công","Mã quà tặng từ shop",
            "Giao kiện hàng không thành công", "Mã miễn phí vận chuyển", "Giao kiện hàng thành công", "Giao kiện hàng thành công"};
    Integer[] status = {2,1,0,1,0,1,1,1};
    String[] product = {"Áo thun cực chất", "Áo MU", "Giày độn", "", "Áo khoác","", "Mũ lưỡi trai", "Áo len"};
    String[] content = {"","","","Bạn nhận được mã voucher là 3TMAIDINH, mã voucher có giá trị sử dụng đến ngày 31-10-2022","","Bạn có mã freeship là GROUP8MOBILE, vui lòng sử dụng trong vòng 5 ngày", "", ""};
    String[] date = {"09:54 12-10-2022", "06:50 15-10-2022","09:04 18-10-2022","16:35 22-10-2022"
    ,"09:00 23-10-2022","16:35 24-10-2022","08:20 25-10-2022","16:47 26-10-2022"};
    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.notify_img_voucher,
            R.drawable.mono1,R.drawable.notify_img_voucher, R.drawable.mono1, R.drawable.mono1  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);


        listNotification = (ListView) findViewById(R.id.idListView);

        for (int i=0;i<7;i++){
            notifiesArray.add(new Notify(i, CheckProduct[i],title[i], status[i], product[i],date[i],content[i], image[i]));
        }
        CustomNotifyListViewAdapter myAdapter=new CustomNotifyListViewAdapter(this,R.layout.custom_notify_listview,notifiesArray);
        listNotification.setAdapter(myAdapter);

    }
}