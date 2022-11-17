package com.example.androidproject08;


import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.AsynchronousChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.callback.Callback;

public class User {
    private String username;
    private String password;
    private String fullname;
    private String gender;
    private String phone;
    private String address;
    private String email;
    private int userId;
    private Cart cart;





    public DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(int id,String username,String password ,String email,
                String phone,String gender,String fullname) {
        this.setFullname(fullname);
        this.setUserId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setUserId(getUserId());
        this.setPassword(password);
        this.setPhone(phone);
        this.setGender(gender);

    }

    public void loadUserbyId(Integer id){
        this.userId=id;
        loadPropById(id,email);
        loadPropById(id,fullname);
        loadPropById(id,gender);
        loadPropById(id,phone);
        loadPropById(id,email);
//        loadPropById(id,email);
//        loadPropById(id,email);
    }

    public void loadPropById(Integer id,String props){
//        String email = mDatabase.child(id.toString()).child("email");
    Query allPost=mDatabase.child("user").child(id.toString()).child(props);
    allPost.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String result = dataSnapshot.getValue().toString();
//            Log.i("testdb", "loadById: "+result);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


//    String a = mDatabase.child("user").child(id.toString()).child("email").get().getResult().toString();
//            Log.i("testdb", "loadById: "+a);

}
    List<String> findIntegers(String stringToSearch) {
        Pattern integerPattern = Pattern.compile("-?\\d+");
        Matcher matcher = integerPattern.matcher(stringToSearch);

        List<String> integerList = new ArrayList<>();
        while (matcher.find()) {
            integerList.add(matcher.group());
        }

        return integerList;
    }
public void setvalue(float a, float b){
        a=b;
}
public float loadPriceFromid(Integer type,Integer product ){
    final float[] priceProduct = {0};
//     ArrayList<Float> posts = null;
//    final float[] priceProduct = {0};
    Query allPost=mDatabase.child("Catagory").child(type.toString()).child("listProduct").child(product.toString()).child("price");
    allPost.addValueEventListener(new ValueEventListener() {
//        public  void  setPrice(float a){
//            priceProduct =a;
//        }
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
//            priceProduct = new
//            posts = new ArrayList<>();

            String result = dataSnapshot.getValue().toString();
            float price =Float.parseFloat(result);

//            priceProduct[0] =price;

                 priceProduct[0] =price;
//            posts.add(price);
//            priceProduct =price;
//            return price;

            Log.i("testpr", "loadById: "+   priceProduct[0]);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }


    });
//            Log.i("testpr", "loadById: "+   posts.get(0));
//            Log.i("testpr", "loadById: "+ priceProduct[0]);
//    float returnn = posts.get(0);
    return priceProduct[0];
}
    public void loadCart(Integer id) {

        Map<String, String> product = new HashMap<String, String>();
//        ArrayList<String> listState =new ArrayList<String>();
        final String[] listState = new String[1];
//        final List<String>[] listState = new List[]{null};
        ArrayList<String> alist = new ArrayList<String>();
//        ArrayList<String> state =new ArrayList<String>();
//        ArrayList<String> state =new ArrayList<String>();
//        ArrayList<String> state =new ArrayList<String>();

        readData(id,new FirebaseCallback() {
            @Override
            public void onCallback(List<String> list, String listTo) {
//                if (listTo.equals("state")) {
//                    list.forEach((element) -> {
//
//                        alist.add()
//                    });
//
//                }
//                   listState[0] =list;
//                   alist=list;
            }
        });


//
//        float price= loadPriceFromid(126,125);
//        Log.i("testdb", "loadById: "+price);
//        Log.i("testdblist", "loadById: "+product.get("715-candy"));

    }
private void readData(Integer id,FirebaseCallback callback){
//    Integer id =1242;
    Query allPost=mDatabase.child("user").child(id.toString()).child("cart").child("product");
    allPost.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {


//                Cart cartFromDB = new Cart();
//            List<String> listState = null;
            ArrayList<String> listState = new ArrayList<String>();
            ArrayList<String> listAmount = new ArrayList<String>();
            for (DataSnapshot item : dataSnapshot.getChildren())
            {

                String state=item.child("state").getValue(). toString();
                String amount=item.child("amount").getValue(). toString();
//                Log.i("haah", "onDataChange: "+state);
                listState.add(state);
                listAmount.add(amount);
//                   listState[0] =state;
//                   String amount=item.child("amount").getValue().toString();

//                    Log.i("testdb", "loadById: "+amount);
//
            }
            callback.onCallback(listState,"state");
            callback.onCallback(listAmount,"amount");
//                AsynchronousChannel float price= loadPriceFromid(126,125);
//                                    Log.i("testdb", "loadById: "+price);

//                String result = dataSnapshot.getValue().toString();

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}
    private interface FirebaseCallback{
        void  onCallback(List<String> list,String type);
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
