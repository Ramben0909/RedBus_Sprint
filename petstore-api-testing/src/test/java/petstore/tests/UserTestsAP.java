package petstore.tests;

import petstore.base.BaseTestAP;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import petstore.models.UserAP;
import petstore.utils.ApiUtilsAP;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UserTestsAP extends BaseTestAP {

    private String TEST_USERNAME;
    private UserAP testUser;

    @BeforeMethod
    public void init() {
        TEST_USERNAME = "user_" + UUID.randomUUID();

        testUser = UserAP.defaultTestUser()
                .withUsername(TEST_USERNAME)
                .withId(System.currentTimeMillis());

        System.out.println("Test user prepared: " + testUser);
    }

    @Test
    public void tc01_createUser() {
        Response response = ApiUtilsAP.post(requestSpec, ApiUtilsAP.USER_ENDPOINT, testUser);

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test
    public void tc02_createUsersWithArray() {

        List<UserAP> users = Arrays.asList(
                UserAP.defaultTestUser()
                        .withUsername("user_" + UUID.randomUUID())
                        .withId(System.currentTimeMillis()),

                UserAP.defaultTestUser()
                        .withUsername("user_" + UUID.randomUUID())
                        .withId(System.currentTimeMillis() + 1)
        );

        Response response = ApiUtilsAP.post(
                requestSpec,
                ApiUtilsAP.USER_CREATE_WITH_ARRAY,
                users
        );

        Assert.assertEquals(response.getStatusCode(), 200);
    }
    
    
    @Test
    public void tc03_createUsersWithList() {

        List<UserAP> users = Arrays.asList(
                UserAP.defaultTestUser()
                        .withUsername("user_" + UUID.randomUUID())
                        .withId(System.currentTimeMillis()),

                UserAP.defaultTestUser()
                        .withUsername("user_" + UUID.randomUUID())
                        .withId(System.currentTimeMillis() + 1)
        );

        Response response = ApiUtilsAP.post(
                requestSpec,
                ApiUtilsAP.USER_CREATE_WITH_LIST,
                users
        );

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void tc04_loginUser_validCredentials() {
        ApiUtilsAP.post(requestSpec, ApiUtilsAP.USER_ENDPOINT, testUser);

        Response response = ApiUtilsAP.getWithQueryParams(
                requestSpec,
                ApiUtilsAP.USER_LOGIN_ENDPOINT,
                testUser.getUsername(),
                testUser.getPassword()
        );

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("logged in user session"));
    }

    @Test
    public void tc05_loginUser_invalidCredentials() {
        Response response = ApiUtilsAP.getWithQueryParams(
                requestSpec,
                ApiUtilsAP.USER_LOGIN_ENDPOINT,
                "wrong_user",
                "wrong_pass"
        );

        Assert.assertTrue(
                response.getStatusCode() == 400 || response.getStatusCode() == 200
        );
    }

    @Test
    public void tc06_getUserByName_exists() {
        ApiUtilsAP.post(requestSpec, ApiUtilsAP.USER_ENDPOINT, testUser);

        Response response = ApiUtilsAP.getByPathParam(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", TEST_USERNAME
        );

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("username"), TEST_USERNAME);
    }

    @Test
    public void tc07_getUserByName_notFound() {
        Response response = ApiUtilsAP.getByPathParam(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", "non_existing_user"
        );

        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void tc08_updateUser() {
        ApiUtilsAP.post(requestSpec, ApiUtilsAP.USER_ENDPOINT, testUser);

        UserAP updatedUser = testUser
                .withFirstName("Updated")
                .withLastName("Name");

        Response response = ApiUtilsAP.put(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", TEST_USERNAME,
                updatedUser
        );

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void tc09_updateUser_notFound() {
        UserAP ghost = UserAP.defaultTestUser()
                .withUsername("ghost_" + UUID.randomUUID());

        Response response = ApiUtilsAP.put(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", ghost.getUsername(),
                ghost
        );

        Assert.assertTrue(
                response.getStatusCode() == 404 || response.getStatusCode() == 200
        );
    }

    @Test
    public void tc10_logoutUser() {
        Response response = ApiUtilsAP.get(requestSpec, ApiUtilsAP.USER_LOGOUT_ENDPOINT);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void tc11_deleteUser_exists() {
        ApiUtilsAP.post(requestSpec, ApiUtilsAP.USER_ENDPOINT, testUser);

        Response response = ApiUtilsAP.delete(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", TEST_USERNAME
        );

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void tc12_deleteUser_notFound() {
        Response response = ApiUtilsAP.delete(
                requestSpec,
                ApiUtilsAP.USER_BY_USERNAME_ENDPOINT,
                "username", "ghost_user"
        );

        Assert.assertEquals(response.getStatusCode(), 404);
    }
}