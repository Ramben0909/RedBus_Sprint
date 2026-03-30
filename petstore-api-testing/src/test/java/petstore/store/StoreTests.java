package petstore.store;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import petstore.model.Order;


public class StoreTests {
    
    @BeforeClass
    public void setup() {
        baseURI = "https://petstore.swagger.io/v2";
    }

    // 1. POST: Place an order
    @Test(priority = 1)
    public void testPlaceOrder() {
        Order order = new Order();
        order.setId(101);
        order.setPetId(1);
        order.setQuantity(2);
        order.setStatus("placed");
        order.setComplete(true);

        given()
            .contentType("application/json")
            .body(order)
        .when()
            .post("/store/order")
        .then()
            .statusCode(200)
            .body("id", equalTo(101))
            .log().all();
    }

    // 2. GET: Get order by ID
    @Test(priority = 2)
    public void testGetOrderById() {
        when()
            .get("/store/order/101")
        .then()
            .statusCode(200)
            .body("status", equalTo("placed"))
            .log().all();
    }

    // 3. GET: Get Inventory (Alternative for PUT as Store has no direct Update Order endpoint)
    @Test(priority = 3)
    public void testGetInventory() {
        when()
            .get("/store/inventory")
        .then()
            .statusCode(200)
            .log().all();
    }

    // 4. DELETE: Delete order by ID
    @Test(priority = 4)
    public void testDeleteOrder() {
        when()
            .delete("/store/order/101")
        .then()
            .statusCode(200)
            .log().all();
    }
}