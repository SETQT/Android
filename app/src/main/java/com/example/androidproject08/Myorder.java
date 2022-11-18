package com.example.androidproject08;

public class Myorder {
    private int id;
    private Integer image;
    private String name;
    private String size;
    private String old_cost;
    private String new_cost;
    private String count;
    private String total;

    public Myorder(int id, Integer image, String name, String size, String old_cost, String new_cost, String count, String total) {
        this.setId(id);
        this.setImage(image);
        this.setName(name);
        this.setSize(size);
        this.setOld_cost(old_cost);
        this.setNew_cost(new_cost);
        this.setCount(count);
        this.setTotal(total);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}



