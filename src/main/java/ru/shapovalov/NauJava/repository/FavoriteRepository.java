package ru.shapovalov.NauJava.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.shapovalov.NauJava.entity.Favorite;

@RepositoryRestResource
public interface FavoriteRepository extends CrudRepository<Favorite, Long> {

}