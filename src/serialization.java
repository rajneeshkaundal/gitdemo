import io.restassured.RestAssured;
import pojo.addPlace;
import pojo.location;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;


public class serialization {

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
		
	RestAssured.baseURI= "https://rahulshettyacademy.com";
	String response=given().log().all().queryParam("key", "qaclick123")
	.body(a)
	.when().post("/maps/api/place/add/json")
	.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);
	}

}
