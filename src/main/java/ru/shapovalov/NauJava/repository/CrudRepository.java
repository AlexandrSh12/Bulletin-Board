package ru.shapovalov.NauJava.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CrudRepository<T, ID> {
    void create(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
    List<T> findAll();
}
