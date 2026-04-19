package ru.shapovalov.NauJava.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Item> items;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
}
