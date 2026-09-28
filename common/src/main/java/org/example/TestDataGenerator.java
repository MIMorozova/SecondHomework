package org.example;

import java.util.Random;

public class TestDataGenerator {
    public String generateName(){
        long uniqueValue = System.currentTimeMillis();
        String name = "Product_" + uniqueValue; //генерируем уникальное имя товара
        return name;
    }
    public Integer generatePrice(){
        Random priceGenerator = new Random();
        Integer price = priceGenerator.nextInt(901) + 100; //задаем диапазон цены от 100 до 1000
        return price;
    }
    public Integer generatePriceUpTo100(){
        Random priceGeneratorUpTo = new Random();
        Integer priceUpTo = priceGeneratorUpTo.nextInt(100)+1;
        return priceUpTo;

    }
}
