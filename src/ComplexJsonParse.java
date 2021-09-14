import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(payload.CoursePrice());
		int count = js.getInt("courses.size()");
		System.out.println(count);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		String firstCourse = js.get("courses[1].title");
		System.out.println(firstCourse);

		// Print All course titles and their respective Prices

		for (int i = 0; i < count; i++) {

			String courseTitle = js.get("courses[" + i + "].title");
			System.out.println(courseTitle);
			int coursePrice = js.get("courses[" + i + "].price");
			System.out.println(coursePrice);
		}

		// Print no of copies sold by RPA Course
		for (int j = 0; j < count; j++) {
			String courseTitle = js.get("courses[" + j + "].title");
			if (courseTitle.equalsIgnoreCase("RPA")) {
				int courseCopies = js.get("courses[" + j + "].copies");
				System.out.println(courseCopies);
				break;	
			}

		}
		//Verify if Sum of all Course prices matches with Purchase Amount
		int sum=0;
		for(int i=0; i<count; i++) {
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int totalPrice=price*copies;
			sum=sum+totalPrice;
		}
		System.out.println("sum of all copies is: "+ sum);
		if(sum==purchaseAmount) {
			System.out.println("Courses Price is mathcing with total purchase amount "+sum+" = "+purchaseAmount);
		}
	}

}
