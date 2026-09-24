package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.qameta.allure.restassured.AllureRestAssured;

import static io.restassured.RestAssured.given;

public class GoodsApi {
    @Step
    public Response postGoods(String requestBodyAddGoods){
       RequestSpecification requestSpecForAdd = given()
                .filter(new AllureRestAssured())
                .baseUri(ConfigProvider.config.testApiUrl())
                .auth()
                .basic(ConfigProvider.config.testLoginAdmin(),ConfigProvider.config.testPasswordAdmin())
                .contentType("application/json")
                .body(requestBodyAddGoods);

        Response response = requestSpecForAdd.when()
                .post("/goods/add");
        return response;
    }
    @Step
    public Response getGoods(int page, int size){
        RequestSpecification requestSpec = given()
                .filter(new AllureRestAssured())
                .baseUri(ConfigProvider.config.testApiUrl())
                .queryParam("page", page)
                .queryParam("size", size);
        Response response =  requestSpec.when()
                .get("/goods/list");
        return response;
    }
    @Step
    public Response getGoodsById(int id){
        RequestSpecification requestSpec = given()
                .filter(new AllureRestAssured())
                .baseUri(ConfigProvider.config.testApiUrl())
                .auth()
                .basic(ConfigProvider.config.testLoginAdmin(),ConfigProvider.config.testPasswordAdmin())
                .pathParam("id", id);
        Response response = requestSpec.when()
                .get("/goods/{id}");
        return response;
    }
    @Step
    public Response deleteGoods(int id){
        RequestSpecification requestSpecForDelete = given()
                .filter(new AllureRestAssured())
                .baseUri(ConfigProvider.config.testApiUrl())
                .auth()
                .basic(ConfigProvider.config.testLoginAdmin(),ConfigProvider.config.testPasswordAdmin())
                .pathParam("id", id);
        Response response = requestSpecForDelete.when()
                .delete("/goods/{id}");
        return response;
    }
    @Step
    public Response patchGoods(String requestBodyPatchGoods, int id){
        RequestSpecification requestSpecForPatch = given()
                .filter(new AllureRestAssured())
                .baseUri(ConfigProvider.config.testApiUrl())
                .auth()
                .basic(ConfigProvider.config.testLoginAdmin(),ConfigProvider.config.testPasswordAdmin())
                .contentType("application/json")
                .body(requestBodyPatchGoods)
                .pathParam("id", id);
        Response response = requestSpecForPatch.when()
                .patch("/goods/{id}");
        return response;
    }
}
