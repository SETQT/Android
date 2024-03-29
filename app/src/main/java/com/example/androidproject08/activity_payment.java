package com.example.androidproject08;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.androidproject08.Services.FCMSend;
import com.example.androidproject08.models.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class activity_payment extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    // khai báo biến UI
    ListView listOrder;
    View ic_back_payment;
    TextView name_user_payment, phone_user_payment, address_payment, cost_payment, value_total_cost_product_payment, value_cost_tranfer_payment, value_total_voucher_discount_payment, value_total_money_payment;
    Button btn_order_payment;
    Spinner spinner_payment_methods;
    RelativeLayout rectangle_voucher;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");
    CollectionReference productsRef = db.collection("products");
    CollectionReference cartsRef = db.collection("carts");
    CollectionReference ordersRef = db.collection("orders");
    CollectionReference vouchersRef = db.collection("vouchers");
    CollectionReference usedVouchersRef = db.collection("usedVouchers");
    CollectionReference notifyRef = db.collection("notifications");
    CollectionReference adminRef = db.collection("admin");

    // sqlite
    SQLiteDatabase sqlite;

    // biến xử lý
    String username, preActivity, paymentMethod;
    String[] type_payment_methods = {"Tiền mặt", "Momo", "Ngân hàng"};
    ArrayList<Myorder> ListOrderArray = new ArrayList<>();
    Voucher usedVoucher;
    Integer finalTotalMoney;
    User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        spinner_payment_methods = (Spinner) findViewById(R.id.spiner_payment_methods);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_spiner_payment_methods, type_payment_methods);
        spinner_payment_methods.setAdapter(adapter);
        spinner_payment_methods.setOnItemSelectedListener(this);
        rectangle_voucher = (RelativeLayout) findViewById(R.id.rectangle_voucher);
        rectangle_voucher.setOnClickListener(this);
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

        if (intent.hasExtra("voucher")) {
            usedVoucher = (Voucher) intent.getExtras().getSerializable("voucher");
        }

        if (preActivity != null) {
            finalTotalMoney = 0;

            for (int i = 0; i < ListOrderArray.size(); i++) {
                finalTotalMoney += ListOrderArray.get(i).getTotal();
            }
            value_total_cost_product_payment.setText("đ" + finalTotalMoney.toString());
            value_cost_tranfer_payment.setText("đ" + 30000);

            if (usedVoucher != null) {
                value_total_voucher_discount_payment.setText("- đ" + usedVoucher.getMoneyDeals().toString());
                finalTotalMoney -= usedVoucher.getMoneyDeals();
            } else {
                value_total_voucher_discount_payment.setText("- đ" + 0);
            }

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
                                    user.setUserId(document.getId());
                                    curUser = user;

                                    if (user.getFullname() == null || user.getPhone() == null || user.getAddress() == null) {
                                        new AlertDialog.Builder(activity_payment.this)
                                                .setMessage("Bạn chưa điền đầy đủ thông tin giao hàng. Vui lòng nhấn Có để qua profile để cập nhật thông tin?")
                                                .setCancelable(false)
                                                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent moveActivity = new Intent(getApplicationContext(), activity_record.class);
                                                        startActivity(moveActivity);
                                                    }
                                                })
                                                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
                                                        startActivity(moveActivity);
                                                    }
                                                })
                                                .show();
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
            Order newOrder;

            if (usedVoucher != null) {
                newOrder = new Order(username, ListOrderArray, 30000, usedVoucher.getId(), 1, paymentMethod, new Date(), finalTotalMoney);

                // tăng lượng voucher đã dùng lên 1 đơn vị
                db.runTransaction(new Transaction.Function<Void>() {
                            @Override
                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                DocumentReference updatedVoucher = vouchersRef.document(usedVoucher.getIdDoc());
                                DocumentSnapshot snapshotVoucher = transaction.get(updatedVoucher);

                                // cập nhật voucher lên 1 đơn vị
                                if (snapshotVoucher.getDouble("amount") - snapshotVoucher.getDouble("amoutOfUsed") >= 1) {
                                    double newAmountOfUsed = snapshotVoucher.getDouble("amoutOfUsed") + 1;
                                    transaction.update(updatedVoucher, "amoutOfUsed", newAmountOfUsed);

                                    UsedVoucher newUsedVoucher = new UsedVoucher(username, usedVoucher.getIdDoc());
                                    usedVouchersRef.add(newUsedVoucher);
                                }

                                // tăng số lượng đã bán ra cho sản phẩm
                                for (int i = 0; i < ListOrderArray.size(); i++) {
                                    DocumentReference updatedProduct = productsRef.document(ListOrderArray.get(i).getId());
                                    DocumentSnapshot snapshotProduct = transaction.get(updatedProduct);

                                    if (snapshotProduct.getDouble("amount") - snapshotProduct.getDouble("amountOfSold") >= ListOrderArray.get(i).getCount()) {
                                        double newAmountOfSold = snapshotProduct.getDouble("amountOfSold") + ListOrderArray.get(i).getCount();
                                        transaction.update(updatedProduct, "amountOfSold", newAmountOfSold);
                                    }
                                }

                                // Success
                                return null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            } else {
                newOrder = new Order(username, ListOrderArray, 30000, "", 1, paymentMethod, new Date(), finalTotalMoney);

                // tăng lượng voucher đã dùng lên 1 đơn vị
                db.runTransaction(new Transaction.Function<Void>() {
                            @Override
                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                // tăng số lượng đã bán ra cho sản phẩm
                                for (int i = 0; i < ListOrderArray.size(); i++) {
                                    DocumentReference updatedProduct = productsRef.document(ListOrderArray.get(i).getId());
                                    DocumentSnapshot snapshotProduct = transaction.get(updatedProduct);

                                    if (snapshotProduct.getDouble("amount") - snapshotProduct.getDouble("amountOfSold") >= ListOrderArray.get(i).getCount()) {
                                        double newAmountOfSold = snapshotProduct.getDouble("amountOfSold") + ListOrderArray.get(i).getCount();
                                        transaction.update(updatedProduct, "amountOfSold", newAmountOfSold);
                                    }
                                }

                                // Success
                                return null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }


            if (ListOrderArray.get(0).getIdCart().equals("")) {
                // lưu đơn hàng lên database
                ordersRef.add(newOrder);
            } else {
                for (int i = 0; i < ListOrderArray.size(); i++) {
                    // xóa mặt hàng khỏi giỏ hàng
                    cartsRef.document(ListOrderArray.get(i).getIdCart()).delete();
                }

                // giảm số lượng trong giỏ hàng cho user
                Map<String, Integer> userCart = new HashMap<>();
                userCart.put("amount", curUser.getCart().get("amount") - ListOrderArray.size());
                usersRef.document(curUser.getUserId()).update("cart", userCart);

                // lưu đơn hàng lên database
                ordersRef.add(newOrder);
            }

            String title = "Thông báo đặt hàng!";
            SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            String content = "Khách hàng " + username + " vừa đặt đơn hàng gồm " + ListOrderArray.size() + " sản phẩm từ shop";
            // thông báo đến cho người dùng
            Notification newNotification = new Notification("https://firebasestorage.googleapis.com/v0/b/androidgroup8.appspot.com/o/logo%2FGroup%2010.png?alt=media&token=bc59d0df-9e04-4c66-a95d-78fbd0eef751", title, content, "admin", "order");
            notifyRef.add(newNotification);

            adminRef.whereEqualTo("name", "G8Shop")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Admin curAdmin = document.toObject(Admin.class);
                                    FCMSend.pushNotifcication(activity_payment.this, curAdmin.getFcmToken(), title, content, "USER_ORDER");

                                    Toast.makeText(getApplicationContext(), "Đặt hàng thành công! Chúng tôi sẽ gọi hoặc nhắn tin xác nhận đơn hàng với bạn!", Toast.LENGTH_LONG).show();

                                    // chuyển về activity dashboard
                                    Intent moveActivity = new Intent(getApplicationContext(), activity_myorder.class);
                                    startActivity(moveActivity);
                                }
                            }
                        }
                    });
        }

        if (view.getId() == rectangle_voucher.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_select_voucher.class);
            moveActivity.putExtra("products", ListOrderArray);
            moveActivity.putExtra("voucher", usedVoucher);
            startActivity(moveActivity);
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

    public static String RandomCode() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int len = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}
