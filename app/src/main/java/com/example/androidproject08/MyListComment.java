package com.example.androidproject08;

public class MyListComment {

    private int id;
    private String username;
    private String type_product;
    private String comment;
    private String time_comment;


    public MyListComment(int id, String username,String type_product,String comment,String time_comment) {
        this.setId(id);
        this.setUserName(username);
        this.setType_product(type_product);
        this.setComment(comment);
        this.setTime_comment(time_comment);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getType_product() {
        return type_product;
    }

    public void setType_product(String type_product) {
        this.type_product = type_product;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime_comment() {
        return time_comment;
    }

    public void setTime_comment(String time_comment) {
        this.time_comment = time_comment;
    }
}

