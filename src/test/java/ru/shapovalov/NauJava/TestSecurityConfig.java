package ru.shapovalov.NauJava;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Отдельный конфиг безопасности только для тестов.
// Отключает CSRF-защиту чтобы RestAssured мог логиниться простым POST-запросом без предварительного получения CSRF-токена со страницы логина.
// Основной SecurityConfig при этом остаётся нетронутым.
@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/registration", "/login", "/logout").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/custom/items/view/list", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll())
                .csrf(csrf -> csrf.disable()); // отключаем только в тестах
        return http.build();
    }
}