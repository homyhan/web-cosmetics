package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import web.webbanhang.order.Orders;
import web.webbanhang.user.User;

import java.util.List;

public interface OrderJpa extends JpaRepository<Orders, Integer> {
    List<Orders> findByUser(User user);
}
