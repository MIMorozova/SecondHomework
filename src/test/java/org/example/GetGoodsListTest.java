package org.example;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;



@Tag("apiTest")
public class GetGoodsListTest {
    // Проверка GET 200
    @Test
    public void goodsListTest(){
        //генерация названия и цены продукта
        long uniqueValue = System.currentTimeMillis();
        String productName = "Product" + uniqueValue;
        Random priceGenerator = new Random();
        Integer price = priceGenerator.nextInt(901) + 100; //задаем диапазон цены от 100 до 1000
        String requestBodyAddProduct = """
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
                .body(requestBodyAddProduct);
        Response addProduct = requestSpecForAdd // сохранили ответ в переменную
                .when()
                .post("/goods/add");
        System.out.println(addProduct.asString()); //вывод в консоль значения
        RequestSpecification requestSpec = given()
                .baseUri("http://localhost:8080")
                .queryParam("page", 0)
                .queryParam("size", 100);
        requestSpec.when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods.name", hasItem(productName))
                .body("goods.price", hasItem(price.floatValue()));
    }
}
