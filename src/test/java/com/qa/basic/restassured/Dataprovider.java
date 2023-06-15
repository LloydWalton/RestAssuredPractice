package com.qa.basic.restassured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class Dataprovider {
	String place_id;

	@BeforeMethod
	public void baseURL() {
		RestAssured.baseURI = "https://reqres.in";
	}

	
	
	// Adding body directly from code
	//given() ->import static io.restassured.RestAssured.*;
	@Test(priority = 1,dataProvider = "userid")
	public void addPlace_Direct(String userid) {
		given().pathParam("userid",userid).log().all().when().get("/api/users/{userid}").then().assertThat().statusCode(200).log().all();
	}
	
	@DataProvider 
	public Object[] userid()
	{
		return new Object[] {"1","2","3"};
	}
}
