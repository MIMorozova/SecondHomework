package org.example;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.DragAndDropOptions.to;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Configuration;

public class DragAndDropTest {
    TestDataGenerator dataGenerator = new TestDataGenerator();
    @BeforeAll
    public static void printConfig() {
        System.out.println(ConfigProvider.config.testBaseUrl());
        System.out.println(ConfigProvider.config.testApiUrl());
        System.out.println(ConfigProvider.config.testTimeout());
        System.out.println(ConfigProvider.config.testLoggingMode());
        System.out.println(ConfigProvider.config.testProductName());
        System.out.println(ConfigProvider.config.testProductPrice());
        Configuration.timeout = ConfigProvider.config.testTimeout() * 1000;
    }

    @BeforeEach
    public void openBrowserTest() {
        open(ConfigProvider.config.testBaseUrl());
    }
    // 1.1 Перетащить элемент в корзину с помощью Drag-and-Drop.

    @AfterEach
    public void closeBrowserTest(){
        closeWebDriver();
    }

    @Test
    public void dragElementToTheBasket(){
        $x("//a[@href='/admin']").click(); //находим SelenideElement + click
        //Авторизация
        $x("//input[@id='username']").setValue(ConfigProvider.config.testLoginAdmin());
        $x("//input[@id='password']").setValue(ConfigProvider.config.testPasswordAdmin());
        $x("//button[@class='primary']").click();
        //Генерация названия товара и цены
        String productName = dataGenerator.generateName();
        Integer productPrice = dataGenerator.generatePriceUpTo100();
        String productPriceText = String.valueOf(productPrice);
        // добавляем товар
        $x("//input[@id='n-name']").setValue(productName);
        $x("//input[@id='n-price']").setValue(productPriceText);
        $x("//button[@id='add-btn']").click();
        $x("//input[@value='" + productName + "']").shouldBe(visible);// находим поле с созданным товаром и ждем, пока оно станет видимым
        $x("//a[@href='/']").click();
        // находим карточку товара (source)
        SelenideElement productCard  = $x("//div[@class='product-card'][@data-name='" + productName + "'][@data-price='" + productPriceText + "']");
        // находим target
        SelenideElement basket = $x("//button[@id='open-cart-btn']");
        productCard.dragAndDrop(to(basket)); // перетаскивание карточки в корзину
        // проверка уведомления
        $x("//div[@id='toast-container']/div[@class='toast']").shouldHave(exactText(productName + " (1 шт.) добавлен в корзину"));
        //проверка счетчика корзины
        $x("//button[@id='open-cart-btn']//span[@id='cart-count']")
                .shouldHave(exactText("1"));
}

    //1.2 Удалить добавленный элемент из корзины и проверить, что он там больше не отображаетс
    @Test
    public void deleteElementFromTheBasket(){
        $x("//a[@href='/admin']").click(); //находим SelenideElement + click
        //Авторизация
        $x("//input[@id='username']").setValue(ConfigProvider.config.testLoginAdmin());
        $x("//input[@id='password']").setValue(ConfigProvider.config.testPasswordAdmin());
        $x("//button[@class='primary']").click();
        //Генерация названия товара и цены
        String productName = dataGenerator.generateName();
        Integer productPrice = dataGenerator.generatePriceUpTo100();
        String productPriceText = String.valueOf(productPrice);
        // добавляем товар
        $x("//input[@id='n-name']").setValue(productName);
        $x("//input[@id='n-price']").setValue(productPriceText);
        $x("//button[@id='add-btn']").click();
        $x("//input[@value='" + productName + "']").shouldBe(visible);// находим поле с созданным товаром и ждем, пока оно станет видимым
        $x("//a[@href='/']").click(); // выйти из админки
        // Находим карточку товара (source)
        SelenideElement productCard  = $x("//div[@class='product-card'][@data-name='" + productName + "'][@data-price='" + productPriceText + "']");
        // находим target
        SelenideElement basket = $x("//button[@id='open-cart-btn']");
        productCard.dragAndDrop(to(basket)); // перетаскивание карточки в корзину
        // проверка уведомления
        $x("//div[@id='toast-container']/div[@class='toast']").shouldHave(exactText(productName + " (1 шт.) добавлен в корзину"));
        //переходим в корзину
        $x("//button[@id='open-cart-btn']").click();
        // из корзины удаляем товар
        $x("//div[@id='cart-items']/div[@class='cart-item']//b[text()='" + productName + "']/ancestor::div[@class='cart-item']/button[@data-action='remove']").click();
        $x("//div[@id='cart-items']/div[@class='cart-item']//b[text()='" + productName + "']/ancestor::div[@class='cart-item']").should(not(exist));
    }
}