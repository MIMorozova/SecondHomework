package org.example;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("apiTest")
public class GoodsAddTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
    TestDataGenerator testDataGenerator  = new TestDataGenerator();
    GoodsRequestBody goodsRequestBody = new GoodsRequestBody();
    @Test
    public void testGoodsAdd() {
        String name = testDataGenerator.generateName();
        Integer price = testDataGenerator.generatePrice();
        String requestBodyAddGoods = goodsRequestBody.requestBody(name,price);
        System.out.println("Сгенерирован товар: name = " + name + ", price = " + price); //Вывод данных, с которыми будет проходить тест
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);
        System.out.println(postAddProductRequest.asString());
        Integer productId = postAddProductRequest.path("data.id");
        apiAssert.checkValueIsNotNull(productId);
    }

    @Test
    public void testGoodsAddFail(){
        String name = testDataGenerator.generateName();
        Integer price = testDataGenerator.generatePrice();
        String requestBodyAddGoods = goodsRequestBody.requestBody(name,price);
        System.out.println("Сгенерирован товар: name = " + name + ", price = " + price); //Вывод данных, с которыми будет проходить тест

        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);
        Response secondPostAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(secondPostAddProductRequest, 400);
    }
}