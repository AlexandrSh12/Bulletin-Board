package ru.shapovalov.NauJava.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.shapovalov.NauJava.entity.Comment;
import ru.shapovalov.NauJava.entity.Item;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {

    // Query Lookup — поиск по цене между двумя значениями
    List<Item> findByPriceBetween(Double min, Double max);
}
