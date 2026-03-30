package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class ApiUtilsAP {

    // Endpoints

    public static final String USER_ENDPOINT              = "/user";
    public static final String USER_BY_USERNAME_ENDPOINT  = "/user/{username}";
    public static final String USER_LOGIN_ENDPOINT        = "/user/login";
    public static final String USER_LOGOUT_ENDPOINT       = "/user/logout";
    public static final String USER_CREATE_WITH_ARRAY     = "/user/createWithArray";
    public static final String USER_CREATE_WITH_LIST      = "/user/createWithList";

    // Generic HTTP helpers

    
     // HTTP POST with a body object.
     
    public static Response post(RequestSpecification spec, String endpoint, Object body) {
        return given()
                .spec(spec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    
     // HTTP GET by path param.
     
    public static Response getByPathParam(RequestSpecification spec, String endpoint,
                                          String paramName, String paramValue) {
        return given()
                .spec(spec)
                .pathParam(paramName, paramValue)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    
     // HTTP GET with query params (used for login).
     
    public static Response getWithQueryParams(RequestSpecification spec, String endpoint,
                                               String username, String password) {
        return given()
                .spec(spec)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    
     // HTTP GET — no params.
     
    public static Response get(RequestSpecification spec, String endpoint) {
        return given()
                .spec(spec)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    
    // HTTP PUT with body, targeting a path param.
     
    public static Response put(RequestSpecification spec, String endpoint,
                                String paramName, String paramValue, Object body) {
        return given()
                .spec(spec)
                .pathParam(paramName, paramValue)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    
     //HTTP DELETE by path param.
     
    public static Response delete(RequestSpecification spec, String endpoint,
                                   String paramName, String paramValue) {
        return given()
                .spec(spec)
                .pathParam(paramName, paramValue)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    // Response helpers

    
     //Extract status code from a response.
     
    public static int statusCode(Response response) {
        return response.getStatusCode();
    }

    
     //Extract a JSON field as String.
     
    public static String jsonPath(Response response, String path) {
        return response.jsonPath().getString(path);
    }

     //Extract a JSON field as Integer.
     
    public static int jsonPathInt(Response response, String path) {
        return response.jsonPath().getInt(path);
    }

    
     //Return the full response body as String.
     
    public static String bodyAsString(Response response) {
        return response.getBody().asString();
    }
}
