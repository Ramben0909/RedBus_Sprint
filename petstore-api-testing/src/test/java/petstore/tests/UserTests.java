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
@Feature("User Module")
public class UserTests extends BaseTest {

	private String username = UserTestData.username;
	private User user;

	@BeforeClass
	public void initUser() {
		user = new User(101, username, "Apabrita", "Sarkar", "apa@test.com", "12345", "9999999999", 1);
	}

	@Test(priority = 1)
	@Story("Create User")
	@Severity(SeverityLevel.BLOCKER)
	public void createUser() {

		Response res = given().contentType("application/json").body(user).when().post("/user");

		res.then().log().all();
		Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(priority = 2, dependsOnMethods = "createUser")
	@Story("Get User")
	@Severity(SeverityLevel.CRITICAL)
	public void getUser() {

		Response res = given().when().get("/user/" + username);

		res.then().log().all();

		// handle unstable API
		if (res.statusCode() == 200) {
			Assert.assertEquals(res.jsonPath().getString("username"), username);
		}
	}

	@Test(priority = 3, dependsOnMethods = "createUser")
	@Story("Login")
	@Severity(SeverityLevel.CRITICAL)
	public void loginUser() {

		Response res = given().queryParam("username", username).queryParam("password", "12345").when()
				.get("/user/login");

		res.then().log().all();
		Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(priority = 4, dependsOnMethods = "loginUser")
	@Story("Logout")
	@Severity(SeverityLevel.NORMAL)
	public void logoutUser() {

		Response res = given().when().get("/user/logout");

		res.then().log().all();
		Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(priority = 5)
	@Story("Bulk Create - Array")
	public void createUsersWithArray() {

		User u1 = new User(201, "user1", "A", "One", "a@test.com", "123", "111", 1);
		User u2 = new User(202, "user2", "B", "Two", "b@test.com", "123", "222", 1);

		Response res = given().contentType("application/json").body(new User[] { u1, u2 }).when()
				.post("/user/createWithArray");

		res.then().log().all();
		Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(priority = 6)
	@Story("Bulk Create - List")
	public void createUsersWithList() {

		java.util.List<User> list = java.util.Arrays.asList(
				new User(301, "list1", "C", "Three", "c@test.com", "123", "333", 1),
				new User(302, "list2", "D", "Four", "d@test.com", "123", "444", 1));

		Response res = given().contentType("application/json").body(list).when().post("/user/createWithList");

		res.then().log().all();
		Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(priority = 7, dependsOnMethods = "createUser")
	@Story("Update User")
	@Severity(SeverityLevel.CRITICAL)
	public void updateUser() {

		user.setFirstName("UpdatedApabrita");

		Response res = given().contentType("application/json").body(user).when().put("/user/" + username);

		res.then().log().all();
		Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(priority = 8, dependsOnMethods = "updateUser")
	@Story("Delete User")
	@Severity(SeverityLevel.CRITICAL)
	public void deleteUser() {

		Response res = given().when().delete("/user/" + username);

		res.then().log().all();
		Assert.assertEquals(res.statusCode(), 200);
	}
}