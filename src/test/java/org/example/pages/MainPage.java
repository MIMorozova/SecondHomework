package org.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    SelenideElement adminButton = $x("//a[@href='/admin']");
    // коллекция карточек товаров на главной странице
    ElementsCollection cardItems = $$x("//div[@class='product-card']");
    // коллекция кнопок в карточке товаров
    ElementsCollection addToCartButtons = $$x("//button[@data-action='add-to-cart']");
    // кнопка "Корзина" на главной странице
    SelenideElement openCartButton = $x("//button[@id='open-cart-btn']");
    // коллекция названий товаров
    ElementsCollection productNames = $$x("//div[@class='product-card']/h4");
    // счетчик товаров в корзине
    SelenideElement cartCount = $x("//span[@id='cart-count']");
    // toast при добавлении товара в корзину
    ElementsCollection toastNotifications = $$x("//div[@class='toast']");


    //Методы взаимодействия
    public MainPage pressAdminButton(){
        adminButton.click();
        return this;
    }
    public MainPage openCart(){
        openCartButton.click();
        return this;
    }
    public MainPage addProductToCart(String productName){
        addToCartButtons.findBy(Condition.attribute("data-name",productName)).click();
        return this;
    }
    //из коллекции карточек получить одну конкретную карточку нужного товара
    public MainPage setProductQuantity(String productName, int count){
        SelenideElement productCard =cardItems.findBy(Condition.attribute("data-name",productName));
        String countText = String.valueOf(count);
        SelenideElement productQuantity = productCard.$(".qty-input");
        productQuantity.setValue(countText);
        return this;
    }
    public MainPageAssert check() {
        return new MainPageAssert(this);
    }
}