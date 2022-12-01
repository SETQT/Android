package com.example.androidproject08;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class dashboard_asynctask extends AsyncTask<Void, Product, Product> {

    // khai báo biến xử lý
    Activity curContext;
    String category;
    LayoutInflater inflter;
    ArrayList<Product> finalList = new ArrayList<Product>();

    // khai báo biến UI
    GridView dashboard_gridview;
    LinearLayout curFrag;

    // kết nối firestore
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference productsRef = db.collection("products");

    public dashboard_asynctask(Activity curContext, LinearLayout curFrag, String category) {
        this.curContext = curContext;
        this.category = category;
        this.inflter = (LayoutInflater.from(curContext));
        this.curFrag = curFrag;
        this.dashboard_gridview = (GridView) curFrag.findViewById(R.id.dashboard_gridview);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Product doInBackground(Void... params) {
        try {
            if (category.equals("Tất cả")) {
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
                                            publishProgress(product);
                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                } catch (Exception error) {
                    throw error;
                }

            } else {
                try {
                    productsRef.whereEqualTo("category", category)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        Boolean isPass = false;

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            isPass = true;

                                            Product product = document.toObject(Product.class);
                                            product.setIdDoc(document.getId().toString());
                                            publishProgress(product);
                                        }

                                        if(!isPass) {
                                            publishProgress();
                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                } catch (Exception error) {
                    throw error;
                }
            }
        } catch (Exception error) {
            throw error;
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
            CustomProductLabelAdapter customAdapter = new CustomProductLabelAdapter(curContext, R.layout.custom_product_gridview, finalList, "activity_dashboard");
            dashboard_gridview.setAdapter(customAdapter);
        } catch (Exception error) {
            Log.e("TAG", "onProgressUpdate: error handling", error);
            return;
        }
    }

    @Override
    protected void onPostExecute(Product product) {
        super.onPostExecute(product);
    }
}
