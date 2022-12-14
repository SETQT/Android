package com.example.androidproject08;

import java.io.Serializable;

public class Scan implements Serializable {
    private String img;
    private String name;
    private String option;
    private Integer state;

    public Scan(){};

    public Scan(String img, String name, String option, Integer state) {
        this.img = img;
        this.name = name;
        this.option = option;
        this.state = state;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
