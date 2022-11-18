package com.example.doan;

public class Order{


    private int id;
    private String name;
    private String old_cost;
    private String new_cost;
    private String number;
    private String size;
    private String color;
    private String charge_tranfer;
    private String cost_final;
    private Integer image;


    public Order(int id, String name, String old_cost, String new_cost, String number, String size,String color,String charge_tranfer,String cost_final,Integer img) {
        this.setId(id);
        this.setName(name);
        this.setOld_cost(old_cost);
        this.setNew_cost(new_cost);
        this.setNumber(number);
        this.setSize(size);
        this.setColor(color);
        this.set_charge_tranfer(charge_tranfer);
        this.set_cost_final(cost_final);
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
    public String get_charge_tranfer() {
        return charge_tranfer;
    }

    public void set_charge_tranfer(String charge_tranfer) {
        this.charge_tranfer = charge_tranfer;
    }
    public String get_cost_final() {
        return cost_final;
    }

    public void set_cost_final(String cost_final) {
        this.cost_final = cost_final;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
