package com.demo.FirstRestAssuredProjectTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
	
	private RequestSpecification req;
	
	@BeforeAll
	static void initBaseUrl() {
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RestAssured.filters(new AllureRestAssured());
	}
		@BeforeEach
		void init() {
		req=RestAssured.given();
	}



	@Epic("API Users Tests")
	@Feature("Users API")
	@Story("Play with RestAssured with API that gives a set of users or a specific user")
	@Description("Verify GET /users/1 returns status 200")
	@AllureId("1")
	@Test
	public void getFirstUsersTest() {
		Allure.step("Calling /users endpoint and getting first element of it");
		Response res = req.when().get("/users");
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
	
	@Epic("Comment API Test")
	@Feature("Getting first post from Comments API")
	@Story("Play with RestAssured with API that gives a set of comments and testing response after query for first post on comments list from API")
	@Description("Verify GET /comments and /comments?postId=1 returns status 200 and"
			+ "that response of /comments?postId=1 has less elements than response of /comments")
	@AllureId("2")
	@Test
	public void getFirstPostTest() {
		req.basePath("/comments");
		Response resComments = req.get();
		Response resFirstPost = req.queryParam("postId", 1).get();
		// BUFFER BEFORE AllureRestAssured touches the stream
		String allCommentsJson = resComments.getBody().asString();
		String firstPostJson = resFirstPost.getBody().asString();
		// Attach buffered JSON
		Allure.addAttachment("All Comments", "application/json", allCommentsJson);
		Allure.addAttachment("First Post", "application/json", firstPostJson);
		Allure.step("Validating status code to get comments should be 200", () -> {
	        assertEquals(200, resComments.statusCode());
	    });
	    Allure.step("Validating status code to get first spost 200", () -> {
	        assertEquals(200, resComments.statusCode());
	    });
	    
	    Allure.step("Validating that number of elements directly in first post is less"
	    		+ " then the number of elements directly in all the comments", () -> {
	        assertTrue(resComments.jsonPath().getList("$").size() > resFirstPost.jsonPath().getList("$").size());
	    });
		
	}

}

