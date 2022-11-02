package com.example.androidproject08;

public class Notify {


    private int id;
    private int CheckProduct;
    private int status;
    private String product;
    private String title;
    private String date;
    private Integer image;
    private String content;


    public Notify(int id,int CheckProduct, String title, int status, String product, String date, String content, Integer img) {
        this.setId(id);
        this.setCheckProduct(CheckProduct);
        this.setStatus(status);
        this.setProduct(product);
        this.setTitle(title);
        this.setDate(date);
        this.setImage(img);
        this.setContent(content);
    }

    public int getCheckProduct() {
        return CheckProduct;
    }

    public void setCheckProduct(int CheckProduct) {
        this.CheckProduct = CheckProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

