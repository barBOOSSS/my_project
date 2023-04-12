package by.pleshkov.service.service;

import java.util.List;

public interface IService<T> {

    T create(T t);

    T read(long t);

    boolean update(T t);

    boolean delete(long t);

    List<T> readAll();
}
