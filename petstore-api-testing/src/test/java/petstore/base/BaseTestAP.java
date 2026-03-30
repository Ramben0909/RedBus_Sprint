package base;

import config.ConfigManagerAP;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;


public class BaseTestAP {

    protected static RequestSpecification requestSpec;

    @BeforeSuite
    public void globalSetup() {
        // Load base URI from config
        RestAssured.baseURI = ConfigManagerAP.get("base.url");

        // Build a reusable RequestSpecification
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigManagerAP.get("base.url"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        System.out.println("Base URI set to: " + ConfigManagerAP.get("base.url"));
    }

    @BeforeClass
    public void setUp() {
        System.out.println("Running test class: " + this.getClass().getSimpleName());
    }
}
