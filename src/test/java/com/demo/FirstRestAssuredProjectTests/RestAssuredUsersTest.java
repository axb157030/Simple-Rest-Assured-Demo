package com.demo.FirstRestAssuredProjectTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
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



	@Epic("Practice Rest Assured Testing")
	@Feature("Users API Tests")
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
	
	@Epic("Practice Rest Assured Testing")
	@Feature("Comments APIs test")
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
	
	@Epic("Practice Rest Assured Testing")
	@Feature("Comment API Test")
	@Story("Play with RestAssured with API to add a comment. to the list of posts and testing response after query for first post on comments list from API")
	@Description("Verify POST /comments and /comments returns status 201")
	@AllureId("3")
	@Test
	public void addPostTest() throws JSONException {
		JSONObject addCommentRequest = new JSONObject();
		addCommentRequest.put("postId", 1);
		addCommentRequest.put("name", "Amine's Test Comment");
		addCommentRequest.put("email", "Presley.Mueller@myrl.com");
		addCommentRequest.put("body", "This is a test comment added via RestAssured.");
		Response resAddedComment = req.basePath("/comments")
	       .body(addCommentRequest.toString())
	       .post();

		Allure.addAttachment("Add Comment Request", "application/json", addCommentRequest.toString());
		Allure.addAttachment("Add Comment Response", "application/json", resAddedComment.asPrettyString());
		Allure.step("Validating status code to add comment should be 201", () -> {
	        assertEquals(201, resAddedComment.statusCode());
	    });

		
	}
	
	@Epic("Practice Rest Assured Testing")
	@Feature("Comment API Test")
	@Story("Play with RestAssured with PUT API to updating a comment.")
	@Description("Verify PUT /comments and /comments returns status 200")
	@AllureId("4")
	@Test
	public void updateCommentPutTest() throws JSONException {
		JSONObject updateCommentRequest = new JSONObject();
		updateCommentRequest.put("postId", 1);
		updateCommentRequest.put("name", "Amine's Test Put Comment");
		updateCommentRequest.put("email", "Presley.Mueller@myrl.com");
		updateCommentRequest.put("body", "This is a test comment update via RestAssured.");
		Response resUpdateComment = req.basePath("/comments")
	       .body(updateCommentRequest.toString())
	       .put("/1");

		Allure.addAttachment("Update Comment Request", "application/json", updateCommentRequest.toString());
		Allure.addAttachment("Update Comment Response", "application/json", resUpdateComment.asPrettyString());
		Allure.step("Validating status code to update comment should be 200", () -> {
	        assertEquals(200, resUpdateComment.statusCode());
	    });
	}
	
	@Epic("Practice Rest Assured Testing")
	@Feature("Comment APIs Test")
	@Story("PATCH Update Comment")
	@Description("Validate partial update of a comment using PATCH on JSONPlaceholder.")
    @Test
    public void updateCommentPatchTest() throws JSONException {

        JSONObject patchCommentRequest = new JSONObject();
        patchCommentRequest.put("name", "Bob's PATCH Updated Name");
        patchCommentRequest.put("body", "This is a partial update via PATCH.");
        Response originalComment = req.basePath("/comments").get("1");

        Response resPatchComment = req
                .basePath("/comments")
                .body(patchCommentRequest.toString())
                .patch("/1");   // IMPORTANT: PATCH must include the ID

        Allure.addAttachment("PATCH Request", "application/json", patchCommentRequest.toString());
        Allure.addAttachment("PATCH Response", "application/json", resPatchComment.asPrettyString());
        Allure.addAttachment("GET Response of the Comment", "application/json", originalComment.asString());

        Allure.step("Validating status code should be 200", () -> {
            assertEquals(200, resPatchComment.statusCode());
        });
        

        /*Allure.step("Validating returned fields match the PATCH request by comparing request fields to response fields", () -> {
            assertEquals(patchCommentRequest.getString("name"), resPatchComment.jsonPath().getString("name"));
            assertEquals(patchCommentRequest.getString("body"), resPatchComment.jsonPath().getString("body"));
        });
        
        Allure.step("Comparing The comment that existed before patch request was made on it to response received after patch request was made on it. They should have different values.", () -> {
            assertTrue(originalComment.jsonPath().getString("name") != resPatchComment.jsonPath().getString("name"));
            assertTrue(originalComment.jsonPath().getString("body") != resPatchComment.jsonPath().getString("body"));
        }); */
    }
	
	@Epic("Practice Rest Assured Testing")
	@Feature("Comments APIs")
	@Story("Delete Comment")
	@Description("Validate that a comment can be deleted using DELETE on JSONPlaceholder.")
    @Test
    public void deleteCommentTest() {

        Response resCommentDelete = req
                .basePath("/comments")
                .delete("/1");   // DELETE must include the ID

        Allure.addAttachment("DELETE Response", "application/json", resCommentDelete.asPrettyString());

        Allure.step("Validating status code should be 200", () -> {
            assertEquals(resCommentDelete.statusCode(), 200);
        });

        Allure.step("Validating response body is empty JSON {}", () -> {
            assertEquals(resCommentDelete.asString().trim(), "{}");
        });
    }
    


}
