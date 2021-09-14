import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourse;
import pojo.Mobile;
import pojo.WebAutomation;

public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//System.getProperty("webdriver.chrome.driver", "D://chromedriver.exe");
		//WebDriver driver = new ChromeDriver();
		/*
		 * WebDriverManager.chromedriver().setup(); WebDriver driver = new
		 * ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		 * ); driver.findElement(By.xpath("//input[@type=\"email\"]")).sendKeys(
		 * "rajneesh.kaundal89");
		 * driver.findElement(By.xpath("//input[@type=\"email\"]")).sendKeys(Keys.ENTER)
		 * ; driver.findElement(By.xpath("//input[@type=\"password\"]")).sendKeys(
		 * "Navya@123");
		 * driver.findElement(By.xpath("//input[@type=\"password\"]")).sendKeys(Keys.
		 * ENTER); Thread.sleep(4000);
		 */
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AX4XfWi01UPCKwDzz8BxifOkToZmoQYqf1VZx0VsKZgMqPi7IzHbf-BLdpt040cf0a1q5Q&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none";
		String partialcode= url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		System.out.println(code);
		
		String gettoken = given().urlEncodingEnabled(false)
		.queryParam("code", code).queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").queryParam("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.println(gettoken);
		JsonPath js = new JsonPath(gettoken);
		String accessToken = js.getString("access_token");
		
		
		String[] expectedWebAutomationCourseList = {"Selenium Webdriver Java", "Cypress","Protractor"};
		GetCourse gt =given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		//System.out.println("response is: "+gt);
		System.out.println(gt.getLinkedIn());
		System.out.println(gt.getInstructor());
		System.out.println(gt.getCourses().getApi().get(1).getCourseTitle());
		System.out.println(gt.getCourses().getWebAutomation().get(1).getCourseTitle());
		System.out.println("Instructor of the course is: "+gt.getInstructor());
		List<API> apiCourse = gt.getCourses().getApi();
		for(int i=0;i<apiCourse.size();i++) {
				if(apiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
				System.out.println(apiCourse.get(i).getPrice());
			}
		} 
		List<Mobile> mobile = gt.getCourses().getMobile();
		System.out.println(mobile.get(0).getPrice());
		ArrayList <String> a = new ArrayList<String>(); 
		List<WebAutomation> webA = gt.getCourses().getWebAutomation();
		for(int i=0; i<webA.size();i++) {
			a.add(webA.get(i).getCourseTitle());
		}
		List<String> expectedList=Arrays.asList(expectedWebAutomationCourseList);
		Assert.assertTrue(a.equals(expectedList));
	}

}
