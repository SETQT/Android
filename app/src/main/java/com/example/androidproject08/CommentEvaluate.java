package com.example.androidproject08;

import java.util.Date;

public class CommentEvaluate {
    private String idProduct;
    private String user;
    private String avatar;
    private String colorProduct;
    private String sizeProduct;
    private String content;
    private Date createdAt;
    private Integer countStar;
    private String reply;
    private String nameProduct;
    private String imgProduct;

    public CommentEvaluate() {

    }

    public CommentEvaluate(String idProduct, String user,String avatar, String colorProduct, String sizeProduct, String content, Date createdAt, Integer countStar, String reply, String nameProduct, String imgProduct) {
        this.idProduct = idProduct;
        this.user = user;
        this.avatar = avatar;
        this.colorProduct = colorProduct;
        this.sizeProduct = sizeProduct;
        this.content = content;
        this.createdAt = createdAt;
        this.countStar = countStar;
        this.reply = reply;
        this.nameProduct = nameProduct;
        this.imgProduct = imgProduct;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getColorProduct() {
        return colorProduct;
    }

    public void setColorProduct(String colorProduct) {
        this.colorProduct = colorProduct;
    }

    public String getSizeProduct() {
        return sizeProduct;
    }

    public void setSizeProduct(String sizeProduct) {
        this.sizeProduct = sizeProduct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCountStar() {
        return countStar;
    }

    public void setCountStar(Integer countStar) {
        this.countStar = countStar;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }
}
