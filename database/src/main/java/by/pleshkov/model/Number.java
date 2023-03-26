package by.pleshkov.model;

import by.pleshkov.constant.ClassRoom;
import by.pleshkov.constant.Status;

import java.util.List;
import java.util.Objects;

public class Number {
    private int numberRoom;
    private int places;
    private ClassRoom classRoom;
    private Status status;
    private String user;
    List<Order> orders;

    public Number() {
    }

    public Number(int numberRoom, int places, ClassRoom classRoom, Status status, String user) {
        this.numberRoom = numberRoom;
        this.places = places;
        this.classRoom = classRoom;
        this.status = status;
        this.user = user;
    }

    public Number(int numberRoom, int places, ClassRoom classRoom, Status status, String user, List<Order> orders) {
        this.numberRoom = numberRoom;
        this.places = places;
        this.classRoom = classRoom;
        this.status = status;
        this.user = user;
        this.orders = orders;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Number number = (Number) o;

        if (numberRoom != number.numberRoom) return false;
        if (places != number.places) return false;
        if (classRoom != number.classRoom) return false;
        if (status != number.status) return false;
        if (!Objects.equals(user, number.user)) return false;
        return Objects.equals(orders, number.orders);
    }

    @Override
    public int hashCode() {
        int result = numberRoom;
        result = 31 * result + places;
        result = 31 * result + (classRoom != null ? classRoom.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Number{" +
                "numberRoom=" + numberRoom +
                ", places=" + places +
                ", classRoom=" + classRoom +
                ", status=" + status +
                ", user='" + user + '\'' +
                ", orders=" + orders +
                '}';
    }
}
