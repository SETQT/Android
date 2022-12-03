package com.example.androidproject08;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order  implements Serializable {
    private String ownOrder;
    private ArrayList<Myorder> arrayOrder;
    private Integer transportFee; // phí vận chuyển
    private String voucher; // voucher áp dụng cho đơn hàng này
    private Integer state; // trạng thái đơn hàng
    // 1: đợi xác nhận
    private String paymentMethods; // phương thức thanh toán
    private Date createdAt;
    private Integer finalTotalMoney; // tổng tiền của đơn hàng sau khi đã giảm tiền voucher và cộng phí vận chuyển

    public Order() {}

    public Order(String ownOrder, ArrayList<Myorder> arrayOrder, Integer transportFee, String voucher, Integer state, String paymentMethods, Date createdAt, Integer finalTotalMoney) {
        this.ownOrder = ownOrder;
        this.arrayOrder = arrayOrder;
        this.transportFee = transportFee;
        this.voucher = voucher;
        this.state = state;
        this.paymentMethods = paymentMethods;
        this.createdAt = createdAt;
        this.finalTotalMoney = finalTotalMoney;
    }

    public String getOwnOrder() {
        return ownOrder;
    }

    public void setOwnOrder(String ownOrder) {
        this.ownOrder = ownOrder;
    }

    public ArrayList<Myorder> getArrayOrder() {
        return arrayOrder;
    }

    public void setArrayOrder(ArrayList<Myorder> arrayOrder) {
        this.arrayOrder = arrayOrder;
    }

    public void addOrderToArrayOrder(Myorder order) {
        this.arrayOrder.add(order);
    }

    public Integer getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(Integer transportFee) {
        this.transportFee = transportFee;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFinalTotalMoney() {
        return finalTotalMoney;
    }

    public void setFinalTotalMoney(Integer finalTotalMoney) {
        this.finalTotalMoney = finalTotalMoney;
    }
}
