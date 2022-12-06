package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class activity_payment extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    // khai báo biến UI
    ListView listOrder;
    View ic_back_payment;
    TextView name_user_payment, phone_user_payment, address_payment, cost_payment, value_total_cost_product_payment, value_cost_tranfer_payment, value_total_voucher_discount_payment, value_total_money_payment;
    Button btn_order_payment;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");
    CollectionReference cartsRef = db.collection("carts");
    CollectionReference ordersRef = db.collection("orders");

    // sqlite
    SQLiteDatabase sqlite;

    // biến xử lý
    String username, preActivity, paymentMethod;
    Spinner spinner_payment_methods;
    String[] type_payment_methods = {"Tiền mặt", "Momo", "Ngân hàng"};
    ArrayList<Myorder> ListOrderArray = new ArrayList<>();

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
        btn_order_payment = (Button) findViewById(R.id.btn_order_payment);
        btn_order_payment.setOnClickListener(this);
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

        if (intent.hasExtra("products")) {
            ListOrderArray = (ArrayList<Myorder>) intent.getExtras().getSerializable("products");
        }

        if (preActivity != null) {
            Integer finalTotalMoney = 0;

            for (int i = 0; i < ListOrderArray.size(); i++) {
                finalTotalMoney += ListOrderArray.get(i).getTotal();
            }
            value_total_cost_product_payment.setText("đ" + finalTotalMoney.toString());
            value_cost_tranfer_payment.setText("đ" + 30000);
            value_total_voucher_discount_payment.setText("đ" + 0);

            finalTotalMoney += 30000;
            cost_payment.setText("đ" + finalTotalMoney.toString());
            value_total_money_payment.setText("đ" + finalTotalMoney.toString());
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
                                    if (name_user_payment == null || phone_user_payment == null || address_payment == null) {
                                        Toast.makeText(getApplicationContext(), "Bạn chưa điền thông tin vui lòng qua profile để thêm!", Toast.LENGTH_SHORT).show();
                                    }

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
        setListViewHeightBasedOnChildren(listOrder);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == ic_back_payment.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
            startActivity(moveActivity);
        }

        if (view.getId() == btn_order_payment.getId()) {
            // tính tổng tiền đơn hàng
            Integer finalTotalMoney = 0;
            for (int i = 0; i < ListOrderArray.size(); i++) {
                finalTotalMoney += ListOrderArray.get(i).getTotal();
            }
            finalTotalMoney += 30000;

            Order newOrder = new Order(username, ListOrderArray, 30000, "", 1, paymentMethod, new Date(), finalTotalMoney);

            if (preActivity.equals("activity_mycart")) {
                for (int i = 0; i < ListOrderArray.size(); i++) {
                    // xóa mặt hàng khỏi giỏ hàng
                    cartsRef.document(ListOrderArray.get(i).getId()).delete();
                }

                // giảm số lượng trong giỏ hàng cho user
                usersRef
                        .whereEqualTo("username", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        User user = document.toObject(User.class);
                                        Map<String, Integer> userCart = new HashMap<>();
                                        userCart.put("amount", user.getCart().get("amount") - ListOrderArray.size());
                                        usersRef.document(document.getId()).update("cart", userCart);

                                        // lưu đơn hàng lên database
                                        ordersRef.add(newOrder);

                                        Toast.makeText(getApplicationContext(), "Đặt hàng thành công! Chúng tôi sẽ gọi hoặc nhắn tin xác nhận đơn hàng với bạn!", Toast.LENGTH_LONG).show();

                                        // chuyển về activity dashboard
                                        Intent moveActivity = new Intent(getApplicationContext(), activity_myorder.class);
                                        startActivity(moveActivity);
                                    }
                                }
                            }
                        });
            } else {
                // lưu đơn hàng lên database
                ordersRef.add(newOrder);

                // chuyển về activity dashboard
                Intent moveActivity = new Intent(getApplicationContext(), activity_myorder.class);
                startActivity(moveActivity);
            }
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        paymentMethod = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Hàm set full listview trong scrollview
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
