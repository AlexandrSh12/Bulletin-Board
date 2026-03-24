package ru.shapovalov.NauJava.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.shapovalov.NauJava.entity.Comment;
import ru.shapovalov.NauJava.entity.Item;
import java.util.List;
//Criteria API
@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public ItemRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Item> findByPriceBetween(Double min, Double max) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root itemRoot = criteriaQuery.from(Item.class);
        Predicate pricePredicate = criteriaBuilder.between(itemRoot.get("price"), min, max);
        criteriaQuery.select(itemRoot).where(pricePredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Comment> findCommentsByItemId(Long itemId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root commentRoot = criteriaQuery.from(Comment.class);
        Join itemJoin = commentRoot.join("item", JoinType.INNER);
        Predicate itemPredicate = criteriaBuilder.equal(itemJoin.get("id"), itemId);
        criteriaQuery.select(commentRoot).where(itemPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
