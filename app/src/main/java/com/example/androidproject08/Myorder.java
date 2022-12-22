package com.example.androidproject08;

import java.io.Serializable;

public class Myorder implements Serializable {
    private String id; //id san pham
    private String image;
    private String name;
    private String size;
    private String color;
    private Integer oldCost;
    private Integer newCost;
    private Integer count;
    private Integer total;
    private String idCart; // id cart
    private String idOrder; // id order của đơn hàng

    public Myorder() {
    }

    public Myorder(String id, String image, String name, String size, String color, Integer oldCost, Integer newCost, Integer count, Integer total, String idCart) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.size = size;
        this.color = color;
        this.oldCost = oldCost;
        this.newCost = newCost;
        this.count = count;
        this.total = total;
        this.idCart = idCart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getOldCost() {
        return oldCost;
    }

    public void setOldCost(Integer oldCost) {
        this.oldCost = oldCost;
    }

    public Integer getNewCost() {
        return newCost;
    }

    public void setNewCost(Integer newCost) {
        this.newCost = newCost;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }
}



