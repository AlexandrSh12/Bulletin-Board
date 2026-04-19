package ru.shapovalov.NauJava.repository;


import org.springframework.data.repository.CrudRepository;
import ru.shapovalov.NauJava.entity.Item;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {

    // Query Lookup — поиск по цене между двумя значениями
    List<Item> findByPriceBetween(Double min, Double max);
}
