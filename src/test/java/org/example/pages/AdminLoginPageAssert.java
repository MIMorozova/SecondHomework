package org.example.pages;

import com.codeborne.selenide.Condition;
import org.assertj.core.api.AbstractAssert;

public class AdminLoginPageAssert extends AbstractAssert<AdminLoginPageAssert, AdminLoginPage> {
        // конструктор
        public AdminLoginPageAssert(AdminLoginPage adminLoginPage) {
            super(adminLoginPage, AdminLoginPageAssert.class);// передаем родительскому AbstractAssert
        }
        // проверка видимости
    public void loginFieldIsVisible() {
        actual.loginField.shouldBe(Condition.visible);
    }
    public void passwordFieldIsVisible() {
        actual.passwordField.shouldBe(Condition.visible);
    }
    public void loginButtonIsVisible(){
            actual.loginButton.shouldBe(Condition.visible);
    }
    // проверка текста
    public void loginValueIs(String expectedLogin){
        actual.loginField.shouldHave(Condition.value(expectedLogin));
    }
    public void passwordValueIs(String expectedPassword){
        actual.passwordField.shouldHave(Condition.value(expectedPassword));
    }
}
