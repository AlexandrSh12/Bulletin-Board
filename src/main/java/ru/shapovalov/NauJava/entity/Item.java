package ru.shapovalov.NauJava.entity;

import java.util.Objects;

public class Item {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String category;
    private ItemStatus status;
    private String author;

    public Item(Long id, String title, String description, Double price, String category, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.author = author;
        this.status = ItemStatus.ACTIVE; // всегда ACTIVE при создании
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        {
            return "[" + id + "] " + title + " | " + price + " руб. | " +
                    category + " | " + status + " | Автор: " + author;
        }
    }
    //добавил equals и hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
