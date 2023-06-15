package com.qa.basic.restassured;

public class TestData {

	
	public static String addPlaceData()
	{
		String addPlaceLocation="{\r\n"
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
				+ "}";
		return addPlaceLocation;
	}
	
	public static String addBookData(String bookName,String bookId)
	{
		String addBookDataPayload="{\r\n"
				+ "\"name\":\"Learn Appium Automation with Java\",\r\n"
				+ "\"isbn\":\""+bookName+"\",\r\n"
				+ "\"aisle\":\""+bookId+"\",\r\n"
				+ "\"author\":\"John foer\"\r\n"
				+ "}";
		
		return addBookDataPayload;
	}
}
