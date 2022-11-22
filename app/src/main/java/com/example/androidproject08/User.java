package com.example.androidproject08;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class User {
    private String username;
    private String password;
    private String fullname;
    private String gender;
    private String phone;
    private String address;
    private String email;
    private String userId;
    private String bio;
    private Map<String, Integer> cart = new HashMap<>();
    private Date birthdate;
    private String image;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String username, String password, String email,
                String phone, String gender, String address, String fullname, Map<String, Integer> cart,String img) {
        this.username = username;
        this.image=img;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.userId = id;
        this.cart = cart;
    }

    public User(String username, String fullname, String email) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.cart.put("amount", 0);
        this.cart.put("totalMoney", 0);
    }

    public User(String username, String password) {
        // mã hóa mật khẩu
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }

            this.password = sb.toString();
            this.username = username;

            this.cart.put("amount", 0);
            this.cart.put("totalMoney", 0);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // mã hóa password người dùng nhập => check
    public boolean checkPassword(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }

            if (this.password.equals(sb.toString())) {
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

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

    public Map<String, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<String, Integer> cart) {
        this.cart = cart;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}