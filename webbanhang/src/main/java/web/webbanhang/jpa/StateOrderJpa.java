package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import web.webbanhang.stateOrder.StateOrder;

public interface StateOrderJpa extends JpaRepository<StateOrder, Integer> {
}
