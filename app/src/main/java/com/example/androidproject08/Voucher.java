package com.example.androidproject08;

public class Voucher {
    private int id;
    private Integer image;
    private String title;
    private String free_cost;
    private String count;
    private String start_date;
    private String expiry_date;

    public Voucher(int id, Integer image, String title, String free_cost, String count, String start_date, String expiry_date) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.free_cost = free_cost;
        this.count = count;
        this.start_date = start_date;
        this.expiry_date = expiry_date;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFree_cost() {
        return free_cost;
    }

    public void setFree_cost(String free_cost) {
        this.free_cost = free_cost;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}



