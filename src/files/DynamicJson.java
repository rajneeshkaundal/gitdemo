package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class DynamicJson {
	@Test(dataProvider="BooksData")
	public void addbook(String isbn, String aisle) {
		RestAssured.baseURI= "http://216.10.245.166";
		String res=given().log().all().header("Content-Type", "application/json")
		.body(payload.addbookPayload(isbn, aisle))
		.when()
		.post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js=ReUsableMehtods.rawToJson(res);
		String id=js.getString("ID");
		System.out.println(id);

	}
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		//array = collection of elements
		//multi demensional array = collection of arrays
		
		return new Object [][] {{"sjksdfa", "24s32"}, {"23sesf", "23s2342"}, {"ghskgg", "24ss3234"}, {"ghjhgj", "34s2677"}};
	}

}
