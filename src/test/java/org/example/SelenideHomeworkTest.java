package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideHomeworkTest {
    TestDataGenerator dataGenerator = new TestDataGenerator();

    @BeforeEach
    public void openBrowserTest() {
        open("http://localhost:8080/");
    }

    @AfterEach
    public void closeBrowserTest() {
        closeWebDriver();
    }

    //2.1 Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
    @Test
    public void addProductTest() {
        $x("//a[@href='/admin']").click(); //находим SelenideElement + click
        //Авторизация
        $x("//input[@id='username']").setValue("admin");
        $x("//input[@id='password']").setValue("secret123");
        $x("//button[@class='primary']").click();
        //Генерация названия товара и цены
        String productName = dataGenerator.generateName();
        Integer productPrice = dataGenerator.generatePrice();
        String productPriceText = String.valueOf(productPrice);
        // добавляем товар
        $x("//input[@id='n-name']").setValue(productName);
        $x("//input[@id='n-price']").setValue(productPriceText);
        $x("//button[@id='add-btn']").click();
        $x("//input[@value='" + productName + "']").shouldBe(visible);// находим поле с созданным товаром и ждем, пока оно станет видимым
        $x("//a[@href='/']").click();

        $x("//div[@class='product-card'][@data-name='" + productName + "'][@data-price='" + productPriceText + "']/h4").shouldHave(text(productName));
        $x("//div[@class='product-card'][@data-name='" + productName + "']/div[contains(@style,'var(--primary)')]").shouldHave(text(productPriceText));
    }

    @Test
    public void addToBasketTest() {
        $x("//button[@class='btn'][@data-name='Стакан']").click();
        $x("//button[@id='open-cart-btn']").click();
        $x("//div[@class='cart-item']//b[text()='Стакан']").shouldBe(visible);
    }

    //2.3. Попытаться войти в админку с неверным логином и паролем.
    @Test
    public void failAuthorizeTest() {
        $x("//a[@class='btn-outline']").click();
        $x("//input[@id='username']").setValue("ADmin");
        $x("//input[@id='password']").setValue("Secret");
        $x("//button[@class='primary']").click();
        $x("//div[@class='alert alert-danger']").shouldHave(exactText("Неверные учетные данные пользователя"));
    }

    //2.4. Проверить сохранение товаров в корзине после обновления страницы.
    @Test
    public void refreshPageTest(){
        $x("//button[@class='btn'][@data-name='Стакан']").click();
        $x("//button[@id='open-cart-btn']").click();
        $x("//div[@class='cart-item']//b[text()='Стакан']").shouldHave(exactText("Стакан"));
        refresh();
        $x("//button[@id='open-cart-btn']").click();
        $x("//div[@class='cart-item']//b[text()='Стакан']").shouldHave(exactText("Стакан"));
        //2.4 падает из-за бага приложения — корзина не сохраняется после обновления страницы.
    }

    //2.5. Добавить в корзину товаров более чем на 300 рублей и нажать на кнопку «Оформить заказ». Проверить, что отображается JS Alert.
    @Test
    public void addProductMoreThen300Test() {
        $x("//a[@href='/admin']").click(); //находим SelenideElement + click
        //Авторизация
        $x("//input[@id='username']").setValue("admin");
        $x("//input[@id='password']").setValue("secret123");
        $x("//button[@class='primary']").click();
        //Генерация названия товара и цены
        String productName = dataGenerator.generateName();
        Integer productPrice = dataGenerator.generatePrice();
        String productPriceText = String.valueOf(productPrice);
        // добавляем товар
        $x("//input[@id='n-name']").setValue(productName);
        $x("//input[@id='n-price']").setValue(productPriceText);
        $x("//button[@id='add-btn']").click();
        $x("//input[@value='" + productName + "']").shouldBe(visible);// находим поле с созданным товаром и ждем, пока оно станет видимым
        $x("//a[@href='/']").click();
        $x("//div[@class='product-card'][@data-name='" + productName + "'][@data-price='" + productPriceText + "']/h4").shouldHave(text(productName));
        $x("//div[@class='product-card'][@data-name='" + productName + "']/div[contains(@style,'var(--primary)')]").shouldHave(text(productPriceText));
        $x("//div[@class='product-card'][@data-name='" + productName + "']//div[@class='qty-controls']/button[@class='qty-btn'][@data-step='1']").click();
        $x("//div[@class='product-card'][@data-name='" + productName + "']//div[@class='qty-controls']/button[@class='qty-btn'][@data-step='1']").click();
        $x("//div[@class='product-card'][@data-name='" + productName + "']//div[@class='qty-controls']/button[@class='qty-btn'][@data-step='1']").click();
        //проверка кол-ва товаров
        $x("//div[@class='product-card'][@data-name='" + productName + "']//div[@class='qty-controls']/input[@class='qty-input']").shouldHave(value("4"));
        // добавляем товар в корзину
        $x("//button[@class='btn'][@data-name='" + productName + "']").click();
        $x("//div[@id='toast-container']/div[@class='toast'][text()='" + productName + " (4 шт.) добавлен в корзину']").shouldHave(exactText(productName + " (4 шт.) добавлен в корзину"));
        $x("//button[@id='open-cart-btn']").click();
        $x("//div[@class='cart-item']//b[text()='" + productName + "']").shouldBe(visible);
        //проверка что товар добавлен в корзину
        $x("//div[@id='cart-items']/div[@class='cart-item'][.//b[text()='" + productName + "']]//span[text()='4']").shouldHave(exactText("4"));
        // проверяем сумму
        Integer expectedPrice = productPrice * 4;
        String expectedTotalPrice = String.valueOf(expectedPrice);
        $x("//span[@id='total-price']").shouldHave(exactText(expectedTotalPrice)); //проверка суммы
        $x("//button[@id='makeOrder'][@class='btn']").click(); // оформление заказа
        // проверка Alert
        Alert alert = switchTo().alert();
        String warningText = alert.getText();
        String expectedText = "[SmartShop]: Денег не хватает! Сумма " + expectedPrice + " ₽ превышает лимит 300 ₽.";
        assertThat(warningText)
                .as("Проверка текста Alert")
                .isEqualTo(expectedText);
        alert.accept();
    }
}
