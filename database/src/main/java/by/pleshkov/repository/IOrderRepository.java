package by.pleshkov.repository;

import java.util.List;

public interface IOrderRepository<T> {

    boolean create(T t);
    T read(String t);
    boolean update(T t);
    boolean delete(T t);
    List<T> readAll();

}
