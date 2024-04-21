package web.webbanhang.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import web.webbanhang.stateOrder.StateOrder;
import web.webbanhang.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @Temporal(TemporalType.TIMESTAMP) // Định dạng cho ngày đặt hàng
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "state_order_id")
    private StateOrder stateOrder;

    private String address;
    private String phoneNumber;
    private double totalPrice;
    private int statusCheckout;

    public Orders() {
    }

    public Orders(User user, List<OrderDetail> orderDetails, Date orderDate, StateOrder stateOrder, String address, String phoneNumber, double totalPrice, int statusCheckout) {
        this.user = user;
        this.orderDetails = orderDetails != null ? orderDetails : new ArrayList<>();
        this.orderDate = orderDate;
        this.stateOrder = stateOrder;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.statusCheckout = statusCheckout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public StateOrder getStateOrder() {
        return stateOrder;
    }

    public void setStateOrder(StateOrder stateOrder) {
        this.stateOrder = stateOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getStatusCheckout() {
        return statusCheckout;
    }

    public void setStatusCheckout(int statusCheckout) {
        this.statusCheckout = statusCheckout;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", user=" + user +
                ", orderDetails=" + orderDetails +
                ", orderDate=" + orderDate +
                ", stateOrder=" + stateOrder +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", totalPrice=" + totalPrice +
                ", statusCheckout=" + statusCheckout +
                '}';
    }
}

