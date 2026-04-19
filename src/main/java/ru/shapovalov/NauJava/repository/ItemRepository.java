package ru.shapovalov.NauJava.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.shapovalov.NauJava.entity.Item;

import java.util.List;
@Component
public class ItemRepository implements CrudRepository<Item, Long> {
    private final List<Item> itemContainer;

    @Autowired
    public ItemRepository(List<Item> itemContainer) {
        this.itemContainer = itemContainer;
    }

    @Override
    public void create(Item item) {
        itemContainer.add(item);
    }

    @Override
    public Item read(Long id) {
        return itemContainer.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Item updatedItem) {
        for (int i = 0; i < itemContainer.size(); i++) {
            if (itemContainer.get(i).getId().equals(updatedItem.getId())) {
                itemContainer.set(i, updatedItem);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        itemContainer.removeIf(item -> item.getId().equals(id));
    }
    @Override
    public List<Item> findAll() {
        return itemContainer;
    }
}
