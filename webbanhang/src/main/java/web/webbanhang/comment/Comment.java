package web.webbanhang.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import web.webbanhang.product.Product;
import web.webbanhang.user.User;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne

    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String contentComment;

    private int quantityStart;

    public Comment( User user, Product product, String contentComment, int quantityStart) {
        this.user = user;
        this.product = product;
        this.contentComment = contentComment;
        this.quantityStart = quantityStart;
    }

    public Comment() {

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

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    public int getQuantityStart() {
        return quantityStart;
    }

    public void setQuantityStart(int quantityStart) {
        this.quantityStart = quantityStart;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", product=" + product +
                ", contentComment='" + contentComment + '\'' +
                ", quantityStart=" + quantityStart +
                '}';
    }
}
