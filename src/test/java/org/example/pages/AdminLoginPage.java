package org.example.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AdminLoginPage {
    // элементы страницы
    SelenideElement loginField = $x("//input[@id='username']");
    SelenideElement passwordField = $x("//input[@id='password']");
    SelenideElement loginButton = $x("//button[@class='primary']");
    // методы взаимодействия
    public AdminLoginPage setLoginValue(String login){
        loginField.setValue(login);
        return this;
    }
    public AdminLoginPage setPasswordValue(String password){
        passwordField.setValue(password);
        return this;
    }
    public AdminLoginPage clickLoginButton(){
        loginButton.click();
        return this;
    }
    public AdminLoginPageAssert check() {
        return new AdminLoginPageAssert(this);
    }
}