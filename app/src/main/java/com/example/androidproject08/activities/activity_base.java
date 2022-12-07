package com.example.androidproject08.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject08.utilities.Constants;
import com.example.androidproject08.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

public class activity_base extends AppCompatActivity {

    private DocumentReference documentReference;
    private FirebaseFirestore db;
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(getApplicationContext());
        db = FirebaseFirestore.getInstance();
        //loadInfoSender
        documentReference = db.collection("users")
                .document(preferenceManager.getString(Constants.KEY_USER_ID));
    }

    @Override
    protected void onPause() {
        super.onPause();
        documentReference.update(Constants.KEY_AVAILABILITY, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        documentReference.update(Constants.KEY_AVAILABILITY, 1);
    }

    private void loadInfoSender() {
        // kết nối sqlite
        File storagePath = getApplication().getFilesDir();
        String myDbPath = storagePath + "/" + "loginDb";
        SQLiteDatabase sqlite = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); // open db

        String mySQL = "select * from USER";
        Cursor c1 = sqlite.rawQuery(mySQL, null);
        c1.moveToPosition(0);
        String username = c1.getString(0);

        try {
            // lấy thông tin senderID
            db.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnCompleteListener(task -> {
                        //Log.d("DOC", "sldoc: " + task.getResult().getDocuments().size());
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());// lưu ID sender
                            preferenceManager.putString(Constants.KEY_USER_NAME, documentSnapshot.getString("fullname"));
                            preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString("image"));

                        } else {

                            Log.d("ERROR", "loadInfoSender: lấy thông tin không thành công! ");
                        }
                    });
        } catch (Exception err){
            Log.d("ERROR", "loadInfoSender: ERROr" + err);
        }
    }

}
