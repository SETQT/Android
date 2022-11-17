package com.example.androidproject08;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class record_asynctask extends AsyncTask<Void, User, User> {
    Activity curContext;
    String username;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference usersRef = db.collection("users");
    ProgressDialog pd;
    TextView record_id_profile_ten, record_id_profile_bio, record_id_profile_sex, record_id_profile_dob, record_id_profile_phone, record_id_profile_email;

    public record_asynctask(Activity curContext, String username) {
        this.curContext = curContext;
        this.username = username;
        this.record_id_profile_ten = curContext.findViewById(R.id.record_id_profile_ten);
        this.record_id_profile_bio = curContext.findViewById(R.id.record_id_profile_bio);
        this.record_id_profile_sex = curContext.findViewById(R.id.record_id_profile_sex);
        this.record_id_profile_dob = curContext.findViewById(R.id.record_id_profile_dob);
        this.record_id_profile_phone = curContext.findViewById(R.id.record_id_profile_phone);
        this.record_id_profile_email = curContext.findViewById(R.id.record_id_profile_email);
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
        record_id_profile_ten.setText(user[0].getFullname());
        record_id_profile_bio.setText(user[0].getBio());
        record_id_profile_sex.setText(user[0].getGender());
//        record_id_profile_dob.setText(user[0].getBirthdate().toString());
        record_id_profile_phone.setText(user[0].getPhone());
        record_id_profile_email.setText(user[0].getEmail());
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
    }
}
