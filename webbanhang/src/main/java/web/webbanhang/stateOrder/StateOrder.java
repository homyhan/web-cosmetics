package web.webbanhang.stateOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class StateOrder {
    @Id
    @GeneratedValue
    private int id;
    private String state_name;
    private int state;

    public StateOrder(int id, String state_name, int state) {
        this.id = id;
        this.state_name = state_name;
        this.state = state;
    }

    public StateOrder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "StateOrder{" +
                "id=" + id +
                ", state_name='" + state_name + '\'' +
                ", state=" + state +
                '}';
    }
}
