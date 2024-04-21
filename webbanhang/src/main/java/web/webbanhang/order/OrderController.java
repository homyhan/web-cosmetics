package web.webbanhang.order;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.webbanhang.jpa.OrderJpa;
import web.webbanhang.jpa.StateOrderJpa;
import web.webbanhang.jpa.UserJpa;
import web.webbanhang.stateOrder.StateOrder;
import web.webbanhang.user.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class OrderController {
    private UserJpa userJpa;
    private OrderJpa orderJpa;

    private StateOrderJpa stateOrderJpa;

    public OrderController(UserJpa userJpa, OrderJpa orderJpa, StateOrderJpa stateOrderJpa) {
        this.userJpa = userJpa;
        this.orderJpa = orderJpa;
        this.stateOrderJpa = stateOrderJpa;
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Orders>> getUserOrders(@PathVariable int userId) {
        try {
            Optional<User> optionalUser = userJpa.findById(userId);
            if (!optionalUser.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            User user = optionalUser.get();
            List<Orders> userOrders = orderJpa.findByUser(user);

            return ResponseEntity.ok(userOrders);
        } catch (Exception e) {
            System.err.println("Error getting user's orders: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Orders>> getAllOrders() {
        try {
            List<Orders> allOrders = orderJpa.findAll();
            return ResponseEntity.ok(allOrders);
        } catch (Exception e) {
            System.err.println("Error getting all orders: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/orders/{orderId}/state")
    public ResponseEntity<String> updateOrderStatePut(@PathVariable int orderId, @RequestBody Map<String, Integer> requestBody) {
        try {
            // Lay stateOrderId tu requestBody
            int stateOrderId = requestBody.get("stateOrderId");
            Optional<Orders> optionalOrder = orderJpa.findById(orderId);
            //Check xem don hang co ton tai hay khong
            if (!optionalOrder.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // Lay don hang
            Orders order = optionalOrder.get();
            //Tim id cua trang thai moi co ton tai trong csdl hay khong
            StateOrder newStateOrder = stateOrderJpa.findById(stateOrderId).orElse(null);
            if (newStateOrder == null) {
                return ResponseEntity.badRequest().body("Invalid state order");
            }

            //Cap nhat lai trang thai moi
            order.setStateOrder(newStateOrder);
            //Luu don hang
            orderJpa.save(order);

            return ResponseEntity.ok("Order state updated successfully");
        } catch (Exception e) {
            System.err.println("Error updating order state: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating order state");
        }
    }

}

