package org.example;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Random;


@Tag("apiTest")
public class GoodsPatchTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
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
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения
        Integer goodsId = postAddProductRequest.path("data.id");
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
        Response responseGoods = goodsApi.patchGoods(requestBodyPatchGoods,goodsId);
        apiAssert.statusCode(responseGoods, 200);
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
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения

        // Создаем товар 2
        Response postAddSecondProductRequest = goodsApi.postGoods(requestBodyAddGoodsNew);
        System.out.println(postAddSecondProductRequest.asString()); //вывод в консоль значения
        Integer productIdNew = postAddSecondProductRequest.path("data.id");
        String requestBodyPatchDuplicateName = """
               {
                "name": "%s",
                "price": %d
                }
        """.formatted(productName, productPriceNew);
        //Проверка PATCH
        Response responseGoods = goodsApi.patchGoods(requestBodyPatchDuplicateName,productIdNew);
        apiAssert.statusCode(responseGoods, 400);
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
        Response postAddSecondProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        System.out.println(postAddSecondProductRequest.asString()); //вывод в консоль значения
        Integer productId = postAddSecondProductRequest.path("data.id");
        // Удаление товара по id
       Response deleteGoods = goodsApi.deleteGoods(productId);
        //Проверка PATCH
        Response patchGoodsRequest = goodsApi.patchGoods(requestBodyAddGoods,productId);
        apiAssert.statusCode(patchGoodsRequest, 404);
    }
}