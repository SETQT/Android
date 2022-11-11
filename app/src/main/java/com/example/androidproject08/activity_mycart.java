package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class activity_mycart extends Activity {

    ListView listMyCart;
    ArrayList<MyCart> MyCartArray = new ArrayList<MyCart>();

    String[] name = {"Áo khoác Mono cực chất lượng", "Áo Liver giúp ra hang đầu mùa giải", "Áo anh 7 dự bị", "Giày độn", "Áo khoác", "Mũ lưỡi trai"};
    String[] old_cost = {"đ500.000", "đ400.000", "đ300.000", "đ700.000", "đ100.000", "đ200.000"};
    String[] new_cost = {"đ300.000", "đ200.000", "đ200.000", "đ500.000", "đ80.000", "đ150.000"};
    String[] number = {"02", "01", "02", "01", "01", "01"};
    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1};

    // khai báo biến UI
    View icon_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        listMyCart = (ListView) findViewById(R.id.MyCart_listview);
        icon_back = (View) findViewById(R.id.icon_back);

        Intent intent = getIntent();
        String previousActivity = intent.getStringExtra("name_activity");

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
                    default:
                        break;
                }

                startActivity(moveActivity);
            }
        });

        for (int i = 0; i < 6; i++) {
            MyCartArray.add(new MyCart(i, name[i], old_cost[i], new_cost[i], number[i], image[i]));
        }

        CustomMycartListViewAdapter myAdapter = new CustomMycartListViewAdapter(this, R.layout.custom_notify_listview, MyCartArray);
        listMyCart.setAdapter(myAdapter);
    }
}