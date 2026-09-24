package org.example;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTask3Test {
    GoodsApi goodsApi = new GoodsApi();
    ApiAssert apiAssert = new ApiAssert();
    TestDataGenerator dataGenerator = new TestDataGenerator();
    Integer productId;

    @BeforeEach
    public void openBrowserTest() {
        open("http://localhost:8080/");
    }

    @AfterEach
    public void closeBrowserTest(){
            if(productId != null) {
                Response deleteGoods = goodsApi.deleteGoods(productId);
                apiAssert.statusCode(deleteGoods,200);
    }
        closeWebDriver();
    }

    @Test
    //3.1. Добавить три единицы товара в корзину и оплатить их (общая стоимость не должна превышать 300 рублей). Проверить уведомление об обработке заказа.
    public void addThreeUnitsOfProductTest() {
        $x("//a[@href='/admin']").click(); //находим SelenideElement + click
        //Авторизация
        $x("//input[@id='username']").setValue("admin");
        $x("//input[@id='password']").setValue("secret123");
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

        $x("//div[@class='product-card'][@data-name='" + productName + "'][@data-price='" + productPriceText + "']/h4").shouldHave(text(productName));
        $x("//div[@class='product-card'][@data-name='" + productName + "']/div[contains(@style,'var(--primary)')]").shouldHave(text(productPriceText));
        $x("//div[@class='product-card'][@data-name='" + productName + "']//div[@class='qty-controls']/button[@class='qty-btn'][@data-step='1']").click();
        $x("//div[@class='product-card'][@data-name='" + productName + "']//div[@class='qty-controls']/button[@class='qty-btn'][@data-step='1']").click();
        //проверка кол-ва товаров
        $x("//div[@class='product-card'][@data-name='" + productName + "']//div[@class='qty-controls']/input[@class='qty-input']").shouldHave(value("3"));
        // добавляем товар в корзину
        $x("//button[@class='btn'][@data-name='" + productName + "']").click();
        $x("//div[@id='toast-container']/div[@class='toast'][text()='" + productName + " (3 шт.) добавлен в корзину']").shouldHave(exactText(productName + " (3 шт.) добавлен в корзину"));
        $x("//button[@id='open-cart-btn']").click();
        $x("//div[@class='cart-item']//b[text()='" + productName + "']").shouldBe(visible);
        //проверка что товар добавлен в корзину
        $x("//div[@id='cart-items']/div[@class='cart-item'][.//b[text()='" + productName + "']]//span[text()='3']").shouldHave(exactText("3"));
        // проверяем сумму
        Integer expectedPrice = productPrice * 3;
        String expectedTotalPrice = String.valueOf(expectedPrice);
        $x("//span[@id='total-price']").shouldHave(exactText(expectedTotalPrice)); //проверка суммы
        $x("//button[@id='makeOrder'][@class='btn']").click(); // оформление заказа
        $x("//div[@id='toast-container']/div[@class='toast'][text()='Заказ принят в обработку!']").shouldHave(exactText("Заказ принят в обработку!"));
        $x("//div[@class='header']/h1[@id='main-title']").shouldHave(exactText("Заказ успешно оформлен!")); // проверка итогового статуса заказа
    }

    // helper
    private void createProduct(String productName, int productPrice){
        String productPriceText = String.valueOf(productPrice);
        $x("//input[@id='n-name']").setValue(productName);
        $x("//input[@id='n-price']").setValue(productPriceText);
        $x("//button[@id='add-btn']").click();
        $x("//input[@value='" + productName + "']").shouldBe(visible);
    }

    //3.2. Добавить в корзину несколько разных товаров и проверить, что общая цена в корзине считается корректно.
    @Test
    public void addProductsTest(){
        // Авторизация
        $x("//a[@href='/admin']").click();
        $x("//input[@id='username']").setValue("admin");
        $x("//input[@id='password']").setValue("secret123");
        $x("//button[@class='primary']").click();
        // Генерация данных
        String productName1 = dataGenerator.generateName() + "_1";
        Integer productPrice1 = dataGenerator.generatePrice();
        String productName2 = dataGenerator.generateName() + "_2";
        Integer productPrice2 = dataGenerator.generatePrice();
        String productName3 = dataGenerator.generateName() + "_3";
        Integer productPrice3 = dataGenerator.generatePrice();
        createProduct(productName1, productPrice1);
        createProduct(productName2, productPrice2);
        createProduct(productName3, productPrice3);
        $x("//a[@href='/']").click(); // Возврат на сайт
        // Добавление товаров в корзину
        $x("//button[@class='btn'][@data-name='" + productName1 + "']").click();
        $x("//div[@id='toast-container']/div[@class='toast'][text()='" + productName1 + " (1 шт.) добавлен в корзину']").shouldHave(exactText(productName1 + " (1 шт.) добавлен в корзину"));
        $x("//button[@class='btn'][@data-name='" + productName2 + "']").click();
        $x("//div[@id='toast-container']/div[@class='toast'][text()='" + productName2 + " (1 шт.) добавлен в корзину']").shouldHave(exactText(productName2 + " (1 шт.) добавлен в корзину"));
        $x("//button[@class='btn'][@data-name='" + productName3 + "']").click();
        $x("//div[@id='toast-container']/div[@class='toast'][text()='" + productName3 + " (1 шт.) добавлен в корзину']").shouldHave(exactText(productName3 + " (1 шт.) добавлен в корзину"));
        $x("//button[@id='open-cart-btn']").click(); // открываем корзину
        $x("//div[@class='cart-item']//b[text()='" + productName1 + "']").shouldBe(visible); // товар 1 отображается
        $x("//div[@class='cart-item']//b[text()='" + productName2 + "']").shouldBe(visible); // товар 2 отображается
        $x("//div[@class='cart-item']//b[text()='" + productName3 + "']").shouldBe(visible); // товар 3 отображается
        // Проверка итоговой суммы
        Integer expectedTotalPrice = productPrice1 + productPrice2 + productPrice3;
        String expectedTotalPriceText = String.valueOf(expectedTotalPrice);
        $x("//span[@id='total-price']").shouldHave(exactText(expectedTotalPriceText));
    }

    // 3.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.
    @Test
    public void addProductAdminTest(){
        // Авторизация
        $x("//a[@href='/admin']").click();
        $x("//input[@id='username']").setValue("admin");
        $x("//input[@id='password']").setValue("secret123");
        $x("//button[@class='primary']").click();
        // Генерация товара
        String productName = dataGenerator.generateName();
        Integer productPrice = dataGenerator.generatePrice();
        String productPriceText = String.valueOf(productPrice);
        // добавляем товар
        $x("//input[@id='n-name']").setValue(productName);
        $x("//input[@id='n-price']").setValue(productPriceText);
        $x("//button[@id='add-btn']").click();
        $x("//div[@id='toast-container']/div[@class='toast'][text()='Товар успешно добавлен!']").shouldHave(exactText("Товар успешно добавлен!"));
    }

    //3.4. Войти в админку и отредактировать товар. Выйти на список товаров и проверить, что изменения применились.
    //создание товара через API
    private Integer createProductViaApi(String productName, int productPrice){
        String requestBodyAddGoods = """
        {
            "name": "%s",
            "price": %d
        }
        """.formatted(productName, productPrice);
        Response postAddProductRequest = goodsApi.postGoods(requestBodyAddGoods);
        apiAssert.statusCode(postAddProductRequest,200);
        Integer productId = postAddProductRequest.path("data.id");
        return productId;
    }

    @Test
    public void editProductTest(){
    // подготовка/создание товара
        // Генерация товара
        String productName = dataGenerator.generateName();
        Integer productPrice = dataGenerator.generatePrice();
        productId = createProductViaApi(productName, productPrice);
        // Авторизация
        $x("//a[@href='/admin']").click();
        $x("//input[@id='username']").setValue("admin");
        $x("//input[@id='password']").setValue("secret123");
        $x("//button[@class='primary']").click();
        String editedProductName = dataGenerator.generateName();
        Integer editedProductPrice = dataGenerator.generatePrice();
        String editedProductPriceText = String.valueOf(editedProductPrice);
        // меняем название и цену
        $x("//input[@id='nm-" + productId + "']").setValue(editedProductName);
        $x("//input[@id='pr-" + productId + "']").setValue(editedProductPriceText);
        $x("//button[@class='btn btn-upd'][@data-id='" + productId + "']").click();
        $x("//div[@id='toast-container']/div[@class='toast'][text()='Товар #" + productId + " обновлен']").shouldHave(exactText("Товар #" + productId + " обновлен"));
        $x("//a[@href='/']").click();
        // проверяем сохранение нового названия и цены
        $x("//div[@class='product-card'][@data-name='" + editedProductName + "'][@data-price='" + editedProductPriceText + "']/h4").shouldHave(text(editedProductName));
        $x("//div[@class='product-card'][@data-name='" + editedProductName + "']/div[contains(@style,'var(--primary)')]").shouldHave(text(editedProductPriceText));
    }
}
