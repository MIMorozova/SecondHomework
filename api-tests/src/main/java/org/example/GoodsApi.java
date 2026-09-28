package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class GoodsApi {
    RestApiBuilder restApiBuilder = new RestApiBuilder();
    @Step
    public Response postGoods(String requestBodyAddGoods){
       RequestSpecification requestSpecForAdd = restApiBuilder.buildRequestWithAuth()
                .contentType("application/json")
                .body(requestBodyAddGoods);

        Response response = requestSpecForAdd.when()
                .post(GoodsApiEndpoints.ADD_GOODS);
        return response;
    }
    @Step
    public Response getGoods(int page, int size){
        RequestSpecification requestSpec = restApiBuilder.buildRequest()
                .queryParam("page", page)
                .queryParam("size", size);
        Response response =  requestSpec.when()
                .get(GoodsApiEndpoints.GOODS_LIST);
        return response;
    }
    @Step
    public Response getGoodsById(int id){
        RequestSpecification requestSpec = restApiBuilder.buildRequestWithAuth()
                .pathParam("id", id);
        Response response = requestSpec.when()
                .get(GoodsApiEndpoints.GOODS_BY_ID);
        return response;
    }
    @Step
    public Response deleteGoods(int id){
        RequestSpecification requestSpecForDelete = restApiBuilder.buildRequestWithAuth()
                .pathParam("id", id);
        Response response = requestSpecForDelete.when()
                .delete(GoodsApiEndpoints.GOODS_BY_ID);
        return response;
    }
    @Step
    public Response patchGoods(String requestBodyPatchGoods, int id){
        RequestSpecification requestSpecForPatch = restApiBuilder.buildRequestWithAuth()
                .contentType("application/json")
                .body(requestBodyPatchGoods)
                .pathParam("id", id);
        Response response = requestSpecForPatch.when()
                .patch(GoodsApiEndpoints.GOODS_BY_ID);
        return response;
    }
}
