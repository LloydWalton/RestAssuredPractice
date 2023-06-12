package com.qa.basic.restassured;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AddPlace {
	String place_id;

	@BeforeMethod
	public void baseURL() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
	}

	
	// Adding body directly from code
	@Test(priority = 1)
	public void addPlace_Direct() {
		given().log().all().queryParam("key", "qaclick12311").header("Content-Type", "application/json")
				.body("{\r\n" + "    \"location\": {\r\n" + "        \"lat\": -38.383494,\r\n"
						+ "        \"lng\": 33.427362\r\n" + "    },\r\n" + "    \"accuracy\": 50,\r\n"
						+ "    \"name\": \"Frontline house\",\r\n" + "    \"phone_number\": \"(+91) 983 893 3937\",\r\n"
						+ "    \"address\": \"29, side layout, cohen 09\",\r\n" + "    \"types\": [\r\n"
						+ "        \"shoe park\",\r\n" + "        \"shop\"\r\n" + "    ],\r\n"
						+ "    \"website\": \"http://google.com\",\r\n" + "    \"language\": \"French-IN\"\r\n" + "}")
				.when().post("maps/api/place/add/json").then().assertThat().statusCode(200).log().all();
	}

	// Adding body from another class
	@Test(priority = 2)
	public void addPlace_FromDiffClass() {
		given().log().all().queryParam("key", "qaclick12311").header("Content-Type", "application/json")
				.body(TestData.addPlaceData()).when().post("maps/api/place/add/json").then().assertThat()
				.statusCode(200).log().all();
	}

	// Asserting response
	@Test(priority =3)
	public void addPlace_CaptureResponse() {
		// equalTo=>import static org.hamcrest.Matchers.*;
		String response = 
		given().log().all().queryParam("key", "qaclick12311").header("Content-Type", "application/json")
		.body(TestData.addPlaceData()).when().post("maps/api/place/add/json").then().assertThat()
		.statusCode(200).body("status", equalTo("OK")).body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.41 (Ubuntu)")).extract().response().asString();

		System.out.println(response);

	}

	@Test(priority = 4)
	public void addPlace_JsonPath() {
		// equalTo=>import static org.hamcrest.Matchers.*;
		String response = given().log().all().queryParam("key", "qaclick12311")
				.header("Content-Type", "application/json").body(TestData.addPlaceData()).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200).body("status", equalTo("OK"))
				.body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.41 (Ubuntu)")).extract().response()
				.asString();

		// Capturing response
		JsonPath js = new JsonPath(response);
		place_id = js.getString("place_id");
		String reference = js.getString("reference");
		String id = js.getString("id");

		System.out.println(place_id);
		System.out.println(reference);
		System.out.println(id);

	}
	
	@Test (dependsOnMethods = "addPlace_JsonPath")
	public void updatePlace()
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		given().queryParam("key", "qaclick12311").header("Content-Type","application/json")
				.body("{\r\n"
						+ "\"place_id\":\""+place_id+"\",\r\n"
						+ "\"address\":\"70 winter walk, USA1\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n"
						+ "}\r\n"
						+ "").
				when().put("maps/api/place/update/json").
				then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
	}
	
	@Test (priority = 5,description = "Passing body from local")
	public void updatePlaceLocal() throws IOException
	{
		String res=given().queryParam("key", "qaclick123").queryParam("place_id",place_id).header("Content-Type","application/json").
				body((new String(Files.readAllBytes(Paths.get("C:\\TestData\\testdata.txt"))))).
		when().put("maps/api/place/update/json").
		then().log().all().assertThat().statusCode(404).body("msg", equalTo("Update address operation failed, looks like the data doesn't exists")).extract().response().asString();
		
		
}

	
}



