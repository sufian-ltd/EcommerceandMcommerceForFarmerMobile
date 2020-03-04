package com.example.e_commerceandm_commerceforfarmer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable{

    private static final long serialVersionUID=1L;
    @SerializedName("id")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;
    @SerializedName("productName")
    private String productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @SerializedName("qtn")
    private Double qtn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Double getQtn() {
        return qtn;
    }

    public void setQtn(Double qtn) {
        this.qtn = qtn;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order(int id, int userId, int productId, Double qtn, int cost, String orderDate, String deliveryDate, String status) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.qtn = qtn;
        this.cost = cost;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;

    }

    @SerializedName("cost")

    private int cost;
    @SerializedName("orderDate")
    private String orderDate;
    @SerializedName("deliveryDate")
    private String deliveryDate;
    @SerializedName("status")
    private String status;

    public String getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(String supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @SerializedName("supplierStatus")
    private String supplierStatus;
    @SerializedName("userStatus")
    private String userStatus;
    @SerializedName("payment")
    private String payment;

}
