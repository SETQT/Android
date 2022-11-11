package com.example.androidproject08;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class User {
    private String username;
    private String password;
    private String fullname;
    private String gender;
    private String phone;
    private String address;
    private String email;
    private String userId;
    private Cart cart;

//    public DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

//    public User(String id,String username,String password ,String email,
//                String phone,String gender,String fullname) {
//        this.setFullname(fullname);
//        this.setUserId(id);
//        this.setUsername(username);
//        this.setEmail(email);
//        this.setUserId(getUserId());
//        this.setPassword(password);
//        this.setPhone(phone);
//        this.setGender(gender);
//    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

//    public void loadById(Integer id){
//        Query allPost=mDatabase.child("user").child(id.toString()).child("email");
//        allPost.addListenerForSingleValueEvent(new ValueEventListener() {
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
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}