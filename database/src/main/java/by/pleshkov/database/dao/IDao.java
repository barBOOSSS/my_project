package by.pleshkov.database.dao;

import java.util.List;

public interface IDao<T> {

    T create(T t);

    T read(long t);

    boolean update(T t);

    boolean delete(long id);

    List<T> readAll();

}
