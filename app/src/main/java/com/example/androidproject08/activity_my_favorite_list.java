package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class activity_my_favorite_list extends Activity implements View.OnClickListener {
    // biến UI
    View icon_back;
    GridView listview_my_favorite;

    // firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference favoritesRef = db.collection("favorites");

    // Biến xử lý
    String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite_list);

        listview_my_favorite = (GridView) findViewById(R.id.listview_my_favorite);
        icon_back = (View) findViewById(R.id.icon_back);
        icon_back.setOnClickListener(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        favorite_asynctask f_at = new favorite_asynctask();
        f_at.execute();
    }

    @Override
    public void onClick(View view){
        if (view.getId() == icon_back.getId()){
            Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
            startActivity(moveActivity);
        }
    }

    private class favorite_asynctask extends AsyncTask<Void, FavoriteProduct, FavoriteProduct> {
        favorite_asynctask() {}

        @Override
        protected FavoriteProduct doInBackground(Void... voids) {
            try {
                favoritesRef.whereEqualTo("user", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    FavoriteProduct favoriteProduct = document.toObject(FavoriteProduct.class);
                                    Log.i("TAG", "onComplete: "+favoriteProduct);
                                    publishProgress(favoriteProduct);
                                }
                            }
                        });
            }catch (Exception error) {
                Log.e("TAG", "Activity_my_favorite_list doInBackground: ", error);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(FavoriteProduct... favoriteProducts) {
            super.onProgressUpdate(favoriteProducts);

            ArrayList<Product> products = favoriteProducts[0].getProducts();

            CustomMyFavoriteListViewAdapter customAdapter = new CustomMyFavoriteListViewAdapter(getApplicationContext(), R.layout.custom_my_favorite_list_gridview, products);
            listview_my_favorite.setAdapter(customAdapter);
        }
    }
}
