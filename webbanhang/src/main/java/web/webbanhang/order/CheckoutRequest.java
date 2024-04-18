package web.webbanhang.order;

import web.webbanhang.cart.Cart;

import java.util.Date;
import java.util.List;

public class CheckoutRequest {
    private int userId;
    private int stateOrderId;
    private int quantity;
    private Date orderDate;
    private String address;
    private String phone;
    private double total;
    private int statusCheckout;

    public CheckoutRequest(int userId, int stateOrderId, int quantity, Date orderDate, String address, String phone, double total, int statusCheckout) {
        this.userId = userId;
        this.stateOrderId = stateOrderId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.address = address;
        this.phone = phone;
        this.total = total;
        this.statusCheckout = statusCheckout;
    }

    public CheckoutRequest() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStateOrderId() {
        return stateOrderId;
    }

    public void setStateOrderId(int stateOrderId) {
        this.stateOrderId = stateOrderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatusCheckout() {
        return statusCheckout;
    }

    public void setStatusCheckout(int statusCheckout) {
        this.statusCheckout = statusCheckout;
    }
}
