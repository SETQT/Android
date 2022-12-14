package com.example.androidproject08;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.example.androidproject08.activities.ChatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class activity_mycart extends Activity implements View.OnClickListener {
    // khai báo biến UI
    View icon_back, icon_chat;
    ListView listMyCart;
    Button MyCart_bg_buy;
    TextView MyCart_total_cost;

    // sqlite
    SQLiteDatabase sqlite;

    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference cartsRef = db.collection("carts");

    // biến xử lý
    String previousActivity, idDoc;
    ObservableArrayList listChecked = new ObservableArrayList();
    ArrayList<MyCart> finalList = new ArrayList<MyCart>(); // danh sách chứa các sản phẩm

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        listMyCart = (ListView) findViewById(R.id.MyCart_listview);
        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);
        icon_chat = (View) findViewById(R.id.icon_chat);
        icon_chat.setOnClickListener(this);

        MyCart_bg_buy = (Button) findViewById(R.id.MyCart_bg_buy);
        MyCart_bg_buy.setOnClickListener(this);
        MyCart_total_cost = (TextView) findViewById(R.id.MyCart_total_cost);

        // get tên của activity trước đỏ để back khi nhấn button back lại đúng vị trí
        Intent intent = getIntent();
        previousActivity = intent.getStringExtra("name_activity");
        idDoc = intent.getStringExtra("idDoc");

        // get username từ sqlite
        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        String username = c1.getString(0);

        if (username == null) {
            return;
        }

        // query dữ liệu cho qua listview
        mycart_asynctask mc_at = new mycart_asynctask(username);
        mc_at.execute();
    }

    @Override
    public void onClick(View view) {
        // trở về activity trước đó
        if (view.getId() == icon_back.getId()) {
            Intent moveActivity = new Intent();

            if (previousActivity != null) {
                switch (previousActivity) {
                    case "activity_dashboard":
                        moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                        startActivity(moveActivity);
                        break;
                    case "activity_notify":
                        moveActivity = new Intent(getApplicationContext(), activity_notify.class);
                        startActivity(moveActivity);
                        break;
                    case "activity_profile":
                        moveActivity = new Intent(getApplicationContext(), activity_profile.class);
                        startActivity(moveActivity);
                        break;
                    case "activity_voucher":
                        moveActivity = new Intent(getApplicationContext(), activity_voucher.class);
                        startActivity(moveActivity);
                        break;
                    case "activity_myorder":
                        moveActivity = new Intent(getApplicationContext(), activity_myorder.class);
                        startActivity(moveActivity);
                        break;
                    case "activity_view_product":
                        moveActivity = new Intent(getApplicationContext(), activity_view_product.class);
                        moveActivity.putExtra("idDoc", idDoc);
                        startActivity(moveActivity);
                        break;
                    default:
                        break;
                }
            } else {
                moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                startActivity(moveActivity);
            }
        }

        // tiến hành đặt hàng
        if (view.getId() == MyCart_bg_buy.getId()) {
            if(finalList.size() == 0) {
                // không có đơn hàng trong giỏ hàng để đặt
                new AlertDialog.Builder(activity_mycart.this)
                        .setMessage("Bạn hiện không có sản phẩm nào trong giỏ. Nhấn có để quay trở lại mua sắm nào!")
                        .setCancelable(false)
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                                startActivity(moveActivity);
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }else {
                ArrayList<Myorder> finalMyOrders = new ArrayList<>();

                for (int i = 0; i < finalList.size(); i++) {
                    Integer total = finalList.get(i).getPrice() * finalList.get(i).getAmount();

                    //Đặt hàng -> Chờ xác nhận -> Đánh giá = NULL
                    Myorder orderProduct = new Myorder(finalList.get(i).getIdDoc(), finalList.get(i).getImage(), finalList.get(i).getName(), finalList.get(i).getSize(), finalList.get(i).getColor(), finalList.get(i).getOldPrice(), finalList.get(i).getPrice(), finalList.get(i).getAmount(), total, finalList.get(i).getId());
                    finalMyOrders.add(orderProduct);
                }

                Intent moveActivity = new Intent(getApplicationContext(), activity_payment.class);
                moveActivity.putExtra("products", finalMyOrders);
                moveActivity.putExtra("name_activity", "activity_mycart");
                startActivity(moveActivity);
            }
        }

        // vào giao diện chat
        if(view.getId() == icon_chat.getId()){
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            startActivity(intent);
        }

    }

    private class mycart_asynctask extends AsyncTask<Void, MyCart, MyCart> {
        String username;
        Integer totalCart;
        Integer totalMoney;

        // biến UI
        Button MyCart_bg_buy;
        TextView MyCart_total_cost;
        CheckBox mycart_checkbox_all;

        public mycart_asynctask(String username) {
            this.username = username;

            this.MyCart_bg_buy = findViewById(R.id.MyCart_bg_buy);
            this.MyCart_total_cost = findViewById(R.id.MyCart_total_cost);
            this.mycart_checkbox_all = findViewById(R.id.mycart_checkbox_all);

            this.totalCart = 0;
            this.totalMoney = 0;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MyCart doInBackground(Void... params) {
            cartsRef
                    .whereEqualTo("ownCart", username)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    MyCart cart = document.toObject(MyCart.class);
                                    cart.setId(document.getId());
                                    publishProgress(cart);
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });
            return null;
        }

        @Override
        protected void onProgressUpdate(MyCart... carts) {
            //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
            super.onProgressUpdate(carts);
            finalList.add(carts[0]);

            totalCart += carts[0].getAmount();
            totalMoney += carts[0].getAmount() * carts[0].getPrice();

            // set check all mycart cho lần đầu
            mycart_checkbox_all.setChecked(true);
            listChecked.add(1);

            // hiển thị tổng sản phẩm và tổng tiền tất cả các mặt hàng có trong giỏ hàng
            MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
            MyCart_total_cost.setText("đ" + totalMoney.toString());

            CustomMycartListViewAdapter myAdapter = new CustomMycartListViewAdapter(getApplicationContext(), R.layout.custom_notify_listview, finalList, listChecked);
            listMyCart.setAdapter(myAdapter);

            mycart_checkbox_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        for (int i = 0; i < listChecked.size(); i++) {
                            listChecked.set(i, 1);
                        }

                        myAdapter.notifyDataSetChanged();

                        // hiển thị tổng sản phẩm và tổng tiền tất cả các mặt hàng có trong giỏ hàng
                        MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
                        MyCart_total_cost.setText("đ" + totalMoney.toString());
                    } else {
                        for (int i = 0; i < listChecked.size(); i++) {
                            listChecked.set(i, 0);
                        }

                        myAdapter.notifyDataSetChanged();

                        // hiển thị tổng sản phẩm và tổng tiền tất cả các mặt hàng có trong giỏ hàng
                        MyCart_bg_buy.setText("Mua hàng (" + 0 + ")");
                        MyCart_total_cost.setText("đ" + 0);
                    }
                }
            });
        }

        @Override
        protected void onPostExecute(MyCart myCart) {
            super.onPostExecute(myCart);
        }
    }
}