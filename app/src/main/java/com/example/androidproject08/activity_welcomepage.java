package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class activity_welcomepage extends Activity {

    Button btn_batdau;
    SQLiteDatabase sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        btn_batdau = (Button) findViewById(R.id.btn_batdau);

        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        if(tableExists(sqlite, "USER")) {
            String mySQL = "select * from USER";
            Cursor c1 = sqlite.rawQuery(mySQL, null);
            c1.moveToPosition(0);
            String username = c1.getString(0);

            if(username != null) {
                Intent callMainActivity = new Intent(activity_welcomepage.this, activity_dashboard.class);
                startActivity(callMainActivity);
                return;
            }
        }

        btn_batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callMainActivity = new Intent(activity_welcomepage.this, activity_login.class);
                startActivity(callMainActivity);
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
