package com.example.androidproject08;

public class MyCart {
    private Integer amount; // lưu số lượng của mặt hàng này trong giỏ
    private String id; // id cart
    private String image; // lưu hình ảnh mô tả sản phẩm
    private String name; // tên sản phẩm
    private Integer price; // giá của sản phẩm
    private Integer sale; // phần trăm giảm giá đơn vị  %
    private String ownCart; // chủ của giỏ hàng này
    private String idDoc; // id sản phẩm
    private String size;
    private String color;

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public MyCart() {
    }

    public MyCart(Integer amount, String id, String image, String name, Integer price, Integer sale, String ownCart, String idDoc, String size, String color) {
        this.amount = amount;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.sale = sale;
        this.ownCart = ownCart;
        this.idDoc = idDoc;
        this.size = size;
        this.color = color;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public void setOwnCart(String ownCart) {
        this.ownCart = ownCart;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getSale() {
        return sale;
    }

    public String getOwnCart() {
        return ownCart;
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

    public Integer getOldPrice() {
        return (this.getPrice() / (100 - this.getSale())) * 100;
    }

    static public boolean checkDuplicatedCart(MyCart cartA, MyCart cartB) {
        if(cartA.getColor().equals(cartB.getColor()) && cartA.getSize().equals(cartB.getSize())) {
            return true;
        }

        return false;
    }
}
