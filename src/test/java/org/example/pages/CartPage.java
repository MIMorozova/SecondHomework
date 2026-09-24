package org.example.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class CartPage {
    // элементы страницы
    // кнопка закрытия
    SelenideElement closeButton = $x("//span[@id='close-modal']");
    //кнопка оформления заказа
    SelenideElement makeOrderButton = $x("//button[@id='makeOrder']");
    //контейнер товаров
    SelenideElement cartItemsContainer = $x("//div[@id='cart-items']");
    // коллекция товаров в корзине
    ElementsCollection cartProductItems = $$x("//div[@class='cart-item']");
    // итоговая стоимость
    SelenideElement totalPrice = $x("//span[@id='total-price']");
    // текущее toast-уведомление
    ElementsCollection toastNotifications = $$x("//div[@class='toast']");



    // методы взаимодействия
    @Step
    public CartPage pressCloseButton() {
        closeButton.click();
        return this;
    }
    @Step
    public CartPage pressMakeOrderButton() {
        makeOrderButton.click();
        return this;
    }

    // товар в корзине по productName
    public SelenideElement findProduct(String productName) {
        SelenideElement productCard = cartProductItems.findBy(Condition.text(productName));
        return productCard;
    }

    // удалить конкретный товар из корзины
    @Step
    public CartPage removeProduct(String productName){
        SelenideElement productCard = findProduct(productName);
        // кнопка удаления товара
        SelenideElement removeButton = productCard.$x(".//button[@data-action='remove']");
        removeButton.click();
        return this;
    }
    //
    public CartPageAssert check(){
        return new CartPageAssert(this);
    }

}


