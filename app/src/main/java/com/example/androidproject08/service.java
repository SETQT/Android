package com.example.androidproject08;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class service {

    private DatabaseReference database;


//    public User getUserbyId(Integer id) {
//        String ids = id.toString();
////        User user = new User();
//        boolean check = false;
//        database.child("user").child(ids).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    Object obj = task.getResult().getValue();
////                    Log.i("tagh----------aha", "writeNewUser: " + obj.email);
//
//                }
//            }
//        });
//        return user;
//
//    }

}
