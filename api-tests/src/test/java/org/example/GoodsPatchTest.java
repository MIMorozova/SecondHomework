package org.example;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("apiTest")
public class GoodsPatchTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
    TestDataGenerator testDataGenerator  = new TestDataGenerator();
    GoodsRequestBody goodsRequestBody = new GoodsRequestBody();
    // Тест PATCH 200
    @Test
    public void goodsPatchTest() {
        //генерация названия и цены продукта
        String productName = testDataGenerator.generateName();
        Integer price = testDataGenerator.generatePrice();
        String requestBodyAddGoods = goodsRequestBody.requestBody(productName,price);
        System.out.println("Сгенерирован товар: name = " + productName + ", price = " + price);
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения
        Integer goodsId = postAddProductRequest.path("data.id");
        apiAssert.checkValueIsNotNull(goodsId);
        //данные для обновления
        String newProductName = testDataGenerator.generateName();
        Integer newPrice = testDataGenerator.generatePrice();
        String requestBodyPatchGoods = goodsRequestBody.requestBody(newProductName,newPrice);
        System.out.println("Сгенерирован товар: newName = " + newProductName + ", newPrice = " + newPrice);
        Response responseGoods = goodsApi.patchGoods(requestBodyPatchGoods,goodsId);
        apiAssert.statusCode(responseGoods, 200);
        Response responseGoodsNew = goodsApi.getGoodsById(goodsId);
        apiAssert.statusCode(responseGoodsNew, 200);
        apiAssert.checkBody(responseGoodsNew, "name", newProductName);
        apiAssert.checkBody(responseGoodsNew, "price", newPrice.floatValue());
    }

    // Тест PATCH 400
    @Test
    public void goodsPatchFailTest() {
        String productName = testDataGenerator.generateName();
        Integer productPrice = testDataGenerator.generatePrice();
        String requestBodyAddGoods = goodsRequestBody.requestBody(productName,productPrice);
        System.out.println("Сгенерирован товар: name = " + productName + ", price = " + productPrice);
        String productNameNew = testDataGenerator.generateName();
        Integer productPriceNew = testDataGenerator.generatePrice();
        String requestBodyAddGoodsNew = goodsRequestBody.requestBody(productNameNew,productPriceNew);
        System.out.println("Сгенерирован товар: name = " + productNameNew + ", price = " + productPriceNew);

        // Создаем товар 1
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения

        // Создаем товар 2
        Response postAddSecondProductRequest = goodsApi.postGoods(requestBodyAddGoodsNew);
        apiAssert.statusCode(postAddSecondProductRequest, 200);
        System.out.println(postAddSecondProductRequest.asString()); //вывод в консоль значения
        Integer productIdNew = postAddSecondProductRequest.path("data.id");
        apiAssert.checkValueIsNotNull(productIdNew);
        String requestBodyPatchDuplicateName = goodsRequestBody.requestBody(productName,productPriceNew);
        //Проверка PATCH
        Response responseGoods = goodsApi.patchGoods(requestBodyPatchDuplicateName,productIdNew);
        apiAssert.statusCode(responseGoods, 400);
    }

    // Тест PATCH 404
    @Test
    public void goodsPatchNotFoundTest(){
        String productName = testDataGenerator.generateName();
        Integer productPrice = testDataGenerator.generatePrice();
        String requestBodyAddGoods = goodsRequestBody.requestBody(productName,productPrice);
        System.out.println("Сгенерирован товар: name = " + productName + ", price = " + productPrice);
        Response postAddSecondProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddSecondProductRequest, 200);
        System.out.println(postAddSecondProductRequest.asString()); //вывод в консоль значения
        Integer productId = postAddSecondProductRequest.path("data.id");
        apiAssert.checkValueIsNotNull(productId);

        // Удаление товара по id
       Response deleteGoods = goodsApi.deleteGoods(productId);
        apiAssert.statusCode(deleteGoods, 200);
        //Проверка PATCH
        Response patchGoodsRequest = goodsApi.patchGoods(requestBodyAddGoods,productId);
        apiAssert.statusCode(patchGoodsRequest, 404);
    }
}