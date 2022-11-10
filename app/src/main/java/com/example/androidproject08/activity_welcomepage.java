package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_welcomepage extends Activity {

    private DatabaseReference mDatabase;
    Button btn_batdau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        btn_batdau = (Button) findViewById(R.id.btn_batdau);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        sendData(activity_welcomepage.this);

        btn_batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callMainActivity = new Intent(activity_welcomepage.this, activity_login.class);
                startActivity(callMainActivity);
            }
        });
    }

    public void sendData(activity_welcomepage view) {
        writeNewUser("1234", "hao123", "truongvanhao159@gmail.com");
    }

    public void writeNewUser(String userId, String username, String email) {
        User user = new User(username, email, userId);

        mDatabase.child("users").child(user.getUserId()).setValue(user);
    }
}
