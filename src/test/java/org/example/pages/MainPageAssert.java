package org.example.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.AbstractAssert;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {
    // конструктор
    public MainPageAssert(MainPage mainPage) {
        super(mainPage,MainPageAssert.class);// передаем родительскому AbstractAssert
    }
    // Проверка счетчика корзины
    public void cartCountIs(int expectedCount){
        String expectedCountText = String.valueOf(expectedCount);
        actual.cartCount.shouldHave(Condition.text(expectedCountText));
    }
    // проверка видимости cartCount (число в счетчике)
    public void cartCountIsVisible(){
        actual.cartCount.shouldBe(Condition.visible);
    }
    // проверка видимости adminButton
    public void adminButtonIsVisible(){
        actual.adminButton.shouldBe(Condition.visible);
    }
    //кнопка «Корзина» видима на MainPage
    public void cartButtonIsVisible(){
        actual.openCartButton.shouldBe(Condition.visible);
    }
    // в списке названий товаров есть нужный товар
    public void productNameIsVisible(String productName){
        actual.productNames.findBy(Condition.text(productName)).shouldBe(Condition.visible);
    }
    // проверка количества карточек
    public void productCardsCountIs(int expectedCount){
        actual.cardItems.shouldHave(CollectionCondition.size(expectedCount));
    }
    // проверка количества кнопок «В корзину»
    public void addToCartButtonsCountIs(int expectedCount){
        actual.addToCartButtons.shouldHave(CollectionCondition.size(expectedCount));
    }
    // проверка уведомления (текст и видимость)
    public void productAddedNotificationIs(String expectedText) {
        actual.toastNotifications.findBy(Condition.text(expectedText)).shouldBe(Condition.visible);
    }
    // проверка новой цены после редактирования  в админке
    public void productPriceIs(String productName, int expectedPrice){
        SelenideElement productCard = actual.cardItems.findBy(Condition.attribute("data-name", productName));
        String expectedPriceText = String.valueOf(expectedPrice);
        productCard.shouldHave(Condition.attribute("data-price", expectedPriceText));
    }
}
