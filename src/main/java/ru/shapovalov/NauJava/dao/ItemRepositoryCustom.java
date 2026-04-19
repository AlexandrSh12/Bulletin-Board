package ru.shapovalov.NauJava.dao;
import ru.shapovalov.NauJava.entity.Comment;
import ru.shapovalov.NauJava.entity.Item;

import java.util.List;
//Criteria API
public interface ItemRepositoryCustom {
    /**
     * Находит все объявления с ценой в заданном диапазоне
     * @param min минимальная цена
     * @param max максимальная цена
     */
    List<Item> findByPriceBetween(Double min, Double max);

    /**
     * Находит все комментарии по id объявления
     * @param itemId id объявления
     */
    List<Comment> findCommentsByItemId(Long itemId);
}
