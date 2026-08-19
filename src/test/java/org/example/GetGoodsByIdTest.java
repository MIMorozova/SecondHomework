package org.example;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@Tag("apiTest")
public class GetGoodsByIdTest {
    @Test
    public void testGetGoods(){
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
        RequestSpecification requestSpecForAdd = given() //подготовили запрос и положили его настройки в requestSpecForAdd.
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
        RequestSpecification requestSpec = given()
                .baseUri("http://localhost:8080")
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", goodsId);
        requestSpec.when()
                .get("/goods/{id}")
                .then()
                .statusCode(200) //проверка на статус-код 200
                .body("id",equalTo(goodsId)) //проверка на содержание в ответе id
                .body("name",equalTo(name)) //проверка на содержание в ответе наименования
                .body("price",equalTo(price.floatValue())); //проверка на содержание в ответе цены (+приведение  к нужному типу)
    }
    @Test
    public void goodsNotFound(){
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
        //создаем товар
        RequestSpecification requestSpecForAdd = given() //подготовили запрос и положили его настройки в requestSpecForAdd.
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
        //удаляем товар с ранее созданным id
        RequestSpecification requestSpecForDelete = given()
                .baseUri("http://localhost:8080")
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", goodsId);
        requestSpecForDelete.when()
                .delete("/goods/{id}")
                .then()
                .statusCode(200);
        //проверка что товар удалился
        RequestSpecification requestSpecCheckGoods = given()
                .baseUri("http://localhost:8080")
                .auth()
                .basic("admin", "secret123")
                .pathParam("id", goodsId);
        Response getGoods = requestSpecCheckGoods // сохранили ответ в переменную
                .when()
                .get("/goods/{id}");
        System.out.println(getGoods.asString()); //вывод в консоль значения
        getGoods
                .then()
                .statusCode(404);
    }
}
