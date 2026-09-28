package org.example;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("apiTest")
public class GetGoodsByIdTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
    TestDataGenerator testDataGenerator  = new TestDataGenerator();
    GoodsRequestBody goodsRequestBody = new GoodsRequestBody();
    @Test
    @Tag("smoke")
    public void testGetGoods(){
        String name = testDataGenerator.generateName();
        Integer price = testDataGenerator.generatePrice();
        String requestBodyAddGoods = goodsRequestBody.requestBody(name,price);
        System.out.println("Сгенерирован товар: name = " + name + ", price = " + price); //Вывод данных, с которыми будет проходить тест

        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения
        Integer goodsId = postAddProductRequest.path("data.id");
        apiAssert.checkValueIsNotNull(goodsId);

        Response responseGoods = goodsApi.getGoodsById(goodsId);
        apiAssert.statusCode(responseGoods, 200);
        apiAssert.checkBody(responseGoods, "id", goodsId); //проверка на содержание в ответе id
        apiAssert.checkBody(responseGoods,"name",name); //проверка на содержание в ответе наименования
        apiAssert.checkBody(responseGoods, "price",price.floatValue()); //проверка на содержание в ответе цены (+приведение  к нужному типу)
    }
    @Test
    public void goodsNotFound(){
        String name = testDataGenerator.generateName();
        Integer price = testDataGenerator.generatePrice();
        String requestBodyAddGoods = goodsRequestBody.requestBody(name,price);
        System.out.println("Сгенерирован товар: name = " + name + ", price = " + price); //Вывод данных, с которыми будет проходить тест
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения
        Integer goodsId = postAddProductRequest.path("data.id");
        apiAssert.checkValueIsNotNull(goodsId);

        //удаляем товар с ранее созданным id
        Response deleteGoodsResponse = goodsApi.deleteGoods(goodsId);
        apiAssert.statusCode(deleteGoodsResponse, 200);

        //проверка что товар удалился
        Response responseGoods = goodsApi.getGoodsById(goodsId);
        System.out.println(responseGoods.asString()); //вывод в консоль значения
        apiAssert.statusCode(responseGoods, 404);
    }
}
