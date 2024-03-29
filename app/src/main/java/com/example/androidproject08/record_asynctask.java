package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class record_asynctask extends AsyncTask<Void, User, User> implements View.OnClickListener {
    Activity curContext;
    String username;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference usersRef = db.collection("users");
    TextView record_id_profile_ten, record_id_profile_bio, record_id_profile_sex, record_id_profile_dob, record_id_profile_phone, record_id_profile_email, record_id_profile_address;
    View record_next_01, record_next_02, record_next_03, record_next_04, record_next_05, record_next_06, record_next_07, record_next_08;
    User curUser;
    Uri avatar;
    Uri background;

    public record_asynctask(Activity curContext, String username, Uri avatar, Uri background) {
        this.curContext = curContext;
        this.username = username;
        this.record_id_profile_ten = curContext.findViewById(R.id.record_id_profile_ten);
        this.record_id_profile_bio = curContext.findViewById(R.id.record_id_profile_bio);
        this.record_id_profile_sex = curContext.findViewById(R.id.record_id_profile_sex);
        this.record_id_profile_dob = curContext.findViewById(R.id.record_id_profile_dob);
        this.record_id_profile_phone = curContext.findViewById(R.id.record_id_profile_phone);
        this.record_id_profile_email = curContext.findViewById(R.id.record_id_profile_email);
        this.record_id_profile_address = curContext.findViewById(R.id.record_id_profile_address);
        this.curUser = new User();
        this.record_next_01 = curContext.findViewById(R.id.record_next_01);
        this.record_next_02 = curContext.findViewById(R.id.record_next_02);
        this.record_next_03 = curContext.findViewById(R.id.record_next_03);
        this.record_next_04 = curContext.findViewById(R.id.record_next_04);
        this.record_next_05 = curContext.findViewById(R.id.record_next_05);
        this.record_next_06 = curContext.findViewById(R.id.record_next_06);
        this.record_next_07 = curContext.findViewById(R.id.record_next_07);
        this.record_next_08 = curContext.findViewById(R.id.record_next_08);

        if (avatar != null) {
            this.avatar = avatar;
        }

        if (background != null) {
            this.background = background;
        }
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
                                user.setUserId(document.getId()); // lấy id document của user

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
        this.curUser = user[0];

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        record_id_profile_ten.setText(user[0].getFullname());
        record_id_profile_bio.setText(user[0].getBio());
        record_id_profile_sex.setText(user[0].getGender());

        if(user[0].getBirthdate() != null) {
            record_id_profile_dob.setText(simpleDateFormat.format(user[0].getBirthdate()));
        }

        record_id_profile_phone.setText(user[0].getPhone());
        record_id_profile_email.setText(user[0].getEmail());
        record_id_profile_address.setText(user[0].getAddress());


        record_next_01.setOnClickListener(this);
        record_next_02.setOnClickListener(this);
        record_next_03.setOnClickListener(this);
        record_next_04.setOnClickListener(this);
        record_next_05.setOnClickListener(this);
        record_next_06.setOnClickListener(this);
        record_next_07.setOnClickListener(this);
        record_next_08.setOnClickListener(this);
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == record_next_01.getId()
                || view.getId() == record_next_02.getId()
                || view.getId() == record_next_03.getId()
                || view.getId() == record_next_06.getId()
                || view.getId() == record_next_07.getId()
                || view.getId() == record_next_08.getId()
        ) {
            String propNeedUpdate = "";
            if (view.getId() == record_next_01.getId()) {
                propNeedUpdate = "fullname";
            } else if (view.getId() == record_next_02.getId()) {
                propNeedUpdate = "bio";
            } else if (view.getId() == record_next_03.getId()) {
                propNeedUpdate = "gender";
            } else if (view.getId() == record_next_06.getId()) {
                propNeedUpdate = "phone";
            } else if (view.getId() == record_next_07.getId()) {
                propNeedUpdate = "email";
            } else if (view.getId() == record_next_08.getId()) {
                propNeedUpdate = "address";
            }

            Intent moveActivity = new Intent(curContext, activity_edit_profile.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId", curUser.getUserId());
            bundle.putString("propNeedUpdate", propNeedUpdate);
            moveActivity.putExtras(bundle);
            curContext.startActivity(moveActivity);
        }

        if (view.getId() == record_next_04.getId()) {
            String propNeedUpdate = "birthdate";

            Intent moveActivity = new Intent(curContext, activity_edit_dob.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId", curUser.getUserId());
            bundle.putString("propNeedUpdate", propNeedUpdate);
            moveActivity.putExtras(bundle);
            curContext.startActivity(moveActivity);
        }

        if (view.getId() == record_next_05.getId()) {
            String propNeedUpdate = "password";

            Intent moveActivity = new Intent(curContext, activity_change_password.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId", curUser.getUserId());
            bundle.putString("propNeedUpdate", propNeedUpdate);
            moveActivity.putExtras(bundle);
            curContext.startActivity(moveActivity);
        }
    }


}
