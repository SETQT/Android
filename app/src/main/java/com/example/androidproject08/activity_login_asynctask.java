package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class activity_login_asynctask extends AsyncTask<Void, User, User> {
    Activity contextParent;
    String tk;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference usersRef = db.collection("users");

    public activity_login_asynctask(Activity contextParent, String tk) {
        this.contextParent = contextParent;
        this.tk = tk;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Hàm này sẽ chạy đầu tiên khi AsyncTask này được gọi
        //Ở đây mình sẽ thông báo quá trình load bắt đâu "Start"
        Toast.makeText(contextParent, "Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected User doInBackground(Void... params) {
        usersRef
                .whereEqualTo("username", tk)
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
        // chuyển sang giao diện chính
        contextParent.startActivity(new Intent(contextParent, activity_dashboard.class));
    }
//
//    @Override
//    protected void onPostExecute(Void aVoid) {
////        super.onPostExecute(aVoid);
//        //Hàm này được thực hiện khi tiến trình kết thúc
//        //Ở đây mình thông báo là đã "Finshed" để người dùng biết
//        Toast.makeText(contextParent, "Okie, Finished", Toast.LENGTH_SHORT).show();
//    }

}
