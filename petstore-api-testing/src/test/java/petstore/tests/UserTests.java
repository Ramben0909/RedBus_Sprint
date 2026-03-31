package petstore.tests;

import static io.restassured.RestAssured.*;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import petstore.base.BaseTest;
import petstore.models.User;
import petstore.utils.UserTestData;

@Epic("Petstore API")
@Feature("User Endpoints")
public class UserTests extends BaseTest {

    private String username = UserTestData.username;
    private User user;

    @BeforeClass
    public void initUser() {
        user = new User(101, username, "Apabrita", "Sarkar",
                "apa@test.com", "12345", "9999999999", 1);
    }

    @Test(priority = 1, description = "TC-U01: Create a single user")
    @Story("Create User")
    @Severity(SeverityLevel.BLOCKER)
    @Description("POST /user - creates a new user and expects HTTP 200")
    public void tc01_createUser() {
        Response res = given()
                .contentType("application/json")
                .body(user)
                .when().post("/user");

        res.then().log().all();
        Assert.assertEquals(res.statusCode(), 200, "Create user should return 200");
    }

    @Test(priority = 2, description = "TC-U02: Get user by username",
            dependsOnMethods = "tc01_createUser")
    @Story("Get User")
    @Severity(SeverityLevel.CRITICAL)
    @Description("GET /user/{username} - fetches the created user and verifies username")
    public void tc02_getUser() {
        Response res = given()
                .when().get("/user/" + username);

        res.then().log().all();
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.jsonPath().getString("username"), username);
    }

    @Test(priority = 3, description = "TC-U03: Login with valid credentials",
            dependsOnMethods = "tc01_createUser")
    @Story("Login / Logout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("GET /user/login - logs in with valid username and password")
    public void tc03_loginUser() {
        Response res = given()
                .queryParam("username", username)
                .queryParam("password", "12345")
                .when().get("/user/login");

        res.then().log().all();
        Assert.assertEquals(res.statusCode(), 200);
        System.out.println("Login Response: " + res.jsonPath().getString("message"));
    }

    @Test(priority = 4, description = "TC-U04: Logout user",
            dependsOnMethods = "tc03_loginUser")
    @Story("Login / Logout")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /user/logout - logs out the current session")
    public void tc04_logoutUser() {
        Response res = given()
                .when().get("/user/logout");

        res.then().log().all();
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test(priority = 5, description = "TC-U05: Create users with array")
    @Story("Bulk Create Users")
    @Severity(SeverityLevel.NORMAL)
    @Description("POST /user/createWithArray - creates multiple users using an array payload")
    public void tc05_createUsersWithArray() {
        User user1 = new User(201, "user1", "A", "One", "a1@test.com", "123", "1111111111", 1);
        User user2 = new User(202, "user2", "B", "Two", "b2@test.com", "123", "2222222222", 1);
        User[] users = {user1, user2};

        Response res = given()
                .contentType("application/json")
                .body(users)
                .when().post("/user/createWithArray");

        res.then().log().all();
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test(priority = 6, description = "TC-U06: Create users with list")
    @Story("Bulk Create Users")
    @Severity(SeverityLevel.NORMAL)
    @Description("POST /user/createWithList - creates multiple users using a list payload")
    public void tc06_createUsersWithList() {
        User user1 = new User(301, "listuser1", "C", "Three", "c3@test.com", "123", "3333333333", 1);
        User user2 = new User(302, "listuser2", "D", "Four",  "d4@test.com", "123", "4444444444", 1);
        java.util.List<User> userList = java.util.Arrays.asList(user1, user2);

        Response res = given()
                .contentType("application/json")
                .body(userList)
                .when().post("/user/createWithList");

        res.then().log().all();
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test(priority = 7, description = "TC-U07: Update existing user",
            dependsOnMethods = "tc02_getUser")
    @Story("Update User")
    @Severity(SeverityLevel.CRITICAL)
    @Description("PUT /user/{username} - updates the first name of an existing user")
    public void tc07_updateUser() {
        user.setFirstName("UpdatedApabrita");

        Response res = given()
                .contentType("application/json")
                .body(user)
                .when().put("/user/" + username);

        res.then().log().all();
        Assert.assertEquals(res.statusCode(), 200);
    }

    @Test(priority = 8, description = "TC-U08: Delete existing user",
            dependsOnMethods = "tc07_updateUser")
    @Story("Delete User")
    @Severity(SeverityLevel.CRITICAL)
    @Description("DELETE /user/{username} - deletes the user and expects HTTP 200")
    public void tc08_deleteUser() {
        Response res = given()
                .when().delete("/user/" + username);

        res.then().log().all();
        Assert.assertEquals(res.statusCode(), 200);
    }
}