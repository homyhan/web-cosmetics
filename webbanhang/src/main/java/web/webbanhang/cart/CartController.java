package web.webbanhang.cart;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.webbanhang.jpa.CartJpa;
import web.webbanhang.jpa.ProductJpa;
import web.webbanhang.jpa.UserJpa;
import web.webbanhang.product.Product;
import web.webbanhang.user.User;

import java.util.*;

@RestController
public class CartController {
    private CartJpa cartRepository;
    private UserJpa userRepository;
    private ProductJpa productRepository;

    public CartController(CartJpa cartRepository, UserJpa userRepository, ProductJpa productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/carts")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest cartRequest) {
        try {
            int userId = cartRequest.getUserId();
            int productId = cartRequest.getProductId();
            int quantity = cartRequest.getQuantity();

            Optional<User> optionalUser = userRepository.findById(userId);
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (!optionalUser.isPresent() || !optionalProduct.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            User user = optionalUser.get();
            Product product = optionalProduct.get();

            Optional<Cart> optionalCart = cartRepository.findByUserAndProduct(user, product);

            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();
                cart.setQuantity(cart.getQuantity() + 1);
                cartRepository.save(cart);
            } else {
                Cart newCart = new Cart(user, product, 1, new Date());
                cartRepository.save(newCart);
            }

            product.setQuantity(product.getQuantity() - 1);
            productRepository.save(product);

            return ResponseEntity.ok("Added to cart successfully");
        } catch (Exception e) {
            System.err.println("Error adding to cart: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding to cart");
        }
    }

    @Transactional
    @DeleteMapping("/carts")
    public ResponseEntity<String> removeFromCart(@RequestParam int userId, @RequestParam int productId) {
        try {
            cartRepository.deleteByUserIdAndProductId(userId, productId);
            return ResponseEntity.ok("Removed from cart successfully");
        } catch (Exception e) {
            System.err.println("Error removing from cart: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing from cart");
        }
    }

    @GetMapping("/carts/{userId}")
    public ResponseEntity<List<Cart>> getUserCarts(@PathVariable int userId) {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            User user = optionalUser.get();
            List<Cart> userCarts = cartRepository.findByUser(user);

            return ResponseEntity.ok(userCarts);
        } catch (Exception e) {
            System.err.println("Error getting user's carts: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
