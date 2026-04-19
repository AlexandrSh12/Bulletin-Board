package ru.shapovalov.NauJava;

import io.restassured.RestAssured;
import io.restassured.filter.cookie.CookieFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

    @LocalServerPort
    private int port;

    private CookieFilter cookieFilter;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        cookieFilter = new CookieFilter();

        // Логинимся перед каждым тестом
        given()
                .filter(cookieFilter)
                .formParam("username", "admin")
                .formParam("password", "admin")
                .when()
                .post("/login");
    }

    // Позитивный тест - findByPrice возвращает список
    @Test
    void testFindByPrice_returnsItems() {
        given()
                .filter(cookieFilter)
                .queryParam("min", 100.0)
                .queryParam("max", 1000.0)
                .when()
                .get("/custom/items/findByPrice")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    // Позитивный тест - findCommentsByItemId возвращает 200
    @Test
    void testFindCommentsByItemId_returnsOk() {
        given()
                .filter(cookieFilter)
                .queryParam("itemId", 1)
                .when()
                .get("/custom/items/findCommentsByItemId")
                .then()
                .statusCode(200);
    }
}