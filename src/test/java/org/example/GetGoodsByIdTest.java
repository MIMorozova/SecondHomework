package org.example;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Random;


@Tag("apiTest")
public class GetGoodsByIdTest {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
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

        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения
        Integer goodsId = postAddProductRequest.path("data.id");

        Response responseGoods = goodsApi.getGoodsById(goodsId);
        apiAssert.statusCode(responseGoods, 200);
        apiAssert.checkBody(responseGoods, "id", goodsId); //проверка на содержание в ответе id
        apiAssert.checkBody(responseGoods,"name",name); //проверка на содержание в ответе наименования
        apiAssert.checkBody(responseGoods, "price",price.floatValue()); //проверка на содержание в ответе цены (+приведение  к нужному типу)
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
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);

        System.out.println(postAddProductRequest.asString()); //вывод в консоль значения
        Integer goodsId = postAddProductRequest.path("data.id");

        //удаляем товар с ранее созданным id
        Response deleteGoodsResponse = goodsApi.deleteGoods(goodsId);
        apiAssert.statusCode(deleteGoodsResponse, 200);

        //проверка что товар удалился
        Response responseGoods = goodsApi.getGoodsById(goodsId);
        System.out.println(responseGoods.asString()); //вывод в консоль значения
        apiAssert.statusCode(responseGoods, 404);
    }
}
