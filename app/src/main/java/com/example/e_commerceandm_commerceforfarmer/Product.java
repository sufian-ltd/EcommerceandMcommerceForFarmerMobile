package com.example.e_commerceandm_commerceforfarmer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable{

    private static final long serialVersionUID=1L;
    @SerializedName("id")
    private int id;
    @SerializedName("category")
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getBeforeDiscount() {
        return beforeDiscount;
    }

    public void setBeforeDiscount(int beforeDiscount) {
        this.beforeDiscount = beforeDiscount;
    }

    public int getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(int afterDiscount) {
        this.afterDiscount = afterDiscount;
    }

    public Double getQtn() {
        return qtn;
    }

    public void setQtn(Double qtn) {
        this.qtn = qtn;
    }

    public Product(int id, String category, String name, String unit, int beforeDiscount, int afterDiscount, Double qtn) {
        this.id = id;

        this.category = category;
        this.name = name;
        this.unit = unit;
        this.beforeDiscount = beforeDiscount;
        this.afterDiscount = afterDiscount;
        this.qtn = qtn;
    }

    @SerializedName("name")

    private String name;
    @SerializedName("unit")
    private String unit;
    @SerializedName("beforeDiscount")
    private int beforeDiscount;

    public int getSells() {
        return sells;
    }

    public void setSells(int sells) {
        this.sells = sells;
    }

    @SerializedName("afterDiscount")
    private int afterDiscount;
    @SerializedName("qtn")
    private Double qtn;
    @SerializedName("image")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("sells")
    private int sells;
}
