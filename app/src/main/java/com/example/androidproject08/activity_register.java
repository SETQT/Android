package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class activity_register extends Activity {
    // khai báo dữ liệu UI
    View btn_login;
    Button btn_register, btn_google_dk, btn_facebook_dk;
    EditText edittext_tk_dk, edittext_mk_dk, edittext_nhaplaimk;

    // khai báo dữ liệu BE
    ArrayList<User> usernameList;

    // kết nối database firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_login = (View) findViewById(R.id.rectangle_3);
        btn_register = (Button) findViewById(R.id.btn_dangky);
        edittext_tk_dk = (EditText) findViewById(R.id.edittext_tk_dk);
        edittext_mk_dk = (EditText) findViewById(R.id.edittext_mk_dk);
        edittext_nhaplaimk = (EditText) findViewById(R.id.edittext_nhaplaimk);
        btn_google_dk = (Button) findViewById(R.id.btn_google_dk);
        btn_facebook_dk = (Button) findViewById(R.id.btn_facebook_dk);

        // đăng ký bằng tài khoản google
        btn_google_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // xử lý
            }
        });

        // đăng ký bằng tài khoản facebook
        btn_facebook_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // xử lý
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tk = edittext_tk_dk.getText().toString();
                String mk = edittext_mk_dk.getText().toString();
                String mkAgain = edittext_nhaplaimk.getText().toString();

                if (tk.equals("") || mk.equals("") || mkAgain.equals("")) {
                    Toast.makeText(activity_register.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // kiểm tra mật khẩu nhập lại có khớp hay không
                if (!mk.equals(mkAgain)) {
                    Toast.makeText(activity_register.this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check tài khoản đã tồn tại hay chưa
                usernameList = new ArrayList<>();
                readData(new FirestoreCallBack() {
                    @Override
                    public void onCallBack(List<User> list) {
                        if (usernameList.size() == 0) {
                            // tạo user mới và thêm vào database
                            User newUser = new User(tk, mk);
                            usersRef.add(newUser);

                            // chuyển sang giao diện đăng nhập
                            Intent moveActivity = new Intent(activity_register.this, activity_login.class);
                            startActivity(moveActivity);
                        } else { // tài khoản đã tồn tại
                            Toast.makeText(activity_register.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }, tk);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(activity_register.this, activity_login.class);
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
}
