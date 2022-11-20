package com.example.androidproject08;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.login.LoginManager;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class activity_profile extends Activity implements View.OnClickListener {

    //khai báo biến UI
    RelativeLayout icon_home, icon_scan, icon_notify;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");
    ArrayList<User> usernameList;



    // sqlite
    SQLiteDatabase sqlite;

    // khai báo biến UI
    TextView username_profile, id_cycle_red_giohang;
    Button dangxuat;
    View icon_cart;
    RelativeLayout rectangle_profile_hosocuatoi, icon_voucher_profile, icon_donhangcuatoi;

//    android:id="@+id/profile_avatar"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        icon_home = (RelativeLayout) findViewById(R.id.icon_home);
        icon_home.setOnClickListener(this);
        icon_scan = (RelativeLayout) findViewById(R.id.icon_scan);
        icon_scan.setOnClickListener(this);
        icon_notify = (RelativeLayout) findViewById(R.id.icon_notify);
        icon_notify.setOnClickListener(this);

        username_profile = (TextView) findViewById(R.id.username_profile);
        id_cycle_red_giohang = (TextView) findViewById(R.id.id_cycle_red_giohang);
        dangxuat = (Button) findViewById(R.id.dangxuat);
        dangxuat.setOnClickListener(this);
        icon_cart = (View) findViewById(R.id.icon_cart);
        icon_cart.setOnClickListener(this);
        rectangle_profile_hosocuatoi = (RelativeLayout) findViewById(R.id.rectangle_profile_hosocuatoi);
        rectangle_profile_hosocuatoi.setOnClickListener(this);
        icon_voucher_profile = (RelativeLayout) findViewById(R.id.icon_voucher_profile);
        icon_voucher_profile.setOnClickListener(this);
        icon_donhangcuatoi = (RelativeLayout) findViewById(R.id.icon_donhangcuatoi);
        icon_donhangcuatoi.setOnClickListener(this);


        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        String username = c1.getString(0);

        // kiểm tra với database
        usernameList = new ArrayList<>();

        // lấy thông tin người dùng
        readData(new FirestoreCallBack() {
            @Override
            public void onCallBack(List<User> list) {
                if (usernameList.get(0).getFullname() == null) {


                    username_profile.setText(usernameList.get(0).getUsername());
                } else {
//                    Log.d("USER", "downdxxxFile: "+usernameList.get(0).getImage());

//                    downloadFile(usernameList.get(0));
                    username_profile.setText(usernameList.get(0).getFullname());
                }
            }
        }, username);


    }

    // truy vấn dữ liệu từ database với username mà người dùng nhập
    private void readData(FirestoreCallBack firestoreCallBack, String tk) {
        usersRef
                .whereEqualTo("username", tk)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                usernameList.add(user);
                                loadImage(user.getUsername());
                            }

                            firestoreCallBack.onCallBack(usernameList);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == icon_voucher_profile.getId()) {
            // chuyển sang giao diện my voucher
            Intent moveActivity = new Intent(getApplicationContext(), activity_voucher.class);
            startActivity(moveActivity);
        }

        if (view.getId() == icon_cart.getId()) {
            // chuyển sang giao diện my cart
            Intent moveActivity = new Intent(getApplicationContext(), activity_mycart.class);
            moveActivity.putExtra("name_activity", "activity_profile");
            startActivity(moveActivity);
        }

        if (view.getId() == dangxuat.getId()) {
            // đăng xuất tài khoản
            sqlite.execSQL("DROP TABLE IF EXISTS USER; "); // xóa bảng <=> xóa phiên đăng nhập hiện tại
            Intent moveActivity = new Intent(getApplicationContext(), activity_login.class);

            LoginManager.getInstance().logOut();
            startActivity(moveActivity);
        }

        if (view.getId() == icon_home.getId()) {
            // trở về giao diện dashboard
            Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
            startActivity(moveActivity);
        }

        if (view.getId() == icon_scan.getId()) {
            // chuyển sang giao diện scan mã qr
            Intent moveActivity = new Intent(getApplicationContext(), activity_dashboard.class);
            startActivity(moveActivity);
        }

        if (view.getId() == icon_notify.getId()) {
            // chuyển sang giao diện thông báo
            Intent moveActivity = new Intent(getApplicationContext(), activity_notify.class);
            startActivity(moveActivity);
        }

        if (view.getId() == rectangle_profile_hosocuatoi.getId()) {
            // chuyển sang giao diện hồ sơ cá nhân
            Intent moveActivity = new Intent(getApplicationContext(), activity_record.class);
            startActivity(moveActivity);
        }

        if(view.getId() == icon_donhangcuatoi.getId()) {
            // chuyển sang giao diện đơn hàng của tôi
            Intent moveActivity = new Intent(getApplicationContext(), activity_myorder.class);
            startActivity(moveActivity);
        }


    }
    public void loadImage(String name) {
        ImageView  header = (ImageView) findViewById(R.id.rectangle_profile);
        ImageView avatar = (ImageView) findViewById(R.id.profile_avatar);

        downloadFile(header,name+"background");
        downloadFile(avatar,name+"avatar");
    }


    public void downloadFile(ImageView avatar,String name) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");
        StorageReference islandRef = storageRef.child("ProfileUser/"+name);
//        Log.d("ss", "downloadFile: "+islandRef);
//        if (islandRef.ex) islandRef = storageRef.child("ProfileUser/"+"default");
//        String nameImage="";


        try {
            File localFile = File.createTempFile("tempfile", ".jpg");

            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    Log.d("down", "success: ");

                    // Local temp file has been created
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                    ImageView imgProduct = (ImageView) findViewById(R.id.custom_mycart_picture);
                    avatar.setImageBitmap(bitmap);
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
