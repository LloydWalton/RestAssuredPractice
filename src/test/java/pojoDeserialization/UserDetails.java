package pojoDeserialization;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@JsonIgnoreProperties(ignoreUnknown = true)

public class UserDetails {
	
	private Data data;
	private Support support;
	
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Support getSupport() {
		return support;
	}
	public void setSupport(Support support) {
		this.support = support;
	}
	
	
	
	
}
