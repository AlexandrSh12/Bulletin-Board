package ru.shapovalov.NauJava.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.shapovalov.NauJava.entity.Comment;
import ru.shapovalov.NauJava.repository.CommentRepository;
import ru.shapovalov.NauJava.repository.ItemRepository;

import java.util.List;
@Service
public class ItemTransactionServiceImpl implements ItemTransactionService {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public ItemTransactionServiceImpl(ItemRepository itemRepository,
                                      CommentRepository commentRepository,
                                      PlatformTransactionManager transactionManager) {
        this.itemRepository = itemRepository;
        this.commentRepository = commentRepository;
        this.transactionManager = transactionManager;
    }
    // удаление поста вместе с комментариями
    @Override
    public void deleteItemWithComments(Long itemId) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // проверить, что объявление существует
            if (!itemRepository.existsById(itemId)) {
                throw new IllegalArgumentException("Объявление с id " + itemId + " не найдено");
            }
            // удалить все комментарии объявления
            List<Comment> comments = (List<Comment>) commentRepository.findAll();
            for (Comment comment : comments) {
                if (comment.getItem().getId().equals(itemId)) {
                    commentRepository.delete(comment);
                }
            }
            // удалить само объявление
            itemRepository.deleteById(itemId);
            transactionManager.commit(status);
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }
    }
}
