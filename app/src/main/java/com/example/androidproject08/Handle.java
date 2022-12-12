package com.example.androidproject08;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

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

    static public void ResetBackgroundStar(View star1, View star2, View star3, View star4, View star5) {
        star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
        star5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A09B9B")));
    }

    static public void setStar(View star1, View star2, View star3, View star4, View star5, Integer countStar) {
        switch (countStar) {
            case 5:
                Handle.ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star5.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                break;
            case 4:
                Handle.ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                break;
            case 3:
                Handle.ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                break;
            case 2:
                Handle.ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                star2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                break;
            case 1:
                Handle.ResetBackgroundStar(star1, star2, star3, star4, star5);
                star1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F9A826")));
                break;
            case 0:
                Handle.ResetBackgroundStar(star1, star2, star3, star4, star5);
            default:
                break;
        }
    }
}

