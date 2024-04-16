package web.webbanhang.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import web.webbanhang.product.Product;
import web.webbanhang.user.User;

import java.util.Date;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne

    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Temporal(TemporalType.TIMESTAMP) // Định dạng cho ngày đặt hàng
    private Date orderDate;

    public Cart() {
    }

    public Cart(User user, Product product, int quantity, Date orderDate) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.orderDate = orderDate;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", product=" + product.getId() +
                ", quantity=" + quantity +
                ", orderDate=" + orderDate +
                '}';
    }
}
