package com.example.androidproject08;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Handle {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference usersRef = db.collection("users");
    static ArrayList<User> usernameList = new ArrayList<>();

    static public boolean tableExists(SQLiteDatabase db, String tableName) {
        String mySql = "SELECT name FROM sqlite_master " + " WHERE type='table' " + " AND name='" + tableName + "'";
        int resultSize = db.rawQuery(mySql, null).getCount();
        if (resultSize != 0) {
            return true;
        }
        else return false;
    }

    // truy vấn dữ liệu từ database với username mà người dùng nhập
    static public void readData(FirestoreCallBack firestoreCallBack, String tk) {
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

    static public String kFortmatter(String number) {
        String result = "";

        result = number.substring(0, number.length() - 3) + "k";

        return result;
    }
}

