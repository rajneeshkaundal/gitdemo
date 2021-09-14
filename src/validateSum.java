import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class validateSum {
	@Test
	public void sumOfCourses() {
		JsonPath js = new JsonPath(payload.CoursePrice());
		int sum=0;
		int count = js.getInt("courses.size()");
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		for(int i=0; i<count; i++) {
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int totalPrice=price*copies;
			sum=sum+totalPrice;
		}
		System.out.println("sum of all copies is: "+ sum);
		Assert.assertEquals(sum, purchaseAmount);
	}

}
