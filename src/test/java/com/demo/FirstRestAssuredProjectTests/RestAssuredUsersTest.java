package com.demo.FirstRestAssuredProjectTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

class RestAssuredUsersTest {

	@Test
	void getUsersTest() {
		
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		RequestSpecification req = RestAssured.given();
		Response res = req.when().get("/users");
		String firstName = res.jsonPath().getString("[0].name");
		String firstUsername = res.jsonPath().getString("[0].username");
		int firstId = res.jsonPath().getInt("[0].id");
		System.out.println("First User Name: " + firstName);
		System.out.println("First User Username: " + firstUsername);
		System.out.println("First User ID: " + firstId);
		for(Header head: res.headers()) {
			System.out.println("header: " + head.toString());
		}
	    assertEquals(200, res.statusCode());
	    assertEquals("Leanne Graham", firstName);
	    assertEquals("Bret", firstUsername);
	    assertEquals(1, firstId); 

	} 

}

