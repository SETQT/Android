package com.example.androidproject08;

import java.util.Date;

public class Comment {
    private String idProduct;
    private String user;
    private String colorProduct;
    private String sizeProduct;
    private String content;
    private Date createdAt;
    private Integer countStar;

    public Comment() {

    }

    public Comment(String idProduct, String user, String colorProduct, String sizeProduct, String content, Date createdAt, Integer countStar) {
        this.idProduct = idProduct;
        this.user = user;
        this.colorProduct = colorProduct;
        this.sizeProduct = sizeProduct;
        this.content = content;
        this.createdAt = createdAt;
        this.countStar = countStar;
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
}
