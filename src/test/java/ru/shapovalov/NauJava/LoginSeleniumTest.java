package ru.shapovalov.NauJava;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(SeleniumTestDataConfig.class)
public class LoginSeleniumTest {

    private static final String TEST_USERNAME = "selenium_admin";
    private static final String TEST_PASSWORD = "selenium_pass";

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");   // запуск без окна браузера
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Тест успешного входа в систему.
    // Используем учётные данные из констант TEST_USERNAME/TEST_PASSWORD, которые соответствуют пользователю, созданному в SeleniumTestDataConfig,
    // а не зависят от @PostConstruct продакшн-сервиса.
    @Test
    void testSuccessfulLogin() {
        // открываем страницу логина
        driver.get(BASE_URL + "/login");

        // вводим учётные данные тестового пользователя
        driver.findElement(By.name("username")).sendKeys(TEST_USERNAME);
        driver.findElement(By.name("password")).sendKeys(TEST_PASSWORD);

        // нажимаем кнопку входа
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        // ждём редирект на страницу списка объявлений и проверяем url
        wait.until(ExpectedConditions.urlContains("/custom/items/view/list"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/custom/items/view/list"));
    }

    // Тест выхода из системы
    @Test
    void testLogout() {
        // входим в систему через тестового пользователя
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).sendKeys(TEST_USERNAME);
        driver.findElement(By.name("password")).sendKeys(TEST_PASSWORD);
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        // ждём успешного входа
        wait.until(ExpectedConditions.urlContains("/custom/items/view/list"));

        // нажимаем кнопку выхода
        WebElement logoutButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("button[type='submit']")));
        logoutButton.click();

        // ждём редиректа на страницу логина и проверяем url
        wait.until(ExpectedConditions.urlContains("/login"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}