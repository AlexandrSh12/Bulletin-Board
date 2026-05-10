package ru.shapovalov.NauJava;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shapovalov.NauJava.entity.Role;
import ru.shapovalov.NauJava.entity.User;
import ru.shapovalov.NauJava.repository.UserRepository;

@TestConfiguration
public class SeleniumTestDataConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createTestAdmin() {
        if (userRepository.findByUsername("selenium_admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("selenium_admin");
            admin.setPassword(passwordEncoder.encode("selenium_pass"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }
}