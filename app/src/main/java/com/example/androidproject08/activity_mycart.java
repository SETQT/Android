package com.example.androidproject08;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.androidproject08.activities.ChatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_mycart extends Activity implements View.OnClickListener {
    // khai báo biến UI
    View icon_back, icon_chat;
    ListView listMyCart;
    Button MyCart_bg_buy;
    TextView MyCart_total_cost;
    CheckBox mycart_checkbox_all;

    // sqlite
    SQLiteDatabase sqlite;

    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference cartsRef = db.collection("carts");
    CollectionReference usersRef = db.collection("users");

    // biến xử lý
    String previousActivity, idDoc;
    Integer totalCart = 0;
    Integer totalMoney = 0;
    ArrayList<Integer> listChecked = new ArrayList<>();
    ArrayList<MyCart> finalList = new ArrayList<MyCart>(); // danh sách chứa các sản phẩm
    String username;

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
        mycart_checkbox_all = findViewById(R.id.mycart_checkbox_all);

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
        username = c1.getString(0);

        if (username == null) {
            return;
        }

        // query dữ liệu cho qua listview
        mycart_asynctask mc_at = new mycart_asynctask();
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
            if (finalList.size() == 0) {
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
            } else {
                ArrayList<Myorder> finalMyOrders = new ArrayList<>();

                for (int i = 0; i < finalList.size(); i++) {
                    if (listChecked.get(i) == 1) {
                        Integer total = finalList.get(i).getPrice() * finalList.get(i).getAmount();

                        //Đặt hàng -> Chờ xác nhận -> Đánh giá = NULL
                        Myorder orderProduct = new Myorder(finalList.get(i).getIdDoc(), finalList.get(i).getImage(), finalList.get(i).getName(), finalList.get(i).getSize(), finalList.get(i).getColor(), finalList.get(i).getOldPrice(), finalList.get(i).getPrice(), finalList.get(i).getAmount(), total, finalList.get(i).getId());
                        finalMyOrders.add(orderProduct);
                    }
                }

                if(finalMyOrders.size() == 0) {
                    // ko có đơn hàng nào được check
                    new AlertDialog.Builder(activity_mycart.this)
                            .setMessage("Vui lòng tick vào sản phẩm cần mua trước khi mua hàng!")
                            .setCancelable(true)
                            .setPositiveButton("OKE", null)
                            .show();
                }else {
                    Intent moveActivity = new Intent(getApplicationContext(), activity_payment.class);
                    moveActivity.putExtra("products", finalMyOrders);
                    moveActivity.putExtra("name_activity", "activity_mycart");
                    startActivity(moveActivity);
                }
            }
        }

        // vào giao diện chat
        if (view.getId() == icon_chat.getId()) {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            startActivity(intent);
        }

    }

    private class mycart_asynctask extends AsyncTask<Void, MyCart, MyCart> {
        public mycart_asynctask() {

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

            MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
            MyCart_total_cost.setText("đ" + totalMoney.toString());

            // set check all mycart cho lần đầu
            mycart_checkbox_all.setChecked(true);
            listChecked.add(1);

            CustomMycartListViewAdapter myAdapter = new CustomMycartListViewAdapter(getApplicationContext(), R.layout.custom_notify_listview, finalList, listChecked);
            listMyCart.setAdapter(myAdapter);

            mycart_checkbox_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        for (int i = 0; i < listChecked.size(); i++) {
                            listChecked.set(i, 1);
                        }

                        totalCart = 0;
                        totalMoney = 0;

                        for (int i = 0; i < finalList.size(); i++) {
                            totalCart += finalList.get(i).getAmount();
                            totalMoney += finalList.get(i).getAmount() * finalList.get(i).getPrice();
                        }

                        MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
                        MyCart_total_cost.setText("đ" + totalMoney.toString());

                        myAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < listChecked.size(); i++) {
                            listChecked.set(i, 0);
                        }

                        totalCart = 0;
                        totalMoney = 0;

                        MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
                        MyCart_total_cost.setText("đ" + totalMoney.toString());

                        myAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        protected void onPostExecute(MyCart myCart) {
            super.onPostExecute(myCart);
        }
    }

    private class CustomMycartListViewAdapter extends ArrayAdapter<MyCart> {
        // biến xử lý
        ArrayList<MyCart> my_cart = new ArrayList<MyCart>();
        ArrayList<Integer> listChecked = new ArrayList<>();
        Context curContext;
        int resource;

        public CustomMycartListViewAdapter(Context context, int resource, ArrayList<MyCart> objects, ArrayList<Integer> listChecked) {
            super(context, resource, objects);
            this.my_cart = objects;
            this.curContext = context;
            this.resource = resource;
            this.listChecked = listChecked;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_mycart_listview, null);

            TextView name = (TextView) v.findViewById(R.id.custom_mycart_name_product);
            TextView old_cost = (TextView) v.findViewById(R.id.custom_mycart_old_cost_product);
            TextView new_cost = (TextView) v.findViewById(R.id.custom_mycart_new_cost_product);
            TextView number = (TextView) v.findViewById(R.id.custom_mycart_number_product);
            ImageView img = (ImageView) v.findViewById(R.id.custom_mycart_picture);
            TextView custom_mycart_size = (TextView) v.findViewById(R.id.custom_mycart_size);
            TextView custom_mycart_color = (TextView) v.findViewById(R.id.custom_mycart_color);
            View subButton = (View) v.findViewById((R.id.custom_mycart_icon_decrease));
            View addButton = (View) v.findViewById((R.id.custom_mycart_icon_increase));
            View garbage = (View) v.findViewById((R.id.custom_mycart_icon_garbage));
            View addTotal = (View) v.findViewById((R.id.custom_mycart_checkbox));

            CheckBox custom_mycart_checkbox = (CheckBox) v.findViewById(R.id.custom_mycart_checkbox);

            if (listChecked.get(position) == 0) {
                custom_mycart_checkbox.setChecked(false);
            } else {
                custom_mycart_checkbox.setChecked(true);
            }

            custom_mycart_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        listChecked.set(position, 1);

                        totalCart += my_cart.get(position).getAmount();
                        totalMoney += my_cart.get(position).getAmount() * my_cart.get(position).getPrice();

                        MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
                        MyCart_total_cost.setText("đ" + totalMoney.toString());

                        notifyDataSetChanged();
                    } else {
                        listChecked.set(position, 0);

                        totalCart -= my_cart.get(position).getAmount();
                        totalMoney -= my_cart.get(position).getAmount() * my_cart.get(position).getPrice();

                        MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
                        MyCart_total_cost.setText("đ" + totalMoney.toString());

                        mycart_checkbox_all.setChecked(false);
                        notifyDataSetChanged();
                    }
                }
            });

            Integer oldCost = (my_cart.get(position).getPrice() / (100 - my_cart.get(position).getSale())) * 100; // tính lại giá cũ
            Picasso.with(curContext).load(my_cart.get(position).getImage()).into(img);
            name.setText(my_cart.get(position).getName());
            old_cost.setText(oldCost.toString());
            new_cost.setText(my_cart.get(position).getPrice().toString());
            number.setText(my_cart.get(position).getAmount().toString());
            custom_mycart_size.setText("Kích thước: " + my_cart.get(position).getSize());
            custom_mycart_color.setText("Màu sắc: " + my_cart.get(position).getColor());

            // trừ số lượng san phẩm trong cart khi bấm vào button "-"
            subButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer newNumber = Integer.parseInt(number.getText().toString()) - 1;
                    if (newNumber >= 0) {
                        number.setText(newNumber.toString());

                        // cập nhật lên firestore
                        cartsRef.document(my_cart.get(position).getId())
                                .update("amount", newNumber);

                        totalCart -= my_cart.get(position).getAmount();
                        totalMoney -= my_cart.get(position).getAmount() * my_cart.get(position).getPrice();

                        my_cart.get(position).setAmount(newNumber);

                        totalCart += my_cart.get(position).getAmount();
                        totalMoney += my_cart.get(position).getAmount() * my_cart.get(position).getPrice();

                        MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
                        MyCart_total_cost.setText("đ" + totalMoney.toString());

                        notifyDataSetChanged();
                    }
                }
            });

            // thêm số lượng sản phẩm trong cart
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer newNumber = Integer.parseInt(number.getText().toString()) + 1;
                    if (newNumber != 10000) {
                        number.setText(newNumber.toString());

                        // cập nhật lên firestore
                        cartsRef.document(my_cart.get(position).getId())
                                .update("amount", newNumber);

                        totalCart -= my_cart.get(position).getAmount();
                        totalMoney -= my_cart.get(position).getAmount() * my_cart.get(position).getPrice();

                        my_cart.get(position).setAmount(newNumber);

                        totalCart += my_cart.get(position).getAmount();
                        totalMoney += my_cart.get(position).getAmount() * my_cart.get(position).getPrice();

                        MyCart_bg_buy.setText("Mua hàng (" + totalCart.toString() + ")");
                        MyCart_total_cost.setText("đ" + totalMoney.toString());

                        notifyDataSetChanged();
                    }
                }
            });

            // xóa mặt hàng khỏi giỏ hàng
            garbage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // cập nhật lên firestore
                    cartsRef.document(my_cart.get(position).getId()).delete();

                    // giảm số lượng trong giỏ hàng cho user
                    usersRef
                            .whereEqualTo("username", my_cart.get(position).getOwnCart())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            User user = document.toObject(User.class);
                                            Map<String, Integer> userCart = new HashMap<>();
                                            userCart.put("amount", user.getCart().get("amount") - 1);
                                            usersRef.document(document.getId()).update("cart", userCart);
                                        }

                                        my_cart.remove(position);
                                        listChecked.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }
                            });
                }
            });

            return v;
        }
    }
}