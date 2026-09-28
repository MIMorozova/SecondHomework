package org.example;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("apiTest")
public class GoodsDeleteTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
    TestDataGenerator testDataGenerator  = new TestDataGenerator();
    GoodsRequestBody goodsRequestBody = new GoodsRequestBody();
    @Test
    public void testGoodsDelete(){
        String name = testDataGenerator.generateName();
        Integer price = testDataGenerator.generatePrice();
        String requestBodyAddGoods = goodsRequestBody.requestBody(name,price);
        System.out.println("Сгенерирован товар: name = " + name + ", price = " + price); //Вывод данных, с которыми будет проходить тест
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения
        Integer goodsId = postAddProductRequest.path("data.id");
        apiAssert.checkValueIsNotNull(goodsId);
        Response deleteProductRequest = goodsApi.deleteGoods(goodsId);
        apiAssert.statusCode(deleteProductRequest, 200);
    }
    @Test
    public void testGoodsDeleteFail(){
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
        Response deleteProductRequest = goodsApi.deleteGoods(goodsId);
        apiAssert.statusCode(deleteProductRequest, 200);
        Response secondDeleteProductRequest = goodsApi.deleteGoods(goodsId);
        apiAssert.statusCode(secondDeleteProductRequest, 404);
    }

}
