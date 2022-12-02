package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Code Bảo 
/*
package com.example.doan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ms.square.android.expandabletextview.ExpandableTextView;


public class activity_view_product extends Activity implements AdapterView.OnItemSelectedListener {
    String[] listType_size = {"M", "N", "O", "P"};
    String[] listType_color = {"Xanh", "Đỏ", "Tím", "Vàng"};
    Spinner spiner_size_view_product, spiner_color_view_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        // getting reference of  ExpandableTextView
        ExpandableTextView expTv = (ExpandableTextView) findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);

// calling setText on the ExpandableTextView so that
// text content will be  displayed to the user
        expTv.setText(getString(R.string.expandable_text));

        spiner_size_view_product = (Spinner) findViewById(R.id.spiner_type_size_view_product);
        ArrayAdapter<String> adapter_size = new ArrayAdapter<String>(this, R.layout.custom_spiner_payment_methods, listType_size);
        spiner_size_view_product.setAdapter(adapter_size);
        spiner_size_view_product.setOnItemSelectedListener(this);

        spiner_color_view_product = (Spinner) findViewById(R.id.spiner_type_color_view_product);
        ArrayAdapter<String> adapter_color = new ArrayAdapter<String>(this, R.layout.custom_spiner_create_voucher, listType_color);
        spiner_color_view_product.setAdapter(adapter_color);
        spiner_color_view_product.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
*/

public class activity_view_product extends Activity implements View.OnClickListener {
    // biến UI
    View ic_back_view_product, icon_cart;
    RecyclerView recyclerView_color, recyclerView_size;
    RelativeLayout rectangle_add_to_card_view_product, rectangle_buy_now_view_product;
    TextView number_cart;
    ImageView imgProduct;

    // biến xử lý
    String previousActivity, idDoc;
    private ListTypeProductAdapter mAdapter_color, mAdapter_size;
    ArrayList<String> list = new ArrayList<>();
    String username = "";
    String linkImage = "";

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");
    CollectionReference cartsRef = db.collection("carts");
    CollectionReference usersRef = db.collection("users");

    // sqlite
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        rectangle_add_to_card_view_product = (RelativeLayout) findViewById(R.id.rectangle_add_to_card_view_product);
        rectangle_add_to_card_view_product.setOnClickListener(this);
        rectangle_buy_now_view_product = (RelativeLayout) findViewById(R.id.rectangle_buy_now_view_product);
        rectangle_buy_now_view_product.setOnClickListener(this);

        recyclerView_color = (RecyclerView) findViewById(R.id.recyclerView_color_view_product);
        LinearLayoutManager mLayoutManager_color = new LinearLayoutManager(getApplicationContext());
        mLayoutManager_color.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAdapter_color = new ListTypeProductAdapter(list);
        recyclerView_color.setLayoutManager(mLayoutManager_color);
        recyclerView_color.setItemAnimator(new DefaultItemAnimator());
        recyclerView_color.setAdapter(mAdapter_color);

        LinearLayoutManager mLayoutManager_size = new LinearLayoutManager(getApplicationContext());
        mLayoutManager_size.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_size = (RecyclerView) findViewById(R.id.recyclerView_size_view_product);
        mAdapter_size = new ListTypeProductAdapter(list);
        recyclerView_size.setLayoutManager(mLayoutManager_size);
        recyclerView_size.setItemAnimator(new DefaultItemAnimator());
        recyclerView_size.setAdapter(mAdapter_color);

        ic_back_view_product = (View) findViewById(R.id.ic_back_view_product);
        ic_back_view_product.setOnClickListener(this);
        icon_cart = (View) findViewById(R.id.icon_cart);
        icon_cart.setOnClickListener(this);

        number_cart = (TextView) findViewById(R.id.number_cart);

        // nhận dữ liệu từ các activity khác gửi tới
        Intent intent = getIntent();
        previousActivity = intent.getStringExtra("name_activity");
        idDoc = intent.getStringExtra("idDoc");

        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        username = c1.getString(0);

