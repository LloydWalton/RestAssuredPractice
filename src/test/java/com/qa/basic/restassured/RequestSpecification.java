package com.qa.basic.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;

public class RequestSpecification {

	public static void main(String[] args) {
		io.restassured.specification.RequestSpecification reqSpec=new RequestSpecBuilder().setBaseUri("https://reqres.in").addQueryParam("page", "2").build();
		ResponseSpecification resSpec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		String res=given().spec(reqSpec).header("Content-Type","application/json").
		when().get("/api/users").then().spec(resSpec).extract().response().asString();
		System.out.println(res);


	}

}
