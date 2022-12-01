package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class record_asynctask extends AsyncTask<Void, User, User> implements View.OnClickListener {
    Activity curContext;
    String username;
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference usersRef = db.collection("users");
    TextView record_id_profile_ten, record_id_profile_bio, record_id_profile_sex, record_id_profile_dob, record_id_profile_phone, record_id_profile_email;
    View record_next_01, record_next_02, record_next_03, record_next_04, record_next_05, record_next_06, record_next_07;
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
        this.curUser = new User();
        this.record_next_01 = curContext.findViewById(R.id.record_next_01);
        this.record_next_02 = curContext.findViewById(R.id.record_next_02);
        this.record_next_03 = curContext.findViewById(R.id.record_next_03);
        this.record_next_04 = curContext.findViewById(R.id.record_next_04);
        this.record_next_05 = curContext.findViewById(R.id.record_next_05);
        this.record_next_06 = curContext.findViewById(R.id.record_next_06);
        this.record_next_07 = curContext.findViewById(R.id.record_next_07);

        if(avatar!=null) {
            this.avatar = avatar;
        }

        if(background!=null) {
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

        record_id_profile_ten.setText(user[0].getFullname());
        record_id_profile_bio.setText(user[0].getBio());
        record_id_profile_sex.setText(user[0].getGender());
//        record_id_profile_dob.setText(user[0].getBirthdate().toString());
        record_id_profile_phone.setText(user[0].getPhone());
        record_id_profile_email.setText(user[0].getEmail());

        record_next_01.setOnClickListener(this);
        record_next_02.setOnClickListener(this);
        record_next_03.setOnClickListener(this);
        record_next_04.setOnClickListener(this);
        record_next_05.setOnClickListener(this);
        record_next_06.setOnClickListener(this);
        record_next_07.setOnClickListener(this);
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
                || view.getId() == record_next_04.getId()
                || view.getId() == record_next_06.getId()
                || view.getId() == record_next_07.getId()
        ) {
            String propNeedUpdate = "";
            if (view.getId() == record_next_01.getId()) {
                propNeedUpdate = "fullname";
            } else if (view.getId() == record_next_02.getId()) {
                propNeedUpdate = "bio";
            } else if (view.getId() == record_next_03.getId()) {
                propNeedUpdate = "gender";
            } else if (view.getId() == record_next_04.getId()) {
                propNeedUpdate = "birthdate";
            } else if (view.getId() == record_next_06.getId()) {
                propNeedUpdate = "phone";
            } else if (view.getId() == record_next_07.getId()) {
                propNeedUpdate = "email";
            }

            Intent moveActivity = new Intent(curContext, activity_edit_profile.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId", curUser.getUserId());
            bundle.putString("propNeedUpdate", propNeedUpdate);
            moveActivity.putExtras(bundle);
            curContext.startActivity(moveActivity);
        }

        if(view.getId() == record_next_05.getId()) {
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
