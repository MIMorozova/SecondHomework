package org.example.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class AdminPage {
    // элементы страницы
    // поле названия
    SelenideElement productNameField = $x("//input[@id='n-name']");
    // поле цена
    SelenideElement productPriceField = $x("//input[@id='n-price']");
    // кнопка создать
    SelenideElement addProductButton = $x("//button[@id='add-btn']");
    // уведомление
    ElementsCollection toastNotifications = $$x("//div[@class='toast']");
    // вернуться на сайт
    SelenideElement backToMainPageLink = $x("//a[@href='/']");


    // методы взаимодействия
    //ввод названия
    @Step
    public AdminPage addProductName(String productName) {
        productNameField.setValue(productName);
        return this;
    }
    //ввод цены товара
    @Step
    public AdminPage addProductPrice(int productPrice){
        String productPriceText = String.valueOf(productPrice);
        productPriceField.setValue(productPriceText);
        return this;
    }
    @Step
    public AdminPage pressAddProductButton(){
        addProductButton.click();
        return this;
    }
    public AdminPageAssert check() {
        return new AdminPageAssert(this);
    }
    //найти нужный товар из списка
    public SelenideElement findProductRow(String productName){
        SelenideElement productRow = $x("//tr[.//input[@value='" + productName + "']]");
        return productRow;
    }
    // поиск конкретного товара
    @Step
    public AdminPage editItemInProductRow(String productName, String newProductName, int newProductPrice){
        SelenideElement productRow = findProductRow(productName);
        SelenideElement productNameField = productRow.$("[id^='nm-']");
        productNameField.setValue(newProductName);
        SelenideElement productPriceField = productRow.$("[id^='pr-']");
        String newProductPriceText = String.valueOf(newProductPrice);
        productPriceField.setValue(newProductPriceText);
        SelenideElement saveButton = productRow.$x(".//button[@data-action='update']");
        saveButton.click();
        return this;
    }
    // венуться на сайт
    @Step
    public MainPage backToTheMainPage(){
        backToMainPageLink.click();
        return new MainPage();
    }
    @Step
    public AdminPage addProduct(String productName, int productPrice){
        addProductName(productName);
        addProductPrice(productPrice);
        pressAddProductButton();
        return this;
    }
}



