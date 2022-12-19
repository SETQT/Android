package com.example.androidproject08;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;

public class activity_welcomepage extends Activity {

    Button btn_batdau;

    // sqlite
    SQLiteDatabase sqlite;

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        btn_batdau = (Button) findViewById(R.id.btn_batdau);

        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        if (Handle.tableExists(sqlite, "USER")) {
            String mySQL = "select * from USER";
            Cursor c1 = sqlite.rawQuery(mySQL, null);
            c1.moveToPosition(0);
            String username = c1.getString(0);

            if (username != null) {
                usersRef.whereEqualTo("username", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        User user = document.toObject(User.class);

                                        if (user.getStatus() == 0) {
                                            Intent callMainActivity = new Intent(activity_welcomepage.this, activity_dashboard.class);
                                            startActivity(callMainActivity);
                                        } else {
                                            new AlertDialog.Builder(activity_welcomepage.this)
                                                    .setTitle("KHÓA TÀI KHOẢN")
                                                    .setMessage("Chúng tôi rất lấy làm tiết tài khoản của bạn đã bị cấm vì vi phạm chính sách của chúng tôi!")
                                                    .setCancelable(true)
                                                    .show();

                                            sqlite.execSQL("DROP TABLE IF EXISTS USER; "); // xóa bảng <=> xóa phiên đăng nhập hiện tại
                                        }
                                    }
                                }
                            }
                        });
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
}
