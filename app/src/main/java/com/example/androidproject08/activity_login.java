package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class activity_login extends Activity {
    View btn_register;
    EditText edittext_tk, edittext_mk;
    Button btn_login;

    // kết nối sqlite
    SQLiteDatabase sqlite;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");
    ArrayList<User> usernameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register = (View) findViewById(R.id.rectangle_4);
        btn_login = (Button) findViewById(R.id.btn_dangnhap);
        edittext_tk = (EditText) findViewById(R.id.edittext_tk);
        edittext_mk = (EditText) findViewById(R.id.edittext_mk); // mật khẩu người dùng

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk = edittext_tk.getText().toString();// tài khoản người dùng
                String mk = edittext_mk.getText().toString();// mật khẩu người dùng

                // kiem tra empty
                if (tk.equals("") || mk.equals("")) {
                    Toast.makeText(activity_login.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // kiểm tra với database
                usernameList = new ArrayList<>();

                readData(new FirestoreCallBack() {
                    @Override
                    public void onCallBack(List<User> list) {
                        if (usernameList.size() == 0) {
                            Toast.makeText(activity_login.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            // kiểm tra password
                            if (usernameList.get(0).checkPassword(mk)) {
                                // thêm vào cookie => lần sau vào không cần đăng nhập nữa
                                // khi người dùng đăng nhập vào thì ta lấy username của người dùng lưu lại
                                // khi khởi động ứng dụng ta truy vấn vào sqlite này để xem nếu username tồn tại rồi thì
                                // không cần đăng nhập vào thẳng trang dash_board cho người dùng
                                File storagePath = getApplication().getFilesDir();
                                String myDbPath = storagePath + "/" + "loginDb";
                                sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

                                if(!tableExists(sqlite, "USER")) {
                                    // create table USER
                                    sqlite.execSQL("create table USER ("
                                            + "username text PRIMARY KEY);");
                                }

                                sqlite.execSQL("insert into USER(username) values ('" + tk + "');");

                                // chuyển sang giao diện chính
                                Intent moveActivity = new Intent(activity_login.this, activity_dashboard.class);
                                startActivity(moveActivity);
                            } else {
                                Toast.makeText(activity_login.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                }, tk);
            }
        });

        // chuyển sang activity đăng ký
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(activity_login.this, activity_register.class);
                startActivity(moveActivity);
            }
        });
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
                            }

                            firestoreCallBack.onCallBack(usernameList);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public boolean tableExists(SQLiteDatabase db, String tableName) {
        String mySql = "SELECT name FROM sqlite_master " + " WHERE type='table' " + " AND name='" + tableName + "'";
        int resultSize = db.rawQuery(mySql, null).getCount();
        if (resultSize != 0) {
            return true;
        }
        else return false;
    }
}
