package com.example.androidproject08;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {
//    Map<Integer, String> product = new HashMap<Integer, String>();
    ArrayList<MyCart> product=new ArrayList<MyCart>();
    float total;
    Cart(MyCart mycart ,float price){
        this.product.add(mycart);
        this.total+=(price* Integer.parseInt(mycart.getNumber()));

    }

    Cart(){

    }
    Cart(ArrayList<MyCart>  products){
        this.product=products;

    }
}


