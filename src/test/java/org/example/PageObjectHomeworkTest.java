package org.example;

import com.codeborne.selenide.logevents.SelenideLogger;
import org.example.pages.AdminLoginPage;
import org.example.pages.AdminPage;
import org.example.pages.CartPage;
import org.example.pages.MainPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class PageObjectHomeworkTest {
    @BeforeAll
    public static void addListener(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }
    @BeforeEach
    public void setUp(){
        closeWebDriver();
        open(ConfigProvider.config.testBaseUrl());
    }
    // 2.1. Добавить три единицы товара в корзину и оплатить их (общая стоимость не должна превышать 300 рублей).
    @Test
    public void addThreeUnitsOfProductToCartAndPlaceOrder(){
        MainPage mainPage = new MainPage();
        mainPage.setProductQuantity(ConfigProvider.config.testProductName(), 3);
        mainPage.addProductToCart(ConfigProvider.config.testProductName());
        mainPage.check().productAddedNotificationIs(ConfigProvider.config.testProductName() + " (3 шт.) добавлен в корзину");
        mainPage.openCart();
        CartPage cartPage = new CartPage();
        cartPage.check().productQuantityIs(ConfigProvider.config.testProductName(),3);
        cartPage.check().totalPriceIs(ConfigProvider.config.testProductPrice() * 3);
        cartPage.pressMakeOrderButton();
        cartPage.check().orderNotificationIs("Заказ принят в обработку!");
    }

    // 2.2. Добавить в корзину несколько разных товаров и проверить, что общая цена в корзине считается корректно.
    @Test
    public void addItemsToCartAndCheckTotalPrice(){
        MainPage mainPage = new MainPage();
        mainPage.addProductToCart(ConfigProvider.config.testProductName());
        mainPage.addProductToCart("Mushrooms");
        mainPage.openCart();
        CartPage cartPage = new CartPage();
        cartPage.check().productIsInCart(ConfigProvider.config.testProductName());
        cartPage.check().productIsInCart("Mushrooms");
        cartPage.check().productQuantityIs("Mushrooms",1);
        cartPage.check().cartItemsCountIs(2);
        cartPage.check().totalPriceIs(ConfigProvider.config.testProductPrice()+300);
    }

    // 2.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.
    @Test
    public void addProductInAdminAndCheckToast(){
        MainPage mainPage = new MainPage();
        mainPage.pressAdminButton();
        AdminLoginPage adminLoginPage= new AdminLoginPage();
        AdminPage adminPage = adminLoginPage.loginInAdmin(ConfigProvider.config.testLoginAdmin(), ConfigProvider.config.testPasswordAdmin());
        TestDataGenerator testDataGenerator = new TestDataGenerator();
        String productNameAdmin = testDataGenerator.generateName();
        Integer productPriceAdmin = testDataGenerator.generatePrice();
        adminPage.addProduct(productNameAdmin,productPriceAdmin);
        adminPage.check().productAddedNotificationIs("Товар успешно добавлен!");
    }

    //2.4. Войти в админку и отредактировать товар. Выйти на список товаров и проверить, что изменения применились.
    @Test
    public void editProductInAdminAndCheck(){
        MainPage mainPage = new MainPage();
        mainPage.pressAdminButton();
        AdminLoginPage adminLoginPage= new AdminLoginPage();
        AdminPage adminPage = adminLoginPage.loginInAdmin(ConfigProvider.config.testLoginAdmin(), ConfigProvider.config.testPasswordAdmin());
        TestDataGenerator testDataGenerator = new TestDataGenerator();
        // создаем исходный товар в админке
        String productNameAdmin = testDataGenerator.generateName();
        Integer productPriceAdmin = testDataGenerator.generatePrice();
        adminPage.addProduct(productNameAdmin,productPriceAdmin);
        adminPage.check().productAddedNotificationIs("Товар успешно добавлен!");
        // создаем данные для редактирования
        String newProductName = testDataGenerator.generateName();
        Integer newProductPrice = testDataGenerator.generatePrice();
        adminPage.editItemInProductRow(productNameAdmin, newProductName, newProductPrice);
        adminPage.check().productEditNotificationIs("обновлен");
        mainPage=adminPage.backToTheMainPage();
        mainPage.check().productNameIsVisible(newProductName);
        mainPage.check().productPriceIs(newProductName,newProductPrice);
    }
}
