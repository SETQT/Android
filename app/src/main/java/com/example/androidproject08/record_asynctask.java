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

public class record_asynctask extends AsyncTask<Void, User, User> {
    Activity curContext;
    String username;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference usersRef = db.collection("users");
    ProgressDialog pd;

    public record_asynctask(Activity curContext, String username) {
        this.curContext = curContext;
        this.username = username;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected User doInBackground(Void... params) {
        usersRef
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                publishProgress(user);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return null;
    }

    @Override
    protected void onProgressUpdate(User... user) {
        //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
        super.onProgressUpdate(user);
        Log.i("TAG", "onProgressUpdate: " + user[0].getUsername());
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
    }
}
