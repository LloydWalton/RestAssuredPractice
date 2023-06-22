package pojaSerialization;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojoDeserialization.UserDetails;

public class RegisterReq {
	RegisterBody body=new RegisterBody();
	
	@BeforeMethod
	public void baseURL() {
		RestAssured.baseURI = "https://reqres.in";
	
		body.setEmail("eve.holt@reqres.in");
		body.setPassword("pistol");
		
	}
		@Test(priority = 1)
		public void actualRequest() throws IOException
		{
			
			Response res=given().log().all().body(body).header("Content-Type","application/json").when().post("/api/register").then().log().all().assertThat().statusCode(200).extract().response();
			
			System.out.println(res);
		}
}
