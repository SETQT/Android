package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class activity_view_product extends Activity implements View.OnClickListener {
    // biến UI
    View ic_back_view_product, icon_cart;
    RecyclerView recyclerView_color, recyclerView_size;
    RelativeLayout rectangle_add_to_card_view_product;

    // biến xử lý
    String previousActivity, idDoc;
    private ListTypeProductAdapter mAdapter_color, mAdapter_size;
    ArrayList<String> list = new ArrayList<>();
    String username = "";

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");
    CollectionReference cartsRef = db.collection("carts");

    // sqlite
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        rectangle_add_to_card_view_product = (RelativeLayout) findViewById(R.id.rectangle_add_to_card_view_product);
        rectangle_add_to_card_view_product.setOnClickListener(this);

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

        avp_asynctask avp_at = new avp_asynctask();
        avp_at.execute();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == ic_back_view_product.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
            startActivity(moveActivity);
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

            downloadFile(products[0].getImage());
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

    public void downloadFile(String id) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
//        StorageReference islandRef = storageRef.child("image/girl480x600.jpg");
        StorageReference islandRef = storageRef.child("image").child(id.toString());//+".jpg");

        try {
            File localFile = File.createTempFile("tempfile", ".jpg");

            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d("down", "success: ");

                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ImageView imgProduct = (ImageView) findViewById(R.id.mono_1);
                    imgProduct.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("down", "onFailure: ");
                }
            });

        } catch (IOException e) {

        }

    }
}