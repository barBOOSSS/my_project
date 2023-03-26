package by.pleshkov.repository;

import java.util.List;

public interface INumberRepository<T>{

//    boolean create(T t);
    T read(int t);
    boolean update(T t);
//    boolean delete(T t);
    List<T> readAll();
}
