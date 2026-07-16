package com.demo.FirstRestAssuredProjectTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredUsersTest {

	@Epic("API Tests")
	@Feature("Users API")
	@Story("Play with RestAssured with API that gives a set of users or a specific user")
	@Description("Verify GET /users/1 returns status 200")
	@AllureId("1")
	@Test
	public void getUsersTest() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		Allure.step("Calling /users endpoint and getting first element of it");
		RequestSpecification req = RestAssured.given();
		Response res = req.filters(new AllureRestAssured()).when().get("/users");
		String firstUserJson = res.jsonPath().getMap("[0]").toString();
		String firstName = res.jsonPath().getString("[0].name");
		String firstUsername = res.jsonPath().getString("[0].username");
		int firstId = res.jsonPath().getInt("[0].id");
		Allure.addAttachment("First User Object", "application/json", firstUserJson);
		System.out.println("First User Name: " + firstName);
		System.out.println("First User Username: " + firstUsername);
		System.out.println("First User ID: " + firstId);
		for(Header head: res.headers()) {
			System.out.println("header: " + head.toString());
		}
	    Allure.step("Validating status code which should be 200", () -> {
	        assertEquals(200, res.statusCode());
	    });
	    Allure.step("Validating first user name", () -> {
	        assertEquals("Leanne Graham", firstName);
	    });
	    Allure.step("Validating first user username", () -> {
	        assertEquals("Bret", firstUsername);
	    });
	    Allure.step("Validating first user ID, which should be 1", () -> {
	        assertEquals(1, firstId);
	    });

	} 

}

