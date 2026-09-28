package org.example;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

//базовый запрос без авторизации и возврат RequestSpecification
public class RestApiBuilder {
    public RequestSpecification buildRequest(){
        return given()
                .filter(new AllureRestAssured())
                .baseUri(ConfigProvider.config.testApiUrl());

    }
    //базовый запрос с авторизацией и возврат RequestSpecification
    public RequestSpecification buildRequestWithAuth(){
        return buildRequest()
                .auth()
                .basic(ConfigProvider.config.testLoginAdmin(),ConfigProvider.config.testPasswordAdmin());
    }
}
