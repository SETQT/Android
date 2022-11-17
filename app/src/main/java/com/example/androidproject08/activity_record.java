package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class activity_record extends Activity {

    // sqlite
    SQLiteDatabase sqlite;

    // khai báo biến UI
    View record_ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        record_ic_back = (View) findViewById(R.id.record_ic_back);

        // get username từ sqlite
        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        String username = c1.getString(0);

        if (username == null) {
            return;
        }

        // query dữ liệu cho qua listview
        record_asynctask r_at = new record_asynctask(activity_record.this, username);
        r_at.execute();

        // quay trở lại activity profile
        record_ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveActivity = new Intent(getApplicationContext(), activity_profile.class);
                startActivity(moveActivity);
            }
        });
    }
}
