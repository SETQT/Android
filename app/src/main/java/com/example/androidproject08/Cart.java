package com.example.androidproject08;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<String, Integer> product = new HashMap<String, Integer>();
    float total;

    Cart() {
        this.product.put("amount", 0);
        this.product.put("totalMoney", 0);
    }

}