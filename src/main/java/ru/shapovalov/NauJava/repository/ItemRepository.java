package ru.shapovalov.NauJava.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.shapovalov.NauJava.entity.Item;

import java.util.List;

@RepositoryRestResource
public interface ItemRepository extends CrudRepository<Item, Long> {

    // Query Lookup — поиск по цене между двумя значениями
    List<Item> findByPriceBetween(Double min, Double max);
}
