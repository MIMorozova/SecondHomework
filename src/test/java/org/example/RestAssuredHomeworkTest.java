package org.example;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class RestAssuredHomeworkTest {
    @BeforeAll
    public static void printConfig() {
        System.out.println(ConfigProvider.config.testBaseUrl());
        System.out.println(ConfigProvider.config.testApiUrl());
        System.out.println(ConfigProvider.config.testTimeout());
        System.out.println(ConfigProvider.config.testLoggingMode());
        System.out.println(ConfigProvider.config.testProductName());
        System.out.println(ConfigProvider.config.testProductPrice());
    }
    @Test
    public void testGoods(){
        given()
                .baseUri(ConfigProvider.config.testApiUrl())
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty());
    }

    @Test
    public void testGoodsRequestSpecification(){
        RequestSpecification requestSpec = given()
                .baseUri(ConfigProvider.config.testApiUrl())
                .queryParam("page", 0)
                .queryParam("size", 10);
                requestSpec.when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty());
    }
    @Test
    public void testAddGoods(){
        String productName = ConfigProvider.config.testProductName();
        Integer productPrice = ConfigProvider.config.testProductPrice();
        String requestBodyAddGoods = """
        {
          "name": "%s",
          "price": %d
        }
        """.formatted(productName,productPrice);
        RequestSpecification requestSpecForAdd = given()
                .baseUri(ConfigProvider.config.testApiUrl())
                .auth()
                .basic(ConfigProvider.config.testLoginAdmin(),ConfigProvider.config.testPasswordAdmin())
                .contentType("application/json")
                .body(requestBodyAddGoods);
                requestSpecForAdd.when()
                .post("/goods/add")
                .then()
                .statusCode(200);

        RequestSpecification requestSpec = given()
                .baseUri(ConfigProvider.config.testApiUrl())
                .queryParam("page", 0)
                .queryParam("size", 10);
                requestSpec.when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem(productName))
                .body("goods.price", hasItem(productPrice.floatValue()));
    }
    @Test
    public void testGoodsList(){
        String requestBodyAddGoods = """
        {
          "name": "Mushrooms",
          "price": 300
        }
        """;
        RequestSpecification requestSpecForAdd = given()
                .baseUri(ConfigProvider.config.testApiUrl())
                .auth()
                .basic(ConfigProvider.config.testLoginAdmin(),ConfigProvider.config.testPasswordAdmin())
                .contentType("application/json")
                .body(requestBodyAddGoods);
                requestSpecForAdd.when()
                .post("/goods/add")
                .then()
                .statusCode(200);
        Response responseGoods = given()
                 .baseUri(ConfigProvider.config.testApiUrl())
                 .queryParam("page", 0)
                 .queryParam("size", 10)
                 .when()
                 .get("/goods/list");
        List<String> actualGoodsNames = responseGoods.path("goods.name");
        Float actualPrice = responseGoods.path("goods.find { it.name == 'Mushrooms' }.price");
        String expectedGoodsName = "Mushrooms";
        Float expectedPrice = 300.0F;
        assertThat(actualGoodsNames)
                .as("Проверка добавления %s в список товаров", expectedGoodsName)
                .contains(expectedGoodsName);
        assertThat(actualPrice)
                .as("Проверка добавления для товара %s цены %s",expectedGoodsName,expectedPrice)
                .isEqualTo(expectedPrice);
    }
}

