package com.spring.taskone.demo.repository;

import java.util.Optional;

public interface StorageRepository<T, ID> {
    Optional<T> findById(ID primaryKey);

    boolean existsById(ID primaryKey);

    Iterable<T> findAll();

    long count();

    void delete(T entity);

    <S extends T> S save(S entity);
}
