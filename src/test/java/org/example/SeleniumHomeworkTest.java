package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumHomeworkTest {
    TestDataGenerator dataGenerator = new TestDataGenerator();
    WebDriver driver; // объявляем переменную
    @BeforeEach
    // перед каждым тестом создаем новый ChromeDriver
    public void openTest(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("http://localhost:8080/"); //выносим в BeforeEach тк все тесты стартуют из одной и той же точки
    }

    @AfterEach
    public void closeTest(){
        driver.quit();
    }

    @Test
    public void addProductTest(){
        //1.1 Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
        String productName = dataGenerator.generateName();
        Integer productPrice = dataGenerator.generatePrice();
        String productPriceText =  String.valueOf(productPrice);
        driver.findElement(By.xpath("//a[@href='/admin']")).click(); //находим WebElement + click
        //Авторизация
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret123");
        driver.findElement(By.xpath("//button[@class='primary']")).click();
        // добавляем товар
        driver.findElement(By.xpath("//input[@id='n-name']")).sendKeys(productName);
        driver.findElement(By.xpath("//input[@id='n-price']")).sendKeys(productPriceText);
        driver.findElement(By.xpath("//button[@id='add-btn']")).click();
        driver.findElement(By.xpath("//input[@value='" + productName + "']"));
        driver.findElement(By.xpath("//a[@href='/']")).click();

        // Находим товар и цену
        String actualName = driver.findElement(
                By.xpath("//div[@class='product-card'][@data-name='" + productName + "'][@data-price='" + productPriceText + "']/h4")
        ).getText();
        String actualPrice = driver.findElement(
                By.xpath("//div[@class='product-card'][@data-name='" + productName + "']/div[contains(@style,'var(--primary)')]")).getText();


        // Добавляем assert для наименования
        assertThat(actualName)
                .as("Проверка добавления товара")
                .isEqualTo(productName);
        // Добавляем assert для цены
        assertThat(actualPrice)
                .as("Проверка добавления стоимости товара")
                .isEqualTo(productPriceText + " ₽");

    }

    @Test
    public void addToBasketTest(){
        //1.2. Добавить товар в корзину и проверить, что он отображается.
        driver.findElement(By.xpath("//button[@class='btn'][@data-name='Стакан']")).click();
        driver.findElement(By.xpath("//button[@id='open-cart-btn']")).click();
        String actualName = driver.findElement(By.xpath("//div[@class='cart-item']//b[text()='Стакан']")).getText();
        // Добавляем assert для наименования
        assertThat(actualName)
                .as("Проверка добавления товара 'Стакан' в корзину")
                .isEqualTo("Стакан");
    }

    @Test
    public void failAuthorizeTest(){
        //1.3. Попытаться войти в админку с неверным логином и паролем.
        driver.findElement(By.xpath("//a[@class='btn-outline']")).click();
        driver.findElement(By.xpath("//input[@id='username']")).sendKeys("ADmin");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Secret");
        driver.findElement(By.xpath("//button[@class='primary']")).click();
        String actualErrorText = driver.findElement(By.xpath("//div[@class='alert alert-danger']")).getText();
        assertThat(actualErrorText)
                .as("Проверка вывода уведомления о неуспешной авторизации через админку")
                .isEqualTo("Неверные учетные данные пользователя");
    }

    @Test
    public void refreshPageTest(){
        //1.4. Проверить сохранение товаров в корзине после обновления страницы.
        driver.findElement(By.xpath("//button[@class='btn'][@data-name='Стакан']")).click();
        driver.findElement(By.xpath("//button[@id='open-cart-btn']")).click();
        String actualName = driver.findElement(By.xpath("//div[@class='cart-item']//b[text()='Стакан']")).getText();
        assertThat(actualName)
                .as("Проверка сохранения товара 'Стакан' в корзине до обновления страницы")
                .isEqualTo("Стакан");
        driver.navigate().refresh();
        driver.findElement(By.xpath("//button[@id='open-cart-btn']")).click();
        List<WebElement> findElements = driver.findElements(By.xpath("//div[@class='cart-item']//b[text()='Стакан']"));
        assertThat(findElements)
                .as("Проверка сохранения товара 'Стакан' в корзине после обновления страницы")
                .isNotEmpty();
    }
}
