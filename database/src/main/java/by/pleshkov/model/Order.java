package by.pleshkov.model;

import by.pleshkov.constant.ClassRoom;
import by.pleshkov.constant.Solution;
import by.pleshkov.constant.StatusOrder;

import java.util.Objects;

public class Order {
    private long id;
    private String user;
    private int places;
    private ClassRoom classRoom;
    private StatusOrder statusOrder;
    private Solution solution;

    public Order() {
    }

    public Order(String user, int places, ClassRoom classRoom, StatusOrder statusOrder) {
        this.user = user;
        this.places = places;
        this.classRoom = classRoom;
        this.statusOrder = statusOrder;
    }

    public Order(long id, String user, int places, ClassRoom classRoom, StatusOrder statusOrder, Solution solution) {
        this.id = id;
        this.user = user;
        this.places = places;
        this.classRoom = classRoom;
        this.statusOrder = statusOrder;
        this.solution = solution;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (places != order.places) return false;
        if (!Objects.equals(user, order.user)) return false;
        if (classRoom != order.classRoom) return false;
        if (statusOrder != order.statusOrder) return false;
        return solution == order.solution;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + places;
        result = 31 * result + (classRoom != null ? classRoom.hashCode() : 0);
        result = 31 * result + (statusOrder != null ? statusOrder.hashCode() : 0);
        result = 31 * result + (solution != null ? solution.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", places=" + places +
                ", classRoom=" + classRoom +
                ", statusOrder=" + statusOrder +
                ", solution=" + solution +
                '}';
    }
}
