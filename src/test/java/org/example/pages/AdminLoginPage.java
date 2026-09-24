package org.example.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class AdminLoginPage {
    // элементы страницы
    SelenideElement loginField = $x("//input[@id='username']");
    SelenideElement passwordField = $x("//input[@id='password']");
    SelenideElement loginButton = $x("//button[@class='primary']");
    // методы взаимодействия
    @Step
    public AdminLoginPage setLoginValue(String login){
        loginField.setValue(login);
        return this;
    }
    @Step
    public AdminLoginPage setPasswordValue(String password){
        passwordField.setValue(password);
        return this;
    }
    @Step
    public AdminLoginPage clickLoginButton(){
        loginButton.click();
        return this;
    }
    public AdminLoginPageAssert check() {
        return new AdminLoginPageAssert(this);
    }
    @Step
    public AdminPage loginInAdmin(String login, String password){
        setLoginValue(login);
        setPasswordValue(password);
        loginButton.click();
        return new AdminPage();
    }
}