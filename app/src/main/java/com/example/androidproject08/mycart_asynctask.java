package com.example.androidproject08;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class mycart_asynctask extends AsyncTask<Void, MyCart, MyCart> {
    Activity curContext;
    String username;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference cartsRef = db.collection("carts");
    ProgressDialog pd;
    ArrayList<MyCart> finalList = new ArrayList<MyCart>(); // danh sách chứa các sản phẩm
    ListView listMyCart;
    Integer totalCart;
    Integer totalMoney;

    // biến UI
    Button MyCart_bg_buy;
    TextView MyCart_total_cost;

    public mycart_asynctask(Activity curContext, String username) {
        this.curContext = curContext;
        this.username = username;

        this.listMyCart = curContext.findViewById(R.id.MyCart_listview);
        this.MyCart_bg_buy = curContext.findViewById(R.id.MyCart_bg_buy);
        this.MyCart_total_cost = curContext.findViewById(R.id.MyCart_total_cost);

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
                                cart.setIdDoc(document.getId().toString());
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

        // hiển thị tổng sản phẩm và tổng tiền tất cả các mặt hàng có trong giỏ hàng
        MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
        MyCart_total_cost.setText("đ" + totalMoney.toString());

        CustomMycartListViewAdapter myAdapter = new CustomMycartListViewAdapter(curContext,R.layout.custom_notify_listview, finalList);
        listMyCart.setAdapter(myAdapter);
    }

    @Override
    protected void onPostExecute(MyCart myCart) {
        super.onPostExecute(myCart);
    }
}
