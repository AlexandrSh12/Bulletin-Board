package ru.shapovalov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.shapovalov.NauJava.entity.Category;

@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
