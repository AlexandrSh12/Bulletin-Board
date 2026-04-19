package ru.shapovalov.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shapovalov.NauJava.entity.Role;
import ru.shapovalov.NauJava.entity.User;
import ru.shapovalov.NauJava.repository.UserRepository;
import ru.shapovalov.NauJava.service.UserServiceImpl;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    // Позитивный тест - успешное добавление пользователя
    @Test
    void testAddUser_success() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        userService.addUser(user);

        Mockito.verify(userRepository).save(user);
        Assertions.assertEquals("encodedPassword", user.getPassword());
        Assertions.assertEquals(Role.USER, user.getRole());
    }

    // Негативный тест - пользователь уже существует
    @Test
    void testAddUser_userAlreadyExists() {
        User user = new User();
        user.setUsername("existinguser");
        user.setPassword("password");

        Mockito.when(userRepository.findByUsername("existinguser"))
                .thenReturn(Optional.of(user));

        Assertions.assertThrows(RuntimeException.class, () -> userService.addUser(user));
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    // Позитивный тест - получение пользователя по имени
    @Test
    void testGetUserByUsername_success() {
        User user = new User();
        user.setUsername("testuser");

        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername("testuser");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("testuser", result.get().getUsername());
    }

    // Негативный тест - пользователь не найден
    @Test
    void testGetUserByUsername_notFound() {
        Mockito.when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserByUsername("unknown");

        Assertions.assertFalse(result.isPresent());
    }
}
