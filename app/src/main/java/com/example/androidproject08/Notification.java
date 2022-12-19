package com.example.androidproject08;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    private String idDoc; // id document của notification này
    private String image;
    private String title;
    private String content;
    private String receiver;
    private Date date; // ngày gửi thông báo
    private Integer status; // thông báo đã được xem hay chưa 0: chưa, 1: rồi
    private String type; // loại thông báo

    public Notification() {
    }

    public Notification(String image, String title, String content, String receiver, String type) {
        this.image = image;
        this.title = title.toUpperCase();
        this.content = content;
        this.receiver = receiver;
        this.date = new Date();
        this.type = type;
        this.status = 0;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
