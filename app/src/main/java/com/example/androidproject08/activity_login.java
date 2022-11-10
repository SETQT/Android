package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_login extends Activity {
    View btn_register;
    EditText edittext_tk, edittext_mk;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register = (View) findViewById(R.id.rectangle_4);
        btn_login = (Button) findViewById(R.id.btn_dangnhap);
        edittext_tk = (EditText) findViewById(R.id.edittext_tk);
        edittext_mk = (EditText) findViewById(R.id.edittext_mk); // mật khẩu người dùng



        // kiểm tra empty
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isPass = false;
                String tk = edittext_tk.getText().toString();// tài khoản người dùng
                String mk = edittext_mk.getText().toString();// mật khẩu người dùng

                if(tk.equals("") || mk.equals("")) {
//                    Toast.makeText(activity_login.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(activity_login.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    isPass = false;
                    return;
                }

                isPass = true;

                // kiểm tra với database

                // giải mã mật khẩu

                // chuyển sang trang giao diện chính
                if(isPass) {
                    // lưu vào cache để nhận biết là được được đăng nhập

                    // chuyển sang giao diện chính
                    Intent moveActivity = new Intent(activity_login.this, MainActivity.class);
                    startActivity(moveActivity);
                }

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(activity_login.this, activity_register.class);
                startActivity(moveActivity);
            }
        });
    }
}
