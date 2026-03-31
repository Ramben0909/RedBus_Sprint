package petstore.base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class BaseTestAP {

    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setUp() {
        // Set base URI once
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Basic request setup
        requestSpec = given()
                .contentType("application/json")
                .accept("application/json");

        System.out.println("Setup done for: " + this.getClass().getSimpleName());
    }
}