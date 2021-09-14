import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.addPlace;
import pojo.location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;


public class specBuilder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		addPlace a = new addPlace();
		a.setAccuracy(50);
		a.setAddress("29, side layout, cohen 09");
		a.setLanguage("French-IN");
		a.setName("Frontline house");
		a.setPhone_number("(+91) 983 893 3937");
		a.setWebsite("http://google.com");
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		a.setTypes(myList);
		location l = new location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		a.setLocation(l);
	RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")	.setContentType(ContentType.JSON).build();
	ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	RequestSpecification request=given().spec(req).body(a);
	Response response=request.when().post("/maps/api/place/add/json")
	.then().spec(resspec).extract().response();
	String responseString = response.asString();
		System.out.println(responseString);
	}

}
