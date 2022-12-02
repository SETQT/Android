package com.example.androidproject08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;

public class activity_payment extends Activity implements View.OnClickListener {
    // khai báo biến UI
    ListView listOrder;
    View ic_back_payment;
    TextView name_user_payment, phone_user_payment, address_payment;

    ArrayList<Order> ListOrderArray =new ArrayList<Order>();

    String[] name = {"Áo khoác Mono cực chất lượng", "Áo Liver giúp ra hang đầu mùa giải", "Áo anh 7 dự bị", "Giày độn", "Áo khoác", "Mũ lưỡi trai"};
    String[] old_cost = {"đ500.000","đ400.000","đ300.000","đ700.000","đ100.000","đ200.000" };
    String[] new_cost = {"đ300.000","đ200.000","đ200.000","đ500.000","đ80.000","đ150.000" };
    String[] number = {"02","01","02","01","01","01" };
    String[] size = {"S","M","L","XL","XXL","XXXL" };
    String[] color = {"Đen","Đỏ","Tím","Vàng","Xanh","Hồng" };
    String[] charge_tranfer = {"đ30.000","đ30.000","đ30.000","đ30.000","đ30.000","đ30.000" };
    String[] cost_final = {"đ500.000","đ500.000","đ500.000","đ500.000","đ500.000","đ500.000" };

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");
    CollectionReference usersRef = db.collection("users");

    // sqlite
    SQLiteDatabase sqlite;

    // biến xử lý
    String username;

    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1,R.drawable.mono1,R.drawable.mono1  };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ic_back_payment = (View) findViewById(R.id.ic_back_payment);
        ic_back_payment.setOnClickListener(this);
        listOrder = (ListView) findViewById(R.id.listview_payment);
        name_user_payment = (TextView) findViewById(R.id.name_user_payment);
        phone_user_payment = (TextView) findViewById(R.id.phone_user_payment);
        address_payment = (TextView) findViewById(R.id.address_payment);

        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        username = c1.getString(0);

        // lấy thông tin người dùng
        try {
            usersRef.whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    name_user_payment.setText(user.getFullname());
                                    phone_user_payment.setText(user.getPhone());
                                    address_payment.setText(user.getAddress());
                                }
                            }
                        }
                    });
        }catch (Exception error) {
            Log.e("ERROR", "activity_payment onCreate: ", error);
        }

        for (int i=0;i<6;i++){
            ListOrderArray.add(new Order(i, name[i],old_cost[i], new_cost[i], number[i],size[i],color[i],charge_tranfer[i],cost_final[i],image[i]));
        }

        CustomMyListViewPaymentAdapter myAdapter = new CustomMyListViewPaymentAdapter(this,R.layout.custom_listview_payment, ListOrderArray);
        listOrder.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == ic_back_payment.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
            startActivity(moveActivity);
        }
    }
}
