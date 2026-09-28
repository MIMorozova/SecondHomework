package org.example;

public class GoodsRequestBody {
    public String requestBody(String productName, Integer price){
        String requestBodyAddProduct = """
                {
                    "name": "%s",
                    "price": %d
                }
                """.formatted(productName, price); //сохранение сгенерированных данных в JSON
        return requestBodyAddProduct;
    }
}
