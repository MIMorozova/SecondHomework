package org.example;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Random;

@Tag("apiTest")
public class GoodsAddTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
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
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        System.out.println(postAddProductRequest.asString());
        apiAssert.statusCode(postAddProductRequest, 200);
        Integer productId = postAddProductRequest.path("data.id");
        apiAssert.checkValueIsNotNull(productId);
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

        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest, 200);
        Response secondPostAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(secondPostAddProductRequest, 400);
    }
}