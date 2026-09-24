package org.example;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;


public class RestAssuredHomeworkTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
    @BeforeAll
    public static void printConfig() {
        System.out.println(ConfigProvider.config.testBaseUrl());
        System.out.println(ConfigProvider.config.testApiUrl());
        System.out.println(ConfigProvider.config.testTimeout());
        System.out.println(ConfigProvider.config.testLoggingMode());
        System.out.println(ConfigProvider.config.testProductName());
        System.out.println(ConfigProvider.config.testProductPrice());
    }
    @Test
    public void testGoods(){
        Response responseGoods = goodsApi.getGoods(0, 10);
        apiAssert.statusCode(responseGoods, 200);
        apiAssert.checkBodyIsEmpty(responseGoods, "goods");
    }

    @Test
    public void testGoodsRequestSpecification(){
        Response responseGoods = goodsApi.getGoods(0, 10);
        apiAssert.statusCode(responseGoods, 200);
        apiAssert.checkBodyIsEmpty(responseGoods, "goods");
    }
    @Test
    public void testAddGoods(){
        String productName = ConfigProvider.config.testProductName();
        Integer productPrice = ConfigProvider.config.testProductPrice();
        String requestBodyAddGoods = """
        {
          "name": "%s",
          "price": %d
        }
        """.formatted(productName,productPrice);
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);

        Response responseGoods = goodsApi.getGoods(0, 10);
        apiAssert.statusCode(responseGoods, 200);
        apiAssert.checkBodyHasItem(responseGoods,"goods.name",productName);
        apiAssert.checkBodyHasItem(responseGoods, "goods.price",productPrice.floatValue());
    }
    @Test
    public void testGoodsList() {
        String requestBodyAddGoods = """
                {
                  "name": "Mushrooms",
                  "price": 300
                }
                """;
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);
        Response responseGoods = goodsApi.getGoods(0, 10);
        List<String> actualGoodsNames = responseGoods.path("goods.name");
        Float actualPrice = responseGoods.path("goods.find { it.name == 'Mushrooms' }.price");
        String expectedGoodsName = "Mushrooms";
        Float expectedPrice = 300.0F;

        apiAssert.checkListContains(actualGoodsNames, expectedGoodsName);
        apiAssert.checkValueEquals(actualPrice, expectedPrice);
    }
}

