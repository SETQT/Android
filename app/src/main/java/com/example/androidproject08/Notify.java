package com.example.androidproject08;

public class Notify {


    private int id;
    private String content;
    private String title;
    private String date;
    private Integer image;




    public Notify(int id,String title, String name, String date,Integer img) {
        this.setId(id);
        this.setContent(name);
        this.setTitle(title);
        this.setDate(date);
        this.setImage(img);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return getContent();
    }

    public void setName(String name) {
        this.setContent(name);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer date) {
        this.image = date;
    }
}
