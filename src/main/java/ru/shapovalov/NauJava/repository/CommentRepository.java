package ru.shapovalov.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.shapovalov.NauJava.entity.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    // JPQL — поиск комментариев через Item
    @Query("SELECT c FROM Comment c WHERE c.item.id = :itemId")
    List<Comment> findCommentsByItemId(@Param("itemId") Long itemId);
}