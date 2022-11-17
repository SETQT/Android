package com.example.androidproject08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class activity_welcomepage extends Activity {

    private DatabaseReference mDatabase;
    Button btn_batdau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        btn_batdau = (Button) findViewById(R.id.btn_batdau);

        mDatabase = FirebaseDatabase.getInstance().getReference();


//        Query allPost=mDatabase.child("user").child("1242").child("email");
//        DatabaseReference data=mDatabase.child("user").child("1242").child("email");
//        data.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String result = dataSnapshot.getValue().toString();
//                Log.i("testdb", "loadById: "+result);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        sendData(activity_welcomepage.this);
//






        btn_batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
//                mDatabase.child("voucher").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        User user = dataSnapshot.getValue(User.class);
//
//                        Log.d("tht", "User name: " + user.getName() + ", email " + user.getEmail());
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//                        // Failed to read value
//                        Log.w(TAG, "Failed to read value.", error.toException());
//                    }
//                });



                Intent callMainActivity = new Intent(activity_welcomepage.this, activity_login.class);
                startActivity(callMainActivity);
            }
        });
    }



    public void sendData(activity_welcomepage view) {
        writeNewUser("1234", "hao123", "truongvanhao159@gmail.com");
    }

    public void writeNewUser(String userId, String username, String email) {

            User user = new User();
            user.loadCart(1242);
//        mDatabase.child("user").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                                                  @Override
//                                                  public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                                      if (task.isSuccessful()) {
////                                                          User obj = task.getResult().getValue(User.class);
//                                                          Object obj = task.getResult().getValue();
//                                                          String objstr = obj.toString();
////
//                                                        JSONObject jsonObject = new JSONObject(objstr);
////                                                          ObjectMapper mapper = new ObjectMapper();
////                                                         mapper.writeValueAsString(objstr);
//                                                          Log.i("tagh----------aha", "writeNewUser: "+obj.getClass());
//                                                      }
//                                                  }
//                                              });

    }
//        Query post = FirebaseDatabase.getInstance().getReference().child("user").child("1234");
//        post.addListenerForSingleValueEvent(new ValueEventListener(){
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                ArrayList<User> posts ;
//                User user = dataSnapshot.getValue(User.class);
//                Log.i("tagh----------aha", "writeNewUser: "+user.getUserId());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//
//
//            }





//        User user = new User(username, email, userId);s
//
//        mDatabase.child("users").child(user.getUserId()).setValue(user);

//        String a=mDatabase.child("users").getKey();


}
