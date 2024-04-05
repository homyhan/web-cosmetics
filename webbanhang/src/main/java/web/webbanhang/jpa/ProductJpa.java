package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import web.webbanhang.product.Product;

public interface ProductJpa extends JpaRepository<Product, Integer>{

}
