package com.example.androidproject08;

import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

public class Product {
    private String category; // loại sản phẩm
    private String name; // tên sản phẩm
    private Integer price; // giá sản phẩm
    private Integer amount; // số lượng sản phảm hiện có
    private Integer sale; // % giảm giá của sản phẩm
    private String image; // ảnh mô tả sản phẩm
    private String description; // mô tả sản phẩm
    private Integer amountOfSold; // số lượng sản phẩm đã bán
    private ArrayList<String> typeColor; // các loại màu sản phẩm
    private ArrayList<String> typeSize; // các loại kích thước sản phẩm
    private String idDoc; // id document của product này

    public Product() {
    }

    public Product(String category, String name, Integer price, Integer amount, Integer sale, String image, String description, Integer amountOfSold, ArrayList<String> typeColor, ArrayList<String> typeSize) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.sale = sale;
        this.image = image;
        this.description = description;
        this.amountOfSold = amountOfSold;
        this.typeColor = typeColor;
        this.typeSize = typeSize;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getSale() {
        return sale;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAmountOfSold() {
        return amountOfSold;
    }

    public ArrayList<String> getTypeColor() {
        return typeColor;
    }

    public ArrayList<String> getTypeSize() {
        return typeSize;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmountOfSold(Integer amountOfSold) {
        this.amountOfSold = amountOfSold;
    }

    public void setTypeColor(ArrayList<String> typeColor) {
        this.typeColor = typeColor;
    }

    public void addToTypeColor(String color) {
        this.typeColor.add(color);
    }

    public void setTypeSize(ArrayList<String> typeSize) {
        this.typeSize = typeSize;
    }

    public void addToTypeSize(String size) {
        this.typeSize.add(size);
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(idDoc, product.idDoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDoc);
    }
}
