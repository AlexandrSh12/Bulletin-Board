package ru.shapovalov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.shapovalov.NauJava.entity.User;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {
}