package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class ApiAssert {
    @Step
    // проверка статус-кода
    public void statusCode(Response response, int expectedCode){
        response.then()
       .statusCode(expectedCode);
    }
    @Step
    // проверка body
    public void checkBody(Response response, String path, Object expectedValue){
        response.then()
        .body(path, equalTo(expectedValue));
    }
    @Step
    // проверка значения в списке
    public void checkBodyHasItem(Response response, String path, Object expectedValue){
        response.then()
                .body(path, hasItem(expectedValue));
    }
    @Step
    // проверка asserts
    public void checkValueIsNotNull(Object actualValue){
        assertThat(actualValue)
                .isNotNull();
    }
    @Step
    public void checkBodyIsEmpty(Response response,String path){
        response.then()
        .body(path, empty());
    }
    @Step
    public void checkListContains(List<String> actualValues, String expectedValue){
        assertThat(actualValues)
                .as("Проверка добавления %s в список товаров", expectedValue)
                .contains(expectedValue);
    }
    @Step
    public void checkValueEquals(Float actualPrice, Float expectedPrice){
        assertThat(actualPrice)
                .isEqualTo(expectedPrice);
    }
}