        // lấy số lượng sản phẩm
        usersRef.whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                number_cart.setText(user.getCart().get("amount").toString());
                                break;
                            }
                        }
                    }
                });

        avp_asynctask avp_at = new avp_asynctask();
        avp_at.execute();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == ic_back_view_product.getId()) {
            Intent moveActivity = new Intent();

            if (previousActivity == null) {
                moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                startActivity(moveActivity);
            } else {
                switch (previousActivity) {
                    case "activity_search":
                        moveActivity = new Intent(getApplicationContext(), activity_search.class);
                        startActivity(moveActivity);
                        break;
                    case "activity_dashboard":
                        moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
                        startActivity(moveActivity);
                        break;
                    default:
                        break;
                }
            }
        }

        if (view.getId() == icon_cart.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
            moveActivity.putExtra("idDoc", idDoc);
            moveActivity.putExtra("name_activity", "activity_view_product");
            startActivity(moveActivity);
        }

        if (view.getId() == rectangle_add_to_card_view_product.getId()) {
            try {
                productsRef.document(idDoc).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    Product product = document.toObject(Product.class);

                                    MyCart ToCart = new MyCart(1, "", product.getImage(), product.getName(), product.getPrice(), product.getSale(), username, document.getId().toString());

                                    cartsRef
                                            .whereEqualTo("idDoc", ToCart.getIdDoc())
                                            .whereEqualTo("ownCart", ToCart.getOwnCart())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        boolean isDuplicated = false;
                                                        Integer totalAmount = 0;

                                                        // kiểm tra thử sản phẩm đã có trong giỏ hàng trước đó chưa
                                                        // nếu có thì cộng thêm số lượng vào
                                                        for (QueryDocumentSnapshot document2 : task.getResult()) {
                                                            MyCart cart = document2.toObject(MyCart.class);
                                                            isDuplicated = true;
                                                            totalAmount = cart.getAmount() + 1;
                                                            cartsRef.document(document2.getId()).update("amount", totalAmount);
                                                        }

                                                        // nếu không thì tạo cart mới
                                                        if (!isDuplicated) {
                                                            cartsRef.add(ToCart);
                                                            // tăng số lượng trong giỏ hàng cho user
                                                            usersRef
                                                                    .whereEqualTo("username", ToCart.getOwnCart())
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                    User user = document.toObject(User.class);
                                                                                    Map<String, Integer> userCart = new HashMap<>();
                                                                                    userCart.put("amount", user.getCart().get("amount") + 1);
                                                                                    number_cart.setText(userCart.get("amount").toString());
                                                                                    usersRef.document(document.getId()).update("cart", userCart);
                                                                                }
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    } else {
                                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                                    }
                                                }
                                            });
                                    Toast.makeText(activity_view_product.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "activity_view_product: " + error);
            }
        }

        if (view.getId() == rectangle_buy_now_view_product.getId()) {
            // lấy những thông tin cần thiết cho đơn hàng
            String id = idDoc;

            String image = linkImage;

            TextView name_view_product = (TextView) findViewById(R.id.name_view_product);
            String name = name_view_product.getText().toString();

            String size = "M";
            String color = "Đen";

            TextView old_cost_view_product = (TextView) findViewById(R.id.old_cost_view_product);
            Integer oldCost = Integer.parseInt(old_cost_view_product.getText().toString().substring(1));

            TextView new_cost_view_product = (TextView) findViewById(R.id.new_cost_view_product);
            Integer newCost = Integer.parseInt(new_cost_view_product.getText().toString().substring(1));

            Integer count = 1;
            Integer total = newCost * count;

            Myorder orderProduct = new Myorder(id, linkImage, name, size, color, oldCost, newCost, count, total, 30000);

            Intent moveActivity = new Intent(getApplicationContext(), activity_payment.class);
            moveActivity.putExtra("name_activity", "activity_view_product");
            moveActivity.putExtra("product", orderProduct);
            startActivity(moveActivity);
        }
    }

    private class avp_asynctask extends AsyncTask<Void, Product, Product> {
        avp_asynctask() {

        }

        @Override
        protected Product doInBackground(Void... voids) {
            try {
                productsRef.document(idDoc).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    Product product = document.toObject(Product.class);
                                    publishProgress(product);
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "activity_view_product: " + error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Product... products) {
            super.onProgressUpdate(products);

            // thiết lập các thuộc tính cơ bản của product
            ImageView imgProduct = (ImageView) findViewById(R.id.mono_1);
            Picasso.with(getApplicationContext()).load(products[0].getImage()).into(imgProduct);
            linkImage = products[0].getImage();

            TextView name_view_product = (TextView) findViewById(R.id.name_view_product);
            name_view_product.setText(products[0].getName());

            TextView new_cost_view_product = (TextView) findViewById(R.id.new_cost_view_product);
            new_cost_view_product.setText("đ" + products[0].getPrice().toString());

            Integer oldPrice = (products[0].getPrice() / (100 - products[0].getSale())) * 100;

            TextView old_cost_view_product = (TextView) findViewById(R.id.old_cost_view_product);
            old_cost_view_product.setText("đ" + oldPrice.toString());

            TextView rate_reduce = (TextView) findViewById(R.id.rate_reduce);
            rate_reduce.setText("-" + products[0].getSale().toString() + "%");

            TextView sales_view_product = (TextView) findViewById(R.id.sales_view_product);
            sales_view_product.setText("Đã bán: " + products[0].getAmountOfSold().toString());

            ExpandableTextView expTv = (ExpandableTextView) findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);
            expTv.setText(products[0].getDescription());

            // thiết lập color
            mAdapter_color = new ListTypeProductAdapter(products[0].getTypeColor());
            recyclerView_color.setAdapter(mAdapter_color);

            // thiết lập size
            mAdapter_size = new ListTypeProductAdapter(products[0].getTypeSize());
            recyclerView_size.setAdapter(mAdapter_size);
        }
    }
}