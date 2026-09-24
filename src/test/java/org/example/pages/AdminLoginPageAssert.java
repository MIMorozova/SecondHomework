package org.example.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

public class AdminLoginPageAssert extends AbstractAssert<AdminLoginPageAssert, AdminLoginPage> {
        // конструктор
        public AdminLoginPageAssert(AdminLoginPage adminLoginPage) {
            super(adminLoginPage, AdminLoginPageAssert.class);// передаем родительскому AbstractAssert
        }
    // проверка видимости
    @Step
    public void loginFieldIsVisible() {
        actual.loginField.shouldBe(Condition.visible);
    }
    @Step
    public void passwordFieldIsVisible() {
            actual.passwordField.shouldBe(Condition.visible);
    }
    @Step
    public void loginButtonIsVisible(){
            actual.loginButton.shouldBe(Condition.visible);
    }
    // проверка текста
    @Step
    public void loginValueIs(String expectedLogin){
        actual.loginField.shouldHave(Condition.value(expectedLogin));
    }
    @Step
    public void passwordValueIs(String expectedPassword){
        actual.passwordField.shouldHave(Condition.value(expectedPassword));
    }
}
