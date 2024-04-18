package web.webbanhang.stateOrder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.webbanhang.jpa.StateOrderJpa;
import web.webbanhang.role.Role;

import java.util.List;

@RestController
public class StateOrderController {
    private StateOrderJpa stateOrderJpa;

    public StateOrderController(StateOrderJpa stateOrderJpa) {
        this.stateOrderJpa = stateOrderJpa;
    }

    @PostMapping("/stateOrder")
    public ResponseEntity<Role> createStateOrder(@RequestBody StateOrder stateOrder) {
        stateOrderJpa.save(stateOrder);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stateOrders")
    public List<StateOrder> getAll(){
        return stateOrderJpa.findAll();
    }
    @GetMapping("/stateOrders/{id}")
    public StateOrder getStateOrderById(@PathVariable int id){
        StateOrder stateOrder = stateOrderJpa.findById(id).get();
        return stateOrder;
    }
}
