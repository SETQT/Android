package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_register extends Activity {
    View btn_login;
    Button btn_register, btn_google_dk, btn_facebook_dk;
    EditText edittext_tk_dk, edittext_mk_dk, edittext_nhaplaimk;

    // kết nối database
    

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
                Boolean isPass = false;

                String tk = edittext_tk_dk.getText().toString();
                String mk = edittext_mk_dk.getText().toString();
                String mkAgain = edittext_nhaplaimk.getText().toString();

                if(tk.equals("") || mk.equals("") || mkAgain.equals("")) {
                    Toast.makeText(activity_register.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    isPass = false;
                    return;
                }

                if(mk.equals(mkAgain)) {
                    Toast.makeText(activity_register.this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                    isPass = false;
                    return;
                }

                // check tài khoản đã tồn tại hay chưa

                // mã hóa mật khẩu

                // lưu vào database

                // chuyển sang giao diện đăng nhập
                Intent moveActivity = new Intent(activity_register.this, activity_login.class);
                startActivity(moveActivity);
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
}
