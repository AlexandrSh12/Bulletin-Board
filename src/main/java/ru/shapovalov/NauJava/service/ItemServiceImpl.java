package ru.shapovalov.NauJava.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shapovalov.NauJava.AppConfig;
import ru.shapovalov.NauJava.entity.Item;
import ru.shapovalov.NauJava.entity.ItemStatus;
import ru.shapovalov.NauJava.repository.ItemRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/*
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final AppConfig appConfig;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, AppConfig appConfig) {
        this.itemRepository = itemRepository;
        this.appConfig = appConfig;
    }
    @PostConstruct
    public void printAppInfo() {
        System.out.println("App " + appConfig.getAppName() + " | version " + appConfig.getAppVersion());
    }
    @Override
    public void createItem(Long id, String title, String description, Double price, String category, String author) {
        Item item = new Item(id, title, description, price, category, author);
        itemRepository.create(item);
    }

    @Override
    public void editItem(Long id, String newDescription, Double newPrice) {
        Item item = itemRepository.read(id);
        if (item != null){
            item.setDescription(newDescription);
            item.setPrice(newPrice);
            itemRepository.update(item);
        }
    }

    @Override
    public void changeStatus(Long id, ItemStatus newStatus) {
        Item item = itemRepository.read(id);
        if (item !=null){
            item.setStatus(newStatus);
            itemRepository.update(item);
        }
    }

    @Override
    public List<Item> sortByPrice() {
        List<Item> allItems = itemRepository.findAll();
        return allItems.stream()
                .sorted(Comparator.comparingDouble(Item::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> filterByCategory(String category) {
        List<Item> allItems = itemRepository.findAll();
        return allItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }


}*/
