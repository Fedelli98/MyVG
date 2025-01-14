package com.myvg.myvg.DAO;

import java.util.List;
import java.util.Optional;

public interface IDAO<T> {
    //CRUD OPERATIONS
    Optional<T> create(T t);
    Optional<T> read(String id);
    boolean update(T t);
    boolean delete(String id);

    //UTILS
    List<T> readAll();
}
