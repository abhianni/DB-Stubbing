package com.wm.services.http;

/* 
    @author Shyam Gupta
    @team   Hotels
*/


import com.wm.constants.HTTP_METHOD;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Service;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;

@Service
public class HttpService {

    public RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder();
    }

    public RequestSpecification getRequestSpecification(String endPoint, Object requestBody, ContentType contentType) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(endPoint);
        requestSpecBuilder.setContentType(contentType);
        requestSpecBuilder.setBody(requestBody);
        return requestSpecBuilder.build();
    }

    public RequestSpecification     getRequestSpecificationWithRequestBody(String baseUri, String basePath, Object requestBody, ContentType contentType) {
        RequestSpecBuilder requestSpecBuilder = getRequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseUri);
        requestSpecBuilder.setBasePath(basePath);
        requestSpecBuilder.setContentType(contentType);
        requestSpecBuilder.setBody(requestBody);
        return requestSpecBuilder.build();
    }

    public RequestSpecification getRequestSpecificationWithRequestBody(String baseUri, String basePath, Object requestBody, Map<String, String> pathParams, ContentType contentType) {
        RequestSpecBuilder requestSpecBuilder = getRequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseUri);
        requestSpecBuilder.setBasePath(basePath);
        requestSpecBuilder.setContentType(contentType);
        requestSpecBuilder.setBody(requestBody);
        requestSpecBuilder.addPathParams(pathParams);
        return requestSpecBuilder.build();
    }

    public RequestSpecification getRequestSpecificationWithOutRequestBody(String baseUri, String basePath, ContentType contentType) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseUri);
        requestSpecBuilder.setBasePath(basePath);
        requestSpecBuilder.setContentType(contentType);
        return requestSpecBuilder.build();
    }

    public RequestSpecification getRequestSpecificationWithOutRequestBody(String baseUri, String basePath, Map<String, String> pathParams, ContentType contentType) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(baseUri);
        requestSpecBuilder.setBasePath(basePath);
        requestSpecBuilder.addPathParams(pathParams);
        requestSpecBuilder.setContentType(contentType);
        return requestSpecBuilder.build();
    }

    public RequestSpecification getRequestSpecification(String endPoint, Object requestBody, Map<String, String> headers) {
        RequestSpecBuilder requestSpecBuilder = getRequestSpecBuilder();
        requestSpecBuilder.setBaseUri(endPoint);
        requestSpecBuilder.addHeaders(headers);
        requestSpecBuilder.setBody(requestBody);
        return requestSpecBuilder.build();
    }

    public RequestSpecification getRequestSpecification(String endPoint, Object requestBody, Map<String, String> queryParams, Map<String, String> headers) {
        RequestSpecBuilder requestSpecBuilder = getRequestSpecBuilder();
        requestSpecBuilder.setBaseUri(endPoint);
        requestSpecBuilder.addQueryParams(queryParams);
        requestSpecBuilder.setBody(requestBody);
        return requestSpecBuilder.build();
    }

    public Response executeAndGetResponse(HTTP_METHOD httpMethod, RequestSpecification specification) {
        return getResponse(httpMethod, specification);
    }

    public Response executeAndGetResponse(HTTP_METHOD httpMethod, String endPoint, Object payLoad, ContentType contentType) {
        return getResponse(httpMethod, getRequestSpecification(endPoint, payLoad, contentType));
    }

    public Response executeAndGetResponse(HTTP_METHOD httpMethod, String endPoint, Object payLoad, Map<String, String> queryParams, Map<String, String> headers) {
        return getResponse(httpMethod, getRequestSpecification(endPoint, payLoad, queryParams, headers));
    }

    public Response executeAndGetResponse(HTTP_METHOD httpMethod, RequestSpecBuilder requestSpecBuilder) {
        return getResponse(httpMethod, requestSpecBuilder.build());
    }

    private Response getResponse(HTTP_METHOD httpMethod, RequestSpecification specification){
        switch (httpMethod) {
            case POST:
                return given().spec(specification).when().post();
            case GET:
                return given().spec(specification).when().get();
            case PUT:
                return given().spec(specification).when().put();
            case DELETE:
                return given().spec(specification).when().delete();
            case PATCH:
                return given().spec(specification).when().patch();
            default:
                return null;
        }
    }
}
