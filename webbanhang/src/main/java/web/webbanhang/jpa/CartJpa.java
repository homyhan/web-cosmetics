package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import web.webbanhang.cart.Cart;
import web.webbanhang.product.Product;
import web.webbanhang.role.Role;
import web.webbanhang.user.User;

import java.util.List;
import java.util.Optional;

public interface CartJpa extends JpaRepository<Cart, Integer>{
    Optional<Cart> findByUserAndProduct(User user, Product product);
    void deleteByUserIdAndProductId(int userId, int productId);

    List<Cart> findByUser(User user);

    List<Cart> findByUserId(int userId);
}
