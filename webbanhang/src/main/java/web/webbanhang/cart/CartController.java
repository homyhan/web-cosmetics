package web.webbanhang.cart;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.webbanhang.jpa.*;
import web.webbanhang.order.CheckoutRequest;
import web.webbanhang.order.OrderDetail;
import web.webbanhang.order.Orders;
import web.webbanhang.product.Product;
import web.webbanhang.stateOrder.StateOrder;
import web.webbanhang.user.User;

import java.util.*;

@RestController
public class CartController {
    private CartJpa cartRepository;
    private UserJpa userRepository;
    private ProductJpa productRepository;

    private OrderJpa orderRepository;
    private StateOrderJpa stateOrderRepository;

    public CartController(CartJpa cartRepository, UserJpa userRepository, ProductJpa productRepository, OrderJpa orderRepository, StateOrderJpa stateOrderRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.stateOrderRepository = stateOrderRepository;
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

//    @PostMapping("/carts/checkout")
//    public ResponseEntity<String> checkout(@RequestBody CheckoutRequest checkoutRequest) {
//        try {
//            int userId = checkoutRequest.getUserId();
//            User user = userRepository.findById(userId).orElse(null);
//
//            if (user == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            List<Cart> carts = cartRepository.findByUserId(userId);
//            if (carts.isEmpty()) {
//                return ResponseEntity.badRequest().body("The user's cart is empty");
//            }
//
//            StateOrder stateOrder = stateOrderRepository.findById(checkoutRequest.getStateOrderId()).orElse(null);
//            if (stateOrder == null) {
//                return ResponseEntity.badRequest().body("Invalid state order");
//            }
//
//            // Create an order
//            Orders order = new Orders();
//            order.setUser(user);
//            order.setStateOrder(stateOrder);
//            order.setOrderDate(new Date());
//            order.setAddress(checkoutRequest.getAddress());
//            order.setPhoneNumber(checkoutRequest.getPhone());
//
//            double total = 0;
//            List<OrderDetail> orderDetails = new ArrayList<>();
//            for (Cart cart : carts) {
//                OrderDetail orderDetail = new OrderDetail();
//                orderDetail.setOrders(order);
//                orderDetail.setProduct(cart.getProduct());
//                orderDetail.setQuantity(cart.getQuantity());
//                orderDetail.setPrice(cart.getProduct().getPrice()); // Assuming product price
//                total += cart.getProduct().getPrice() * cart.getQuantity();
//                orderDetails.add(orderDetail);
//            }
//            order.setOrderDetails(orderDetails);
//            order.setTotalPrice(total);
//            order.setStatusCheckout(checkoutRequest.getStatusCheckout());
//
//            // Save order
//            orderRepository.save(order);
//
//            // Clear user's cart
//            cartRepository.deleteAll(carts);
//
//            return ResponseEntity.ok("Checkout successfully");
//        } catch (Exception e) {
//            System.err.println("Error during checkout: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during checkout");
//        }
//    }

    @PostMapping("/carts/checkout")
    public ResponseEntity<String> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        try {
            int userId = checkoutRequest.getUserId();
            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            List<Cart> carts = cartRepository.findByUserId(userId);
            if (carts.isEmpty()) {
                return ResponseEntity.badRequest().body("The user's cart is empty");
            }

            StateOrder stateOrder;
            if (checkoutRequest.getStatusCheckout() == 1) {
                // statusCheckout 1 => stateOrderId 2
                stateOrder = stateOrderRepository.findById(2).orElse(null);
            } else {
                stateOrder = stateOrderRepository.findById(1).orElse(null);
            }

            if (stateOrder == null) {
                return ResponseEntity.badRequest().body("Invalid state order");
            }

            // Tao don hang
            Orders order = new Orders();
            order.setUser(user);
            order.setStateOrder(stateOrder);
            order.setOrderDate(new Date());
            order.setAddress(checkoutRequest.getAddress());
            order.setPhoneNumber(checkoutRequest.getPhone());

            double total = 0;
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (Cart cart : carts) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrders(order);
                orderDetail.setProduct(cart.getProduct());
                orderDetail.setQuantity(cart.getQuantity());
                orderDetail.setPrice(cart.getProduct().getPrice()); // Assuming product price
                total += cart.getProduct().getPrice() * cart.getQuantity();
                orderDetails.add(orderDetail);
            }
            order.setOrderDetails(orderDetails);
            order.setTotalPrice(total);
            order.setStatusCheckout(checkoutRequest.getStatusCheckout());

            // Luu don hang
            orderRepository.save(order);

            cartRepository.deleteAll(carts);

            return ResponseEntity.ok("Checkout successfully");
        } catch (Exception e) {
            System.err.println("Error during checkout: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during checkout");
        }
    }


}