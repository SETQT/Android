package com.example.androidproject08;

public class Voucher {
    private int id;
    private Integer image;
    private String title;
    private String free_cost;
    private String date;

    public Voucher(int id, Integer image, String title, String free_cost, String date) {
        this.setId(id);
        this.setImage(image);
        this.setTitle(title);
        this.setFree_cost(free_cost);
        this.setDate(date);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}



