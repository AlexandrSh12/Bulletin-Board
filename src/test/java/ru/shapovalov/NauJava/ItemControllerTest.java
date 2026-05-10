package ru.shapovalov.NauJava;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("default")
@Import(TestSecurityConfig.class)
public class ItemControllerTest {

    @LocalServerPort
    private int port;

    private String sessionCookie;
    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";

        sessionCookie = given()
                .formParam("username", "admin")
                .formParam("password", "admin")
                .when()
                .post("/login")
                .then()
                .extract()
                .cookie("JSESSIONID");
    }

    // Позитивный тест — findByPrice возвращает 200 и список
    @Test
    void testFindByPrice_returnsOk() {
        System.out.println("Используем JSESSIONID: " + sessionCookie);

        String body = given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("min", 100.0)
                .queryParam("max", 1000.0)
                .when()
                .get("/custom/items/findByPrice")
                .then()
                .extract().body().asString();

        System.out.println("Тело ответа: " + body);
    }

    // Пограничный тест — min равен max
    @Test
    void testFindByPrice_minEqualsMax() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("min", 500.0)
                .queryParam("max", 500.0)
                .when()
                .get("/custom/items/findByPrice")
                .then()
                .statusCode(200)
                .body("$", instanceOf(java.util.List.class));
    }

    // Пограничный тест — min больше max, ожидаем пустой список
    @Test
    void testFindByPrice_minGreaterThanMax() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("min", 1000.0)
                .queryParam("max", 100.0)
                .when()
                .get("/custom/items/findByPrice")
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    // Негативный тест — отсутствует параметр min
    @Test
    void testFindByPrice_missingMin() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("max", 1000.0)
                .when()
                .get("/custom/items/findByPrice")
                .then()
                .statusCode(400);
    }

    // Негативный тест — отсутствует параметр max
    @Test
    void testFindByPrice_missingMax() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("min", 100.0)
                .when()
                .get("/custom/items/findByPrice")
                .then()
                .statusCode(400);
    }

    // Негативный тест — неверный тип параметра
    @Test
    void testFindByPrice_invalidParamType() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("min", "abc")
                .queryParam("max", 1000.0)
                .when()
                .get("/custom/items/findByPrice")
                .then()
                .statusCode(400);
    }

    // Позитивный тест — findCommentsByItemId возвращает 200 и список
    @Test
    void testFindCommentsByItemId_returnsOk() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("itemId", 1)
                .when()
                .get("/custom/items/findCommentsByItemId")
                .then()
                .statusCode(200)
                .body("$", instanceOf(java.util.List.class));
    }

    // Пограничный тест — несуществующий itemId возвращает пустой список
    @Test
    void testFindCommentsByItemId_nonExistent() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("itemId", 999999)
                .when()
                .get("/custom/items/findCommentsByItemId")
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    // Пограничный тест — отрицательный itemId возвращает пустой список
    @Test
    void testFindCommentsByItemId_negativeId() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .queryParam("itemId", -1)
                .when()
                .get("/custom/items/findCommentsByItemId")
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    // Негативный тест — параметр itemId не передан
    @Test
    void testFindCommentsByItemId_missingParam() {
        given()
                .cookie("JSESSIONID", sessionCookie)
                .when()
                .get("/custom/items/findCommentsByItemId")
                .then()
                .statusCode(400);
    }
}