package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import web.webbanhang.product.Product;
import web.webbanhang.user.User;

import java.util.List;

public interface ProductJpa extends JpaRepository<Product, Integer>{
    List<Product> findByNameProdContainingIgnoreCase(String keyword);
}
