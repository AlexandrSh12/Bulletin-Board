package ru.shapovalov.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shapovalov.NauJava.repository.ItemRepository;

@Controller
@RequestMapping("/custom/items/view")
public class ItemViewController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/list")
    public String itemListView(Model model) {
        Iterable items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "itemList";
    }
}