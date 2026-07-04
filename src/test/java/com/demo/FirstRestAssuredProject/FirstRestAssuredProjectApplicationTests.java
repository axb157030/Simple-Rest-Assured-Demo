package com.demo.FirstRestAssuredProject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest
class FirstRestAssuredProjectApplicationTests {

	@Test
	void contextLoads() {
		
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/users/1\n";
		RequestSpecification req = RestAssured.given();

	}

}
