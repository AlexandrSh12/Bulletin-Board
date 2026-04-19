package ru.shapovalov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shapovalov.NauJava.entity.Comment;
import ru.shapovalov.NauJava.entity.Item;
import ru.shapovalov.NauJava.repository.CommentRepository;
import ru.shapovalov.NauJava.repository.ItemRepository;

import java.util.List;

@RestController
@RequestMapping("/custom/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/findByPrice")
    public List<Item> findByPrice(@RequestParam Double min, @RequestParam Double max) {
        return itemRepository.findByPriceBetween(min, max);
    }

    @GetMapping("/findCommentsByItemId")
    public List<Comment> findCommentsByItemId(@RequestParam Long itemId) {
        return commentRepository.findCommentsByItemId(itemId);
    }
}