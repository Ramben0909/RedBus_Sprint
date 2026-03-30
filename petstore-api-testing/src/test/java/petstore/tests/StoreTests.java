package petstore.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import petstore.api.StoreApi;
import petstore.base.BaseTest;
import petstore.models.Order;

public class StoreTests extends BaseTest {

    private StoreApi storeApi;

    @BeforeClass
    public void init() {
        storeApi = new StoreApi();
    }

    @Test(priority = 1)
    public void testPlaceOrder() {
        Order order = new Order();
        order.setId(101);
        order.setPetId(1);
        order.setQuantity(2);
        order.setStatus("placed");
        order.setComplete(true);

        Response response = storeApi.placeOrder(order);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch");
        Assert.assertEquals(response.jsonPath().getInt("id"), 101, "Order ID mismatch");
        Assert.assertEquals(response.jsonPath().getString("status"), "placed", "Order status mismatch");
    }

    @Test(priority = 2)
    public void testGetOrderById() {
        Response response = storeApi.getOrderById(101);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch");
        Assert.assertEquals(response.jsonPath().getInt("id"), 101, "Order ID mismatch");
        Assert.assertEquals(response.jsonPath().getString("status"), "placed", "Order status mismatch");
    }

    @Test(priority = 3)
    public void testGetInventory() {
        Response response = storeApi.getInventory();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch");
        Assert.assertNotNull(response.jsonPath().getMap(""), "Inventory response should not be empty");
    }

    @Test(priority = 4)
    public void testDeleteOrder() {
        Response response = storeApi.deleteOrder(101);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch");
    }
}