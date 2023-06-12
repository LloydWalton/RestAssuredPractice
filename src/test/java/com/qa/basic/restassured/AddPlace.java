package com.qa.basic.restassured;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class AddPlace {
	
	@BeforeMethod
	public void baseURL()
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
	}

	// Adding body directly from code
	@Test
	public void addPlace_Direct()
	{
		given().log().all().queryParam("key", "qaclick12311").header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"location\": {\r\n"
				+ "        \"lat\": -38.383494,\r\n"
				+ "        \"lng\": 33.427362\r\n"
				+ "    },\r\n"
				+ "    \"accuracy\": 50,\r\n"
				+ "    \"name\": \"Frontline house\",\r\n"
				+ "    \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "    \"address\": \"29, side layout, cohen 09\",\r\n"
				+ "    \"types\": [\r\n"
				+ "        \"shoe park\",\r\n"
				+ "        \"shop\"\r\n"
				+ "    ],\r\n"
				+ "    \"website\": \"http://google.com\",\r\n"
				+ "    \"language\": \"French-IN\"\r\n"
				+ "}").
		when().post("maps/api/place/add/json").then().assertThat().statusCode(200).log().all();
}
	
	// Adding body from another class
		@Test
		public void addPlace_FromDiffClass()
		{
			TestData data=new TestData();
			
			given().log().all().queryParam("key", "qaclick12311").header("Content-Type","application/json").body(data.addPlaceData()).
			when().post("maps/api/place/add/json").then().assertThat().statusCode(200).log().all();
		}


	}
	
