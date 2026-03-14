package ru.shapovalov.NauJava.service;

import ru.shapovalov.NauJava.entity.Item;
import ru.shapovalov.NauJava.entity.ItemStatus;

import java.util.List;
public interface ItemService {
    void createItem(Long id, String title, String description,
                    Double price, String category, String author);
    void editItem(Long id, String newDescription, Double newPrice);
    void changeStatus(Long id, ItemStatus newStatus);
    List<Item> sortByPrice();
    List<Item> filterByCategory(String category);
}
