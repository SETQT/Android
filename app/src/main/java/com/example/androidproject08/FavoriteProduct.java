package com.example.androidproject08;

import java.util.ArrayList;

public class FavoriteProduct {
    private String user;
    private ArrayList<Product> products;
    private String id;

    public FavoriteProduct() {}

    public FavoriteProduct(String user, ArrayList<Product> products) {
        this.user = user;
        this.products = products;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
