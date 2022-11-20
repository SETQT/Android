package com.example.androidproject08;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class dashboard_fragment_first_asynctask extends AsyncTask<Void, Category, Category> {
    // kết nối firestore
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference categoriesRef = db.collection("categories");

    // biến xử lý
    ArrayList<ListViewOptionDashboard> list = new ArrayList<>();
    ArrayList<String> listNameCategory = new ArrayList<>();
    Activity curContext;
    RecyclerView recyclerView;

    public dashboard_fragment_first_asynctask(Activity curContext, RecyclerView recyclerView, ArrayList<ListViewOptionDashboard> list) {
        this.curContext = curContext;
        this.list = list;
        this.recyclerView = recyclerView;
    }

    public ArrayList<String> getListNameCategory() {
        return listNameCategory;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Category doInBackground(Void... voids) {
        try {
            categoriesRef
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Category category = document.toObject(Category.class);
                                    publishProgress(category);
                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        } catch (Exception error) {
            Log.i("Exception error", "doInBackground: " + error.toString());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Category... categories) {
        //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
        super.onProgressUpdate(categories);

        try {

        } catch (Exception error) {
            Log.e("TAG", "onProgressUpdate: error handling", error);
            return;
        }
    }

    @Override
    protected void onPostExecute(Category category) {
    }
}
