package org.example;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class RestAssuredHomeworkTest {
    @Test
    public void testGoods(){
        given()
                .baseUri("http://localhost:8080")
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
                .baseUri("http://localhost:8080")
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
        String requestBodyAddGoods = """
        {
          "name": "Limon",
          "price": 100
        }
        """;
        RequestSpecification requestSpecForAdd = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin","secret123")
                .contentType("application/json")
                .body(requestBodyAddGoods);
                requestSpecForAdd.when()
                .post("/goods/add")
                .then()
                .statusCode(200);

        RequestSpecification requestSpec = given()
                .baseUri("http://localhost:8080")
                .queryParam("page", 0)
                .queryParam("size", 10);
                requestSpec.when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem("Limon"))
                .body("goods.price", hasItem(100.0F));
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
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin","secret123")
                .contentType("application/json")
                .body(requestBodyAddGoods);
                requestSpecForAdd.when()
                .post("/goods/add")
                .then()
                .statusCode(200);
        Response responseGoods = given()
                 .baseUri("http://localhost:8080")
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

