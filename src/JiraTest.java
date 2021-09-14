import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {
	
	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
		SessionFilter session = new SessionFilter();
		//Login API
		String loginAPIRespones= given().relaxedHTTPSValidation().header("Content-Type", "application/json").body("{ \"username\": \"rajneesh.kaundal\", \"password\": \"Navya@123\" }")
		.log().all().filter(session).when()
		.post("rest/auth/1/session").then().log().all().extract().response().asString();
		System.out.println(loginAPIRespones);
		
		
		// Add Comment
		String comment ="This is API Automation using restassured";
		String addCommentResponse = given().pathParam("key", "10006").log().all().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \""+comment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when()
		.post("/rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addCommentResponse);
		String commentId = js.getString("id");
		//add attachment
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10006").header("Content-Type", "multipart/form-data")
		.multiPart("file", new File("Jira.txt"))
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//Get all detail of an issue
		String issueDetail=given().filter(session).pathParam("key", "10006")
				.queryParam("fields", "comment")
				.header("content-type", "application/json")
				.when().get("/rest/api/2/issue/{key}")
				.then().assertThat().statusCode(200).extract().response().asString();
			System.out.println(issueDetail);
			
			JsonPath js1 = new JsonPath(issueDetail);
			int commentSize =js1.getInt("fields.comment.comments.size()");
			for(int i=0;i<commentSize; i++) {
				String commentIdIssue=js1.get("fields.comment.comments["+i+"].id");
				System.out.println(commentIdIssue);
				if(commentIdIssue.equalsIgnoreCase(commentId)) {
					String message = js1.get("fields.comment.comments["+i+"].body").toString();
					System.out.println("Comment from response"+message);
					Assert.assertEquals(message, comment);
				}
			}
			
	
	}	

}
