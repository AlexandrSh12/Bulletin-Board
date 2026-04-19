package ru.shapovalov.NauJava;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shapovalov.NauJava.entity.Item;
import ru.shapovalov.NauJava.entity.ItemStatus;
import ru.shapovalov.NauJava.service.ItemService;

import java.util.List;
//тут просто закомментировал, чтобы не создавался бин и не работал консольный ввод
//@Component
public class CommandProcessor {

    private final ItemService itemService;

    //@Autowired
    public CommandProcessor(ItemService itemService) {
        this.itemService = itemService;
    }

    public void processCommand(String input) {
        String[] cmd = input.split(" ");
        switch (cmd[0]) {
            case "create" -> {
                // create <id> <title> <description> <price> <category> <author>
                itemService.createItem(Long.valueOf(cmd[1]), cmd[2], cmd[3],
                        Double.valueOf(cmd[4]), cmd[5], cmd[6]);
                System.out.println("Объявление успешно создано...");
            }
            case "edit" -> {
                // edit <id> <newDescription> <newPrice>
                itemService.editItem(Long.valueOf(cmd[1]), cmd[2], Double.valueOf(cmd[3]));
                System.out.println("Объявление успешно обновлено...");
            }
            case "status" -> {
                // status <id> <ACTIVE|ARCHIVED|SOLD>
                itemService.changeStatus(Long.valueOf(cmd[1]), ItemStatus.valueOf(cmd[2]));
                System.out.println("Статус успешно изменён...");
            }
            case "sort" -> {
                List<Item> items = itemService.sortByPrice();
                items.forEach(System.out::println);
            }
            case "filter" -> {
                // filter <category>
                List<Item> items = itemService.filterByCategory(cmd[1]);
                items.forEach(System.out::println);
            }
            default -> System.out.println("Введена неизвестная команда...");
        }
    }
}