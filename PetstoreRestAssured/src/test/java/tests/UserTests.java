package tests;

import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseClass;
import io.restassured.response.Response;
import pojo.UserPojo;

public class UserTests extends BaseClass {
	UserPojo user;
	@Test
	public void createUser() {
		user = new UserPojo(101, username, "Apabrita", "Sarkar", "apa@test.com", "12345", "9999999999", 1);
		Response res = given()
				.contentType("application/json")
				.body(user)
				
				.when().post("/user");

		res.then().log().all();

		Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(dependsOnMethods = "createUser")
	public void getUser() {
		Response res = given()

				.when().get("/user/" + username);

		res.then().log().all();

		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertEquals(res.jsonPath().getString("username"), username);
	}
	@Test(dependsOnMethods = "createUser")
	public void loginUser() {

	    Response res =
	            given()
	                    .queryParam("username", username)
	                    .queryParam("password", "12345")

	            .when()
	                    .get("/user/login");

	    res.then().log().all();

	    Assert.assertEquals(res.statusCode(), 200);

	    String message = res.jsonPath().getString("message");
	    System.out.println("Login Response: " + message);
	}
	
	@Test(dependsOnMethods = "loginUser")
	public void logoutUser() {

	    Response res =
	            given()

	            .when()
	                    .get("/user/logout");

	    res.then().log().all();

	    Assert.assertEquals(res.statusCode(), 200);
	}
	
	@Test
	public void createUsersWithArray() {

	    UserPojo user1 = new UserPojo(201, "user1", "A", "One",
	            "a1@test.com", "123", "1111111111", 1);

	    UserPojo user2 = new UserPojo(202, "user2", "B", "Two",
	            "b2@test.com", "123", "2222222222", 1);

	    UserPojo[] users = {user1, user2};

	    Response res =
	            given()
	                    .contentType("application/json")
	                    .body(users)

	            .when()
	                    .post("/user/createWithArray");

	    res.then().log().all();

	    Assert.assertEquals(res.statusCode(), 200);
	}
	
	@Test
	public void createUsersWithList() {

	    UserPojo user1 = new UserPojo(301, "listuser1", "C", "Three",
	            "c3@test.com", "123", "3333333333", 1);

	    UserPojo user2 = new UserPojo(302, "listuser2", "D", "Four",
	            "d4@test.com", "123", "4444444444", 1);

	    java.util.List<UserPojo> userList = java.util.Arrays.asList(user1, user2);

	    Response res =
	            given()
	                    .contentType("application/json")
	                    .body(userList)

	            .when()
	                    .post("/user/createWithList");

	    res.then().log().all();

	    Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(dependsOnMethods = "getUser")
	public void updateUser() {
		user.setFirstName("UpdatedApabrita");
		Response res = given()
				.contentType("application/json")
				.body(user)

				.when().put("/user/" + username);

		res.then().log().all();

		Assert.assertEquals(res.statusCode(), 200);
	}

	@Test(dependsOnMethods = "updateUser")
	public void deleteUser() {
		Response res = given()

				.when().delete("/user/" + username);

		res.then().log().all();

		Assert.assertEquals(res.statusCode(), 200);
	}
}