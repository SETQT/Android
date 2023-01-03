package com.example.androidproject08.Services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidproject08.User;
import com.example.androidproject08.activity_login;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;

import java.io.File;

public class CheckBannedService extends Service {

    // kết nối firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef = db.collection("users");

    // kết nối sqlite
    SQLiteDatabase sqlite;

    @Override
    public void onCreate() {

        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String username = "";

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);

        username = c1.getString(0);

        usersRef.document("bx4enrytfkOt9Uqklp1f")
                .addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        User user = snapshot.toObject(User.class);

                        if (user.getStatus() == 1) {
                            Log.i("TAG", "onEvent: " + getApplicationContext().toString());
                            Intent moveActivity = new Intent(getApplicationContext(), activity_login.class);
                            moveActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            // đăng xuất tài khoản
                            sqlite.execSQL("DROP TABLE IF EXISTS USER; "); // xóa bảng <=> xóa phiên đăng nhập hiện tại

                            startActivity(moveActivity);
                        }
                    }
                });

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
