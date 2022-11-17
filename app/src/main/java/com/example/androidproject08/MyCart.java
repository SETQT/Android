package com.example.androidproject08;

public class MyCart {


    private int id;
    private String name;
    private String old_cost;
    private String new_cost;
    private String number;
    private Integer image;


    public MyCart(int id, String name, String old_cost, String new_cost, String number, Integer img) {
        this.setId(id);
        this.setName(name);
        this.setOld_cost(old_cost);
        this.setNew_cost(new_cost);
        this.setNumber(number);
        this.setImage(img);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOld_cost() {
        return old_cost;
    }

    public void setOld_cost(String old_cost) {
        this.old_cost = old_cost;
    }

    public String getNew_cost() {
        return new_cost;
    }

    public void setNew_cost(String new_cost) {
        this.new_cost = new_cost;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
