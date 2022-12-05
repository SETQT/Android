package com.example.androidproject08;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class activity_search extends Activity implements View.OnClickListener {

    // khai báo biến UI
    View icon_back, icon_search;
    RelativeLayout icon_scan, icon_notify, icon_profile, icon_home;
    GridView search_gridview;
    EditText text_search;

    // biến xử lý

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productsRef = db.collection("products");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);
        icon_scan = (RelativeLayout) findViewById(R.id.icon_scan);
        icon_scan.setOnClickListener(this);
        icon_notify = (RelativeLayout) findViewById(R.id.icon_notify);
        icon_notify.setOnClickListener(this);
        icon_profile = (RelativeLayout) findViewById(R.id.icon_profile);
        icon_profile.setOnClickListener(this);
        icon_home = (RelativeLayout) findViewById(R.id.icon_home);
        icon_home.setOnClickListener(this);
        icon_search = (View) findViewById(R.id.icon_search);
        icon_search.setOnClickListener(this);

        text_search = (EditText) findViewById(R.id.text_search);
        search_gridview = (GridView) findViewById(R.id.search_gridview);

        Intent intent = getIntent();
        String dataSearch = "";
        dataSearch = intent.getStringExtra("data_search");
        text_search.setText(dataSearch);
        String slugNameProduct = "";

        // chuyển data search từ tiếng việt => thành không dấu ví dụ: Áo quần thành ao-quan
        Log.i("TAG", "onCreate: " + dataSearch);
        if(dataSearch != null) {
            String temp = Normalizer.normalize(dataSearch.toLowerCase(), Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            slugNameProduct = pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        }

        search_asynctask as_at = new search_asynctask(slugNameProduct);
        as_at.execute();
    }

    @Override
    public void onClick(View view) {
        // chuyển sang giao diện scan mã qr
        if (view.getId() == icon_scan.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
            startActivity(moveActivity);
        }

        // chuyển sang giao diện thông báo
        if (view.getId() == icon_notify.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_notify.class);
            startActivity(moveActivity);
        }

        // chuyển sang giao diện profile
        if (view.getId() == icon_profile.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
            startActivity(moveActivity);
        }

        // chuyển về activity dashboard
        if (view.getId() == icon_back.getId() || view.getId() == icon_home.getId()) {
            Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
            startActivity(moveActivity);
        }

        if(view.getId() == icon_search.getId()) {
            String dataSearch = "";
            dataSearch = text_search.getText().toString();

            String temp = Normalizer.normalize(dataSearch.toLowerCase(), Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String slugNameProduct = pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");

            search_asynctask as_at = new search_asynctask(slugNameProduct);
            as_at.execute();
        }
    }

    private class search_asynctask extends AsyncTask<Void, Product, Product> {
        String slugFromDataSearch;
        ArrayList<Product> finalList = new ArrayList<Product>();

        public search_asynctask(String slugFromDataSearch) {
            this.slugFromDataSearch = slugFromDataSearch;
        }

        protected Product doInBackground(Void... voids) {
            try {
                productsRef
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Product product = document.toObject(Product.class);
                                        product.setIdDoc(document.getId().toString());
                                        String temp = Normalizer.normalize(product.getName().toLowerCase(), Normalizer.Form.NFD);
                                        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                                        String slugNameProduct = pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");

                                        if(slugNameProduct.contains(slugFromDataSearch)) {
                                            publishProgress(product);
                                        }
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } catch (Exception error) {
                Log.e("ERROR", "activity_search: " + error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Product... products) {
            //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
            super.onProgressUpdate(products);

            if(products.length == 0) {
                finalList.clear();
            } else {
                finalList.add(products[0]);
            }

            try {
                CustomProductLabelAdapter customAdapter = new CustomProductLabelAdapter(getApplicationContext(), R.layout.custom_product_gridview, finalList, "activity_search");
                search_gridview.setAdapter(customAdapter);
            } catch (Exception error) {
                Log.e("ERROR", "Activity_search onProgressUpdate: ", error);
                return;
            }
        }
    }

}
