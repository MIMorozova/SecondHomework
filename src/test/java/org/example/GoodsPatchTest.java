package org.example;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static io.restassured.RestAssured.given;


@Tag("apiTest")
public class GoodsPatchTest {
    // Тест PATCH 200
    @Test
    public void goodsPatchTest() {
        //генерация названия и цены продукта
        long uniqueValue = System.currentTimeMillis();
        String productName = "Product" + uniqueValue;
        Random priceGenerator = new Random();
        Integer price = priceGenerator.nextInt(901) + 100; //задаем диапазон цены от 100 до 1000
        String requestBodyAddGoods = """
                {
                    "name": "%s",
                    "price": %d
                }
                """.formatted(productName, price); //сохранение сгенерированных данных в JSON
        System.out.println("Сгенерирован товар: name = " + productName + ", price = " + price);
        RequestSpecification requestSpecForAdd = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType("application/json")
                .body(requestBodyAddGoods);
        Response addGoods = requestSpecForAdd // сохранили ответ в переменную
                .when()
                .post("/goods/add");
        System.out.println(addGoods.asString()); //вывод в консоль значения
        Integer goodsId = addGoods.path("data.id");
        //данные для обновления
        String newProductName = "ProductNew" + uniqueValue;
        Integer newPrice = priceGenerator.nextInt(901) + 100; //задаем диапазон цены от 100 до 1000
        String requestBodyPatchGoods = """
                {
                    "name" : "%s",
                    "price" : %d
                }
                """.formatted(newProductName, newPrice);
        System.out.println("Сгенерирован товар: newName = " + newProductName + ", newPrice = " + newPrice);

        RequestSpecification requestSpecForPatch = given()
                .baseUri("http://localhost:8080")
                .auth()
                .basic("admin", "secret123")
                .contentType("application/json")
                .body(requestBodyPatchGoods)
                .pathParam("id", goodsId);
        requestSpecForPatch.when()
                .patch("/goods/{id}")
                .then()
                .statusCode(200);
    }
    // Тест PATCH 400
    @Test
    public void goodsPatchFailTest() {
        long uniqueValue = System.currentTimeMillis();
        String productName = "Product" + uniqueValue;
        Random priceGenerator = new Random();
        Integer productPrice = priceGenerator.nextInt(901) + 100;
        String requestBodyAddGoods = """
                {
                    "name": "%s",
                    "price": %d
                }
                """.formatted(productName, productPrice); //сохранение сгенерированных данных в JSON
        System.out.println("Сгенерирован товар: name = " + productName + ", price = " + productPrice);

        String productNameNew = "ProductNew" + uniqueValue;
        Integer productPriceNew = priceGenerator.nextInt(901) + 100;
        String requestBodyAddGoodsNew = """   
                {
                    "name": "%s", 
                    "price": %d   
                }
                """.formatted(productNameNew, productPriceNew); //сохранение сгенерированных данных в JSON
        System.out.println("Сгенерирован товар: name = " + productNameNew + ", price = " + productPriceNew);

        // Создаем товар 1
        RequestSpecification requestSpecForAdd = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType("application/json")
                .body(requestBodyAddGoods);
        Response addGoods = requestSpecForAdd // сохранили ответ в переменную
                .when()
                .post("/goods/add");
        System.out.println(addGoods.asString()); //вывод в консоль значения

        // Создаем товар 2
        RequestSpecification requestSpecForNewAdd = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType("application/json")
                .body(requestBodyAddGoodsNew);
        Response addGoodsNew = requestSpecForNewAdd // сохранили ответ в переменную
                .when()
                .post("/goods/add");
        System.out.println(addGoodsNew.asString()); //вывод в консоль значения
        Integer productIdNew = addGoodsNew.path("data.id");
        String requestBodyPatchDuplicateName = """
               {
                "name": "%s",
                "price": %d
                }
        """.formatted(productName, productPriceNew);
        //Проверка PATCH
        RequestSpecification requestSpecForPatch = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType("application/json")
                .body(requestBodyPatchDuplicateName)
                .pathParam("id", productIdNew);
        requestSpecForPatch.when()
                .patch("/goods/{id}")
                .then()
                .statusCode(400);
    }

    // Тест PATCH 404
    @Test
    public void goodsPatchNotFoundTest(){
        long uniqueValue = System.currentTimeMillis();
        String productName = "Product" + uniqueValue;
        Random priceGenerator = new Random();
        Integer productPrice = priceGenerator.nextInt(901) + 100;
        String requestBodyAddGoods = """
                {
                    "name": "%s",
                    "price": %d
                }
                """.formatted(productName, productPrice); //сохранение сгенерированных данных в JSON
        System.out.println("Сгенерирован товар: name = " + productName + ", price = " + productPrice);
        RequestSpecification requestSpecForAdd = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType("application/json")
                .body(requestBodyAddGoods);
        Response addGoods = requestSpecForAdd // сохранили ответ в переменную
                .when()
                .post("/goods/add");
        System.out.println(addGoods.asString()); //вывод в консоль значения
        Integer productId = addGoods.path("data.id");
        // Удаление товара по id
        RequestSpecification requestSpecForDelete = given()
                .baseUri("http://localhost:8080")
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", productId);
        requestSpecForDelete.when()
                .delete("/goods/{id}")
                .then()
                .statusCode(200);
        //Проверка PATCH
        RequestSpecification requestSpecForPatch = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType("application/json")
                .body(requestBodyAddGoods)
                .pathParam("id", productId);
        requestSpecForPatch.when()
                .patch("/goods/{id}")
                .then()
                .statusCode(404);
    }
}