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

public class AddPlace {
	String place_id;

	@BeforeMethod
	public void baseURL() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
	}

	
	
	// Adding body directly from code
	//given() ->import static io.restassured.RestAssured.*;
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

	@Test(priority = 6,dataProvider = "bookData")
	public void delBook(String book,String id)
	{
		//RestAssured.baseURI="https://rahulshettyacademy.com";
		//given().log().all().header("Content-Type","application/json").body(TestData.addBookData(book, id)).when().post("Library/Addbook.php")
		given().log().all().header("Content-Type","application/json").body("{\r\n"
				+ "\"name\":\"Learn Aaappium Automation with Java\",\r\n"
				+ "\"isbn\":\"bcssd\",\r\n"
				+ "\"aisle\":\"29ss26\",\r\n"
				+ "\"author\":\"John faoer\"\r\n"
				+ "}").when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).body("Msg", equalTo("successfully added"));
		System.out.println("************************");
		System.out.println(book);
		System.out.println(id);
	}
	
	
	@DataProvider
	public Object[][] bookData()
	{
		return new Object[][] {{"Viadsan","raddsa"},{"dsadsa","dsada"}};
	}

	@Test(priority = 7)
	public void pathParameter()
	
	{
		given().log().all().pathParam("country", "Finland") .when() .get("https://restcountries.com/v2/name/{country}") .then().log().all().statusCode(200).body("capital", contains("Helsinki"));
		
	}

	/*
	@Test(priority = 8)
	public void sessionFilter()
	{
		RestAssured.baseURI="http://localhost:8080";
		//Login Scenario
		SessionFilter session=new SessionFilter();
		String response=given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{\r\n" +
		"    \"username\": \"RahulShetty\",\r\n" +
		"    \"password\": \"XXXX11\"\r\n" +
		"}").log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();


		//Pass the same under 
		given().filter(session).when().post().then().statusCode(200);
	}
	
	
	@Test (priority = 9)
	public void addAttchment()
	{
		given().pathParam("idparam",id).log().all().header("Content-Type","multipart/form-data").filter(session).header("X-Atlassian-Token","nocheck").
		multiPart("file",new File("C:\\Users\\clloy\\eclipse-workspace\\RestAssured-Udemy\\Attachment")).when().post("/rest/api/2/issue/{idparam}/attachments").
		then().assertThat().statusCode(200);
	}

@Test (priority = 10)
	public void relaxedHTTPSValidation()
	{
	String response=given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{\r\n" +
"    \"username\": \"RahulShetty\",\r\n" +
"    \"password\": \"XXXX11\"\r\n" +
"}").log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();

}
*/	
}



