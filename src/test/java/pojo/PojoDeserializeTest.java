package pojo;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class PojoDeserializeTest {
	
	@BeforeMethod
	public void baseURL() {
		RestAssured.baseURI = "https://reqres.in";
	}
		@Test(priority = 2)
		public void actualRequest() throws IOException
		{
		
			
			
			UserDetails userDetails=given().pathParam("userid","1").log().all().when().get("/api/users/{userid}").as(UserDetails.class);
			System.out.println("***************");
			
			System.out.println(userDetails.getData().getId());
			System.out.println(userDetails.getData().getEmail());
			System.out.println(userDetails.getData().getFirst_name());
			System.out.println(userDetails.getData().getLast_name());
			System.out.println(userDetails.getData().getAvatar());
			
			
			System.out.println(userDetails.getSupport().getUrl());
			System.out.println(userDetails.getSupport().getText());
			
			System.out.println("***************");
			
			//System.out.println(gc.getInstructor());
			
		//	System.out.println(gc.getExpertise());
			
			//System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());	
			
		}


}
