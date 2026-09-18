package org.example.pages;

import com.codeborne.selenide.Condition;
import org.assertj.core.api.AbstractAssert;

public class AdminPageAssert extends AbstractAssert<AdminPageAssert, AdminPage> {
    // конструктор
    public AdminPageAssert(AdminPage adminPage) {
        super(adminPage,AdminPageAssert.class);// передаем родительскому AbstractAssert
    }
    // проверка toast-а после создания товара (видимость)
    public void productAddedNotificationIs(String expectedText) {
        actual.toastNotifications.findBy(Condition.text(expectedText)).shouldBe(Condition.visible);
}
    // проверка toast-а после редактирования товара (видимость)
    public void productEditNotificationIs(String expectedText) {
        actual.toastNotifications.findBy(Condition.text(expectedText)).shouldBe(Condition.visible);
    }
}
