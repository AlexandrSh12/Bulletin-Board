package ru.shapovalov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.shapovalov.NauJava.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
