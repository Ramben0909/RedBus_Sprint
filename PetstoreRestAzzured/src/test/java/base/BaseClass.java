package base;

import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeClass;

public class BaseClass {

    public static String username = "apiautoUser";

    @BeforeClass
    public void setup() {
        baseURI = "https://petstore.swagger.io/v2";
    }
}