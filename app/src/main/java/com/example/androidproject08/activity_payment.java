package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

public class activity_payment extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    // khai báo biến UI
    ListView listOrder;
    View ic_back_payment;
    TextView name_user_payment, phone_user_payment, address_payment, cost_payment, value_total_cost_product_payment, value_cost_tranfer_payment, value_total_voucher_discount_payment, value_total_money_payment;

    ArrayList<Myorder> ListOrderArray = new ArrayList<>();

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");
    CollectionReference usersRef = db.collection("users");

    // sqlite
    SQLiteDatabase sqlite;

    // biến xử lý
    String username, preActivity;

    Integer[] image = {R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1, R.drawable.mono1};
    Spinner spinner_payment_methods;
    String[] type_payment_methods = {"Tiền mặt", "Momo", "Ngân hàng"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        spinner_payment_methods = (Spinner) findViewById(R.id.spiner_payment_methods);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_payment_methods, type_payment_methods);
        spinner_payment_methods.setAdapter(adapter);
        spinner_payment_methods.setOnItemSelectedListener(this);

        ic_back_payment = (View) findViewById(R.id.ic_back_payment);
        ic_back_payment.setOnClickListener(this);
        listOrder = (ListView) findViewById(R.id.listview_payment);
        name_user_payment = (TextView) findViewById(R.id.name_user_payment);
        phone_user_payment = (TextView) findViewById(R.id.phone_user_payment);
        address_payment = (TextView) findViewById(R.id.address_payment);
        cost_payment = (TextView) findViewById(R.id.cost_payment);
        value_total_cost_product_payment = (TextView) findViewById(R.id.value_total_cost_product_payment);
        value_cost_tranfer_payment = (TextView) findViewById(R.id.value_cost_tranfer_payment);
        value_total_voucher_discount_payment = (TextView) findViewById(R.id.value_total_voucher_discount_payment);
        value_total_money_payment = (TextView) findViewById(R.id.value_total_money_payment);

        // lấy dữ liệu
        Intent intent = getIntent();
        preActivity = intent.getStringExtra("name_activity");

        Myorder orderProduct = new Myorder();
        if (intent.hasExtra("product")) {
            orderProduct = (Myorder) intent.getExtras().getSerializable("product");
            ListOrderArray.add(orderProduct);
        }

        if(preActivity != null) {
            switch (preActivity) {
                // xử lý khi vào xem sản phẩm và nhấn mua hàng ngay
                case "activity_view_product":
                    value_total_cost_product_payment.setText("đ" + orderProduct.getTotal().toString());
                    value_cost_tranfer_payment.setText("đ" + orderProduct.getTransportFee().toString());
                    value_total_voucher_discount_payment.setText("đ" + 0);

                    Integer finalTotalMoney = orderProduct.getTotal() + orderProduct.getTransportFee();
                    cost_payment.setText("đ" + finalTotalMoney.toString());
                    value_total_money_payment.setText("đ" + finalTotalMoney.toString());
                    break;
                default:
                    break;
            }
        }

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
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    name_user_payment.setText(user.getFullname());
                                    phone_user_payment.setText(user.getPhone());
                                    address_payment.setText(user.getAddress());
                                }
                            }
                        }
                    });
        } catch (Exception error) {
            Log.e("ERROR", "activity_payment onCreate: ", error);
        }

        CustomMyListViewPaymentAdapter myAdapter = new CustomMyListViewPaymentAdapter(this, R.layout.custom_listview_payment, ListOrderArray);
        listOrder.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == ic_back_payment.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
            startActivity(moveActivity);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
