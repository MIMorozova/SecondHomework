package org.example;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("apiTest")
public class GoodsAddTest {
    @Test
    public void testGoodsAdd() {
        long uniqueValue = System.currentTimeMillis();
        String name = "Product_" + uniqueValue; //генерируем уникальное имя товара
        Random priceGenerator = new Random();
        Integer price = priceGenerator.nextInt(901) + 100; //задаем диапазон цены от 100 до 1000
        String requestBodyAddGoods = """
        {
            "name": "%s",
            "price": %d
        }
        """.formatted(name, price); //сохранение сгенерированных данных в JSON
        System.out.println("Сгенерирован товар: name = " + name + ", price = " + price); //Вывод данных, с которыми будет проходить тест

        RequestSpecification requestSpecForAdd = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType("application/json")
                .body(requestBodyAddGoods);
        Response addProducts = requestSpecForAdd // сохранили ответ в переменную
                .when()
                .post("/goods/add");
        System.out.println(addProducts.asString());
        addProducts
                .then()
                .statusCode(200);
        Integer productId = addProducts.path("data.id");
        assertThat(productId)
                .as("Проверка что значение productId не null")
                .isNotNull();


    }

    @Test
    public void testGoodsAddFail(){
        long uniqueValue = System.currentTimeMillis();
        String name = "Product_" + uniqueValue; //генерируем уникальное имя товара
        Random priceGenerator = new Random();
        Integer price = priceGenerator.nextInt(901) + 100; //задаем диапазон цены от 100 до 1000
        String requestBodyAddGoods = """
        {
            "name": "%s",
            "price": %d
        }
        """.formatted(name, price); //сохранение сгенерированных данных в JSON
        System.out.println("Сгенерирован товар: name = " + name + ", price = " + price); //Вывод данных, с которыми будет проходить тест

        RequestSpecification requestSpecForAdd = given()
                .baseUri("http://localhost:8080/")
                .auth()
                .basic("admin", "secret123")
                .contentType("application/json")
                .body(requestBodyAddGoods);
        requestSpecForAdd.when()
                .post("/goods/add")
                .then()
                .statusCode(200);
        requestSpecForAdd.when()
                .post("/goods/add")
                .then()
                .statusCode(400);
    }
}