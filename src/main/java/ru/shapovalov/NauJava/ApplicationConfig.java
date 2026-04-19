package ru.shapovalov.NauJava;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.shapovalov.NauJava.entity.Item;

import java.util.ArrayList;
import java.util.List;
@Configuration
public class ApplicationConfig {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Item> itemContainer(){
        return new ArrayList<>();
    }
}
