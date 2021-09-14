import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMehtods;

public class CPractice {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://ecq-ecom-api-internal-1.stage.consumerreports.org/api";
		String response=
				given().log().all().header("x-api-key", "cyEzs9zRSn1sTJhg4ice72PQvBQPGzr67PDqJ0n3").header("Content-Type", "application/Json")
		.when().post("/authentication/anonymous")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		System.out.println("here is the response: "+response);

		 JsonPath js=ReUsableMehtods.rawToJson(response); 
		 String token =js.get("sessionToken"); System.out.println("Access Token is: "+ token);
		
		
		//Update API
		/*String newAddress = "70 winter walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/Json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("/maps/api/place/update/json")
		.then().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get API
		String responseGetPlace = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/Json").queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().log().all().statusCode(200).extract().response().asString();
		JsonPath js1=ReUsableMehtods.rawToJson(responseGetPlace);
		String actualAddress= js1.getString("address");
		System.out.println("actual Address is: "+ actualAddress);
		Assert.assertEquals(newAddress, actualAddress);
		//.body("address", equalTo("70 winter walk, USA"));
	}*/
	}
}
