package ru.shapovalov.NauJava;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.shapovalov.NauJava.entity.Item;
import ru.shapovalov.NauJava.entity.ItemStatus;
import ru.shapovalov.NauJava.service.ItemService;

import java.util.List;

@Component
public class CommandProcessor {

    private final ItemService itemService;

    @Autowired
    public CommandProcessor(ItemService itemService) {
        this.itemService = itemService;
    }

    public void processCommand(String input) {
        try {
            String[] cmd = input.trim().split("\\s+");
            switch (cmd[0].toLowerCase()) {
                case "create" -> {
                    // create <title> <description> <price> <category> <author>
                    requireArgs(cmd, 6);
                    double price = parseDouble(cmd[3]);
                    itemService.createItem(cmd[1], cmd[2], price, cmd[4], cmd[5]);
                    System.out.println("Объявление успешно создано.");
                }
                case "edit" -> {
                    // edit <id> <newDescription> <newPrice>
                    requireArgs(cmd, 4);
                    long id = parseLong(cmd[1]);
                    double price = parseDouble(cmd[3]);
                    itemService.editItem(id, cmd[2], price);
                    System.out.println("Объявление успешно обновлено.");
                }
                case "status" -> {
                    // status <id> <ACTIVE|ARCHIVED|SOLD>
                    requireArgs(cmd, 3);
                    long id = parseLong(cmd[1]);
                    ItemStatus status = parseStatus(cmd[2]);
                    itemService.changeStatus(id, status);
                    System.out.println("Статус успешно изменён.");
                }
                case "sort"   -> printItems(itemService.sortByPrice());
                case "filter" -> {
                    requireArgs(cmd, 2);
                    printItems(itemService.filterByCategory(cmd[1]));
                }
                case "list"   -> printItems(itemService.listAll());
                case "help"   -> printHelp();
                default -> System.out.println("Неизвестная команда. Введите 'help' для справки.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка: " + e.getMessage());
        }
    }
    private void requireArgs(String[] cmd, int minCount) {
        if (cmd.length < minCount) {
            throw new IllegalArgumentException(
                    "Недостаточно аргументов для команды '" + cmd[0] +
                            "'. Введите 'help' для справки."
            );
        }
    }

    private long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID должен быть целым числом, получено: '" + value + "'.");
        }
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Цена должна быть числом, получено: '" + value + "'.");
        }
    }

    private ItemStatus parseStatus(String value) {
        try {
            return ItemStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Неизвестный статус '" + value + "'. Допустимые: ACTIVE, ARCHIVED, SOLD."
            );
        }
    }
    private void printItems(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("Объявления не найдены.");
        } else {
            items.forEach(System.out::println);
        }
    }
    private void printHelp() {
        System.out.println("""
            Доступные команды:
            
              create <title> <description> <price> <category> <author>
                  Создать новое объявление. ID генерируется автоматически.
                  Пример: create Велосипед Горный_б/у 5000.0 Транспорт Иван
            
              edit <id> <newDescription> <newPrice>
                  Изменить описание и цену объявления.
                  Пример: edit 1 Новое_описание 4500.0
            
              status <id> <ACTIVE|ARCHIVED|SOLD>
                  Изменить статус объявления.
                  Пример: status 1 SOLD
            
              list
                  Показать все объявления.
            
              sort
                  Показать все объявления, отсортированные по цене.
            
              filter <category>
                  Показать объявления по категории.
                  Пример: filter Транспорт
            
              help
                  Показать эту справку.
            
              exit
                  Выйти из приложения.
            """);
    }
}