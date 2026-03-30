package tests;

import base.BaseTestAP;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import pojo.UserAP;
import utils.ApiUtilsAP;
import utils.JSValidatorAP;

import java.util.Arrays;
import java.util.List;

public class UserTestsAP extends BaseTestAP {

    private static final String TEST_USERNAME = "optimus prime";
    private UserAP testUser;

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        testUser = UserAP.defaultTestUser();
        System.out.println("Test user prepared: " + testUser);
    }

    @Test(priority = 1, description = "TC01 - Create a single user via POST /user")
    public void tc01_createUser() {
        Response response = ApiUtilsAP.post(requestSpec, ApiUtilsAP.USER_ENDPOINT, testUser);

        Assert.assertEquals(response.getStatusCode(), 200);

        String body = ApiUtilsAP.bodyAsString(response);

        JSValidatorAP.assertValid(body,
                "var obj = JSON.parse(responseBody);" +
                        "obj.code === 200 && obj.type === 'unknown';",
                "Response code=200 and type=unknown");

        JSValidatorAP.assertFieldEquals(body, "type", "unknown");
        JSValidatorAP.assertFieldNotEmpty(body, "message");

        System.out.println("TC01 PASSED - User created. Response: " + body);
    }

    @Test(priority = 2, description = "TC02 - Create multiple users with array")
    public void tc02_createUsersWithArray() {
        List<UserAP> users = Arrays.asList(
                new UserAP(100002L, "shockwave", "shock", "wave",
                        "sw@petstore.com", "Pass@001", "1111111111", 1),
                new UserAP(100003L, "soundwave", "sound", "wave",
                        "sw1@petstore.com", "Pass@002", "2222222222", 1)
        );

        Response response = ApiUtilsAP.post(requestSpec, ApiUtilsAP.USER_CREATE_WITH_ARRAY, users);

        Assert.assertEquals(response.getStatusCode(), 200);

        String body = ApiUtilsAP.bodyAsString(response);

        JSValidatorAP.assertValid(body,
                "var obj = JSON.parse(responseBody); obj.code === 200;",
                "Batch create with array returns code 200");

        System.out.println("TC02 PASSED - Users created with array.");
    }

    @Test(priority = 3, description = "TC03 - Create multiple users with list")
    public void tc03_createUsersWithList() {
        List<UserAP> users = Arrays.asList(
                new UserAP(100004L, "wheeljack", "wheel", "jack",
                        "wj@petstore.com", "Pass@003", "3333333333", 1),
                new UserAP(100005L, "bulkhead", "bulk", "head",
                        "bh@petstore.com", "Pass@004", "4444444444", 1)
        );

        Response response = ApiUtilsAP.post(requestSpec, ApiUtilsAP.USER_CREATE_WITH_LIST, users);

        Assert.assertEquals(response.getStatusCode(), 200);

        String body = ApiUtilsAP.bodyAsString(response);

        JSValidatorAP.assertValid(body,
                "var obj = JSON.parse(responseBody); obj.code === 200;",
                "Batch create with list returns code 200");

        System.out.println("TC03 PASSED - Users created with list.");
    }

    @Test(priority = 4, description = "TC04 - Login with valid credentials",
            dependsOnMethods = "tc01_createUser")
    public void tc04_loginUser_validCredentials() {
        Response response = ApiUtilsAP.getWithQueryParams(
                requestSpec,
                ApiUtilsAP.USER_LOGIN_ENDPOINT,
                testUser.getUsername(),
                testUser.getPassword()
        );

        Assert.assertEquals(response.getStatusCode(), 200);

        String body = ApiUtilsAP.bodyAsString(response);

        JSValidatorAP.assertMessageContains(body, "logged in user session");

        JSValidatorAP.assertValid(body,
                "responseBody.length > 0;",
                "Login response body should not be empty");

        System.out.println("TC04 PASSED - Login successful. Response: " + body);
    }

    @Test(priority = 5, description = "TC05 - Login with invalid credentials")
    public void tc05_loginUser_invalidCredentials() {
        Response response = ApiUtilsAP.getWithQueryParams(
                requestSpec,
                ApiUtilsAP.USER_LOGIN_ENDPOINT,
                "invalid_user_xyz",
                "wrong_password_123"
        );

        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 200);

        System.out.println("TC05 PASSED - Invalid login handled. Status: " + statusCode);
    }

    @Test(priority = 6, description = "TC06 - Get user by valid username",
            dependsOnMethods = "tc01_createUser")
    public void tc06_getUserByName_exists() {
        Response response = ApiUtilsAP.getByPathParam(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", TEST_USERNAME
        );

        Assert.assertEquals(response.getStatusCode(), 200);

        String body = ApiUtilsAP.bodyAsString(response);

        JSValidatorAP.assertFieldEquals(body, "username", TEST_USERNAME);
        JSValidatorAP.assertFieldEquals(body, "firstName", testUser.getFirstName());
        JSValidatorAP.assertFieldEquals(body, "lastName", testUser.getLastName());
        JSValidatorAP.assertValidEmail(body, "email");
        JSValidatorAP.assertNumericFieldPositive(body, "id");

        JSValidatorAP.assertValid(body,
                "var obj = JSON.parse(responseBody);" +
                        "obj.userStatus === 0 || obj.userStatus === 1;",
                "userStatus should be 0 or 1");

        UserAP fetched = response.as(UserAP.class);
        Assert.assertEquals(fetched.getUsername(), TEST_USERNAME);
        Assert.assertEquals(fetched.getEmail(), testUser.getEmail());

        System.out.println("TC06 PASSED - User fetched: " + fetched);
    }

    @Test(priority = 7, description = "TC07 - Get user by non-existent username returns 404")
    public void tc07_getUserByName_notFound() {
        Response response = ApiUtilsAP.getByPathParam(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", "arcee"
        );

        Assert.assertEquals(response.getStatusCode(), 404);

        String body = ApiUtilsAP.bodyAsString(response);

        JSValidatorAP.assertValid(body,
                "var obj = JSON.parse(responseBody); obj.code === 1 || obj.type === 'error';",
                "Error response validation");

        System.out.println("TC07 PASSED - 404 for unknown user.");
    }

    @Test(priority = 8, description = "TC08 - Update user",
            dependsOnMethods = "tc01_createUser")
    public void tc08_updateUser() {
        UserAP updatedUser = new UserAP()
                .withId(testUser.getId())
                .withUsername(TEST_USERNAME)
                .withFirstName("Updated")
                .withLastName("Name")
                .withEmail("updated.email@petstore.com")
                .withPassword("NewPass@5678")
                .withPhone("0000000000")
                .withUserStatus(1);

        Response response = ApiUtilsAP.put(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", TEST_USERNAME,
                updatedUser
        );

        Assert.assertEquals(response.getStatusCode(), 200);

        String body = ApiUtilsAP.bodyAsString(response);

        JSValidatorAP.assertValid(body,
                "var obj = JSON.parse(responseBody); obj.code === 200;",
                "Update response code check");

        Response getResponse = ApiUtilsAP.getByPathParam(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", TEST_USERNAME
        );

        String getBody = ApiUtilsAP.bodyAsString(getResponse);

        JSValidatorAP.assertFieldEquals(getBody, "firstName", "Updated");
        JSValidatorAP.assertFieldEquals(getBody, "lastName", "Name");
        JSValidatorAP.assertValidEmail(getBody, "email");

        System.out.println("TC08 PASSED - User updated.");
    }

    @Test(priority = 9, description = "TC09 - Update non-existent user")
    public void tc09_updateUser_notFound() {
        UserAP ghost = UserAP.defaultTestUser().withUsername("bumblebee");

        Response response = ApiUtilsAP.put(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", "bumblebee",
                ghost
        );

        int status = response.getStatusCode();
        Assert.assertTrue(status == 404 || status == 200);

        System.out.println("TC09 PASSED - Status: " + status);
    }

    @Test(priority = 10, description = "TC10 - Logout user",
            dependsOnMethods = "tc04_loginUser_validCredentials")
    public void tc10_logoutUser() {
        Response response = ApiUtilsAP.get(requestSpec, ApiUtilsAP.USER_LOGOUT_ENDPOINT);

        Assert.assertEquals(response.getStatusCode(), 200);

        String body = ApiUtilsAP.bodyAsString(response);

        JSValidatorAP.assertMessageContains(body, "ok");

        System.out.println("TC10 PASSED - User logged out.");
    }

    @Test(priority = 11, description = "TC11 - Delete existing user")
    public void tc11_deleteUser_exists() {

        UserAP freshUser = new UserAP(
                100001L, TEST_USERNAME, "Optimus", "Prime",
                "optimusprimer@petstore.com", "Pass@1234", "9876543210", 1
        );

        ApiUtilsAP.post(requestSpec, ApiUtilsAP.USER_ENDPOINT, freshUser);

        Response response = ApiUtilsAP.delete(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", TEST_USERNAME
        );

        Assert.assertEquals(response.getStatusCode(), 200);

        String body = ApiUtilsAP.bodyAsString(response);

        JSValidatorAP.assertValid(body,
                "var obj = JSON.parse(responseBody); obj.code === 200;",
                "Delete response validation");

        System.out.println("TC11 PASSED - User deleted.");
    }

    @Test(priority = 12, description = "TC12 - Delete non-existent user")
    public void tc12_deleteUser_notFound() {
        Response response = ApiUtilsAP.delete(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", "starscream99"
        );

        Assert.assertEquals(response.getStatusCode(), 404);

        System.out.println("TC12 PASSED - 404 confirmed.");
    }
}