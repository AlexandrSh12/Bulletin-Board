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

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginSeleniumTest {

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

    // Тест успешного входа в систему
    @Test
    void testSuccessfulLogin() {
        driver.get(BASE_URL + "/login");

        WebElement usernameInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.name("username")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));

        usernameInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        submitButton.click();

        // После входа должны попасть на страницу со списком объявлений
        wait.until(ExpectedConditions.urlContains("/custom/items/view/list"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/custom/items/view/list"));
    }

    // Тест выхода из системы
    @Test
    void testLogout() {
        // Сначала входим
        driver.get(BASE_URL + "/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/custom/items/view/list"));

        // Нажимаем кнопку выхода
        WebElement logoutButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[type='submit']")));
        logoutButton.click();

        // После выхода должны попасть на страницу логина
        wait.until(ExpectedConditions.urlContains("/login"));
        Assertions.assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}