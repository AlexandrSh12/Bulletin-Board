package ru.shapovalov.NauJava;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shapovalov.NauJava.dao.ItemRepositoryCustom;
import ru.shapovalov.NauJava.entity.Comment;
import ru.shapovalov.NauJava.entity.Item;
import ru.shapovalov.NauJava.entity.User;
import ru.shapovalov.NauJava.repository.CommentRepository;
import ru.shapovalov.NauJava.repository.ItemRepository;
import ru.shapovalov.NauJava.repository.UserRepository;
import ru.shapovalov.NauJava.service.ItemTransactionService;

import java.util.List;

@SpringBootTest
@Transactional
public class ItemTest {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ItemRepositoryCustom itemRepositoryCustom;
    private final ItemTransactionService itemTransactionService;

    @Autowired
    ItemTest(ItemRepository itemRepository,
             CommentRepository commentRepository,
             UserRepository userRepository,
             ItemRepositoryCustom itemRepositoryCustom,
             ItemTransactionService itemTransactionService) {
        this.itemRepository = itemRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.itemRepositoryCustom = itemRepositoryCustom;
        this.itemTransactionService = itemTransactionService;
    }
    // Тест поиска объявлений по диапозону цен Query Lookup
    @Test
    void testFindByPriceBetween() {
        Item item = new Item();
        item.setTitle("Гараж");
        item.setPrice(500.0);
        itemRepository.save(item);
        List<Item> items = itemRepository.findByPriceBetween(100.0, 1000.0);
        // проверяем, что список не пустой
        Assertions.assertFalse(items.isEmpty());
        // проверяем, что среди найденных объявлений есть наше
        Assertions.assertTrue(items.stream().anyMatch(i -> i.getPrice() == 500.0));
    }

    // Тест поиска объявлений по диапозону цен Criteria API
    @Test
    void testFindByPriceBetweenCriteria() {
        Item item = new Item();
        item.setTitle("Второй гараж");
        item.setPrice(750.0);
        itemRepository.save(item);

        List<Item> items = itemRepositoryCustom.findByPriceBetween(500.0, 1000.0);

        Assertions.assertFalse(items.isEmpty());
        Assertions.assertTrue(items.stream().anyMatch(i -> i.getPrice() == 750.0));
    }

    // Тест поиска комментариев по id объявления JPQL
    @Test
    void testFindCommentsByItemId() {
        Item item = new Item();
        item.setTitle("Товар с комментарием");
        item.setPrice(100.0);
        itemRepository.save(item);

        Comment comment = new Comment();
        comment.setText("Тестовый комментарий");
        comment.setItem(item); // связываем коммент с объявлением
        commentRepository.save(comment);
        // получаем список комментариев по id объявления
        List<Comment> comments = commentRepository.findCommentsByItemId(item.getId());

        Assertions.assertFalse(comments.isEmpty());
        Assertions.assertEquals("Тестовый комментарий", comments.getFirst().getText());
    }

    // Тест поиска комментариев по id объявления  Criteria API
    @Test
    void testFindCommentsByItemIdCriteria() {
        Item item = new Item();
        item.setTitle("Второй товар с комментарием");
        item.setPrice(200.0);
        itemRepository.save(item);

        Comment comment = new Comment();
        comment.setText("Второй комментарий");
        comment.setItem(item);
        commentRepository.save(comment);

        List<Comment> comments = itemRepositoryCustom.findCommentsByItemId(item.getId());

        Assertions.assertFalse(comments.isEmpty());
        Assertions.assertEquals("Второй комментарий", comments.getFirst().getText());
    }

    // Позитивный тест удаления объявления с комментариями
    @Test
    void testDeleteItemWithComments() {
        Item item = new Item();
        item.setTitle("Удаляемый товар");
        item.setPrice(300.0);
        itemRepository.save(item);

        Comment comment = new Comment();
        comment.setText("Удаляемый комментарий");
        comment.setItem(item);
        commentRepository.save(comment);

        itemTransactionService.deleteItemWithComments(item.getId());
        // проверяем, что товар не найден, то есть удалён
        Assertions.assertFalse(itemRepository.findById(item.getId()).isPresent());
        // получаем список комментариев к удалённому товару
        List<Comment> comments = itemRepositoryCustom.findCommentsByItemId(item.getId());
        // проверяем, что этот список пустой
        Assertions.assertTrue(comments.isEmpty());
    }

    //Негативный тест — откат транзакции при удалении несуществующего объявления
    // удаляем -1 объявление
    @Test
    void testDeleteItemWithCommentsRollback() {
        try {
            itemTransactionService.deleteItemWithComments(-1L);
            Assertions.fail("Должно было выброситься исключение");
        } catch (IllegalArgumentException e) {
            // всё правильно, исключение выброшено
        }
    }

}
