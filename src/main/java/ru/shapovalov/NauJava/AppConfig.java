package ru.shapovalov.NauJava;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.shapovalov.NauJava.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class AppConfig {
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;


    public String getAppName() { return appName; }
    public String getAppVersion() { return appVersion; }
    // вывод инфы о приложении перенесен из сервисного слоя
    @PostConstruct
    public void printAppInfo() {
        System.out.println("Приложение: " + appName + " | Версия: " + appVersion);
    }
    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public List<Item> itemContainer() {
        return new ArrayList<>();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public AtomicLong idGenerator() {
        return new AtomicLong(1);
    }


}
