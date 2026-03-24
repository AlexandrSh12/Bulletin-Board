package ru.shapovalov.NauJava.repository;


import org.springframework.data.repository.CrudRepository;
import ru.shapovalov.NauJava.entity.Favorite;

public interface FavoriteRepository extends CrudRepository<Favorite, Long> {

}