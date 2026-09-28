package org.example;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("apiTest")
public class GetGoodsListTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
    TestDataGenerator testDataGenerator  = new TestDataGenerator();
    GoodsRequestBody goodsRequestBody = new GoodsRequestBody();
    // Проверка GET 200
    @Test
    public void goodsListTest(){
        //генерация названия и цены продукта
        String productName = testDataGenerator.generateName();
        Integer price = testDataGenerator.generatePrice();
        String requestBodyAddProduct = goodsRequestBody.requestBody(productName,price);
        System.out.println("Сгенерирован товар: name = " + productName + ", price = " + price);
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddProduct);
        apiAssert.statusCode(postAddProductRequest, 200);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения
        Response getGoods = goodsApi.getGoods(0,100);
        apiAssert.statusCode(getGoods, 200);
        apiAssert.checkBodyHasItem(getGoods,"goods.name",productName);
        apiAssert.checkBodyHasItem(getGoods,"goods.price",price.floatValue());
    }
}
