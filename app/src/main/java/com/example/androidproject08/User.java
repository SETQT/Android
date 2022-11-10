package com.example.androidproject08;


import com.google.firebase.database.DatabaseReference;

public class User {
    public String username;
    public String email;
    public String userId;
    public DatabaseReference mDatabase;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String userId) {
        this.username = username;
        this.email = email;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }
}
