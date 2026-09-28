package org.example.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

public class CartPageAssert extends AbstractAssert<CartPageAssert, CartPage> {
    // конструктор
    public CartPageAssert(CartPage cartPage) {
        super(cartPage, CartPageAssert.class); // передаем родительскому AbstractAssert
    }

    // проверка итоговой суммы
    @Step
    public void totalPriceIs(int expectedTotalPrice) {
        String expectedTotalPriceText = String.valueOf(expectedTotalPrice);
        actual.totalPrice.shouldHave(Condition.text(expectedTotalPriceText));
    }
    // проверка, что товар присутствует в корзине
    @Step
    public void productIsInCart(String productName) {
        actual.cartProductItems.findBy(Condition.text(productName)).shouldBe(Condition.visible);
    }
    // проверка количества товарных позиций в корзине
    @Step
    public void cartItemsCountIs(int expectedCount) {
        actual.cartProductItems.shouldHave(CollectionCondition.size(expectedCount));
    }
    // проверка количества конкретного товара в корзине
    @Step
    public void productQuantityIs(String productName, int expectedCount) {
        SelenideElement productCard = actual.cartProductItems.findBy(Condition.text(productName));
        SelenideElement productQuantity = productCard.$x(".//div[@class='qty-controls']/span");
        String expectedCountText = String.valueOf(expectedCount);
        productQuantity.shouldHave(Condition.text(expectedCountText));
    }
    // проверка уведомления (текст и видимость)
    @Step
    public void orderNotificationIs(String expectedText) {
        actual.toastNotifications.findBy(Condition.text(expectedText)).shouldBe(Condition.visible);
    }
}

