package com.example.androidproject08;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<Integer, String> product = new HashMap<Integer, String>();
    float total;
    Cart(int key,String state,float price){
        this.product.put(key,state);
        this.total+=price;
    }
}