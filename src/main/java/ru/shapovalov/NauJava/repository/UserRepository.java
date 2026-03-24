package ru.shapovalov.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.shapovalov.NauJava.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
}