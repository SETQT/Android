package com.example.androidproject08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class activity_payment extends Activity {

    ListView listOrder;
    ArrayList<Order> ListOrderArray =new ArrayList<Order>();

    String[] name = {"Áo khoác Mono cực chất lượng", "Áo Liver giúp ra hang đầu mùa giải", "Áo anh 7 dự bị", "Giày độn", "Áo khoác", "Mũ lưỡi trai"};
    String[] old_cost = {"đ500.000","đ400.000","đ300.000","đ700.000","đ100.000","đ200.000" };
    String[] new_cost = {"đ300.000","đ200.000","đ200.000","đ500.000","đ80.000","đ150.000" };
    String[] number = {"02","01","02","01","01","01" };
    String[] size = {"S","M","L","XL","XXL","XXXL" };
    String[] color = {"Đen","Đỏ","Tím","Vàng","Xanh","Hồng" };
    String[] charge_tranfer = {"đ30.000","đ30.000","đ30.000","đ30.000","đ30.000","đ30.000" };
    String[] cost_final = {"đ500.000","đ500.000","đ500.000","đ500.000","đ500.000","đ500.000" };




    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1,R.drawable.mono1,R.drawable.mono1  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        listOrder = (ListView) findViewById(R.id.listview_payment);

        for (int i=0;i<6;i++){
            ListOrderArray.add(new Order(i, name[i],old_cost[i], new_cost[i], number[i],size[i],color[i],charge_tranfer[i],cost_final[i],image[i]));
        }
        CustomMyListViewPaymentAdapter myAdapter = new CustomMyListViewPaymentAdapter(this,R.layout.custom_listview_payment, ListOrderArray);
        listOrder.setAdapter(myAdapter);

    }

}
