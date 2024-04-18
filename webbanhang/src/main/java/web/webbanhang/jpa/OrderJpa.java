package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import web.webbanhang.order.Orders;

public interface OrderJpa extends JpaRepository<Orders, Integer> {
}
