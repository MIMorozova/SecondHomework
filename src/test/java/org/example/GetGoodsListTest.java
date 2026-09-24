package org.example;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Random;


@Tag("apiTest")
public class GetGoodsListTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
    // Проверка GET 200
    @Test
    public void goodsListTest(){
        //генерация названия и цены продукта
        long uniqueValue = System.currentTimeMillis();
        String productName = "Product" + uniqueValue;
        Random priceGenerator = new Random();
        Integer price = priceGenerator.nextInt(901) + 100; //задаем диапазон цены от 100 до 1000
        String requestBodyAddProduct = """
                {
                    "name": "%s",
                    "price": %d
                }
                """.formatted(productName, price); //сохранение сгенерированных данных в JSON
        System.out.println("Сгенерирован товар: name = " + productName + ", price = " + price);
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddProduct);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения
        Response getGoods = goodsApi.getGoods(0,100);
        apiAssert.statusCode(getGoods, 200);
        apiAssert.checkBodyHasItem(getGoods,"goods.name",productName);
        apiAssert.checkBodyHasItem(getGoods,"goods.price",price.floatValue());
    }
}
