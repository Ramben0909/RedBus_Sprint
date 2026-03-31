package petstore.api;

import io.restassured.response.Response;
import petstore.models.Order;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class StoreApi {

    public Response placeOrder(Order order) {
        return given()
                .contentType("application/json")
                .body(order)
               .when()
                .post("/store/order");
    }

    public Response getOrderById(int orderId) {
        return when()
                .get("/store/order/" + orderId);
    }

    public Response getInventory() {
        return when()
                .get("/store/inventory");
    }

    public Response deleteOrder(int orderId) {
        return when()
                .delete("/store/order/" + orderId);
    }
}