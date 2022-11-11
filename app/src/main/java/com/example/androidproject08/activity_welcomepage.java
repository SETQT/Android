package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_welcomepage extends Activity {

    Button btn_batdau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        btn_batdau = (Button) findViewById(R.id.btn_batdau);

        btn_batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callMainActivity = new Intent(activity_welcomepage.this, activity_login.class);
                startActivity(callMainActivity);
            }
        });
    }
}
