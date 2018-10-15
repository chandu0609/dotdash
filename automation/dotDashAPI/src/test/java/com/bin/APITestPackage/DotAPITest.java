package com.bin.APITestPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.bin.basepackage.BaseAPIClass;
import com.bin.objectModel.Task;
import com.bin.objectModel.TaskOrder;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DotAPITest extends BaseAPIClass{

	private String baseUrl;
	public Gson gson;
	public DotAPITest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@BeforeClass
	public void setUp(){
		baseUrl = prop.getProperty("baseURL");
		RestAssured.baseURI = baseUrl;
		RestAssured.port = 85;
		gson = new Gson();
	}
	
	
	public Response GetAPIRequest() {
	     String resource = "/nss-todo-automation/fake-API-call.php";
		 Response res =  given().
				    contentType(ContentType.JSON).
				   when().
				      get(resource).
				   then().  //assertThat().statusCode(200).  This is another way but more readable way assertion as below
		           extract().response();
		 return res;
	}

	@Test(priority = 1)
	public void serviceStatusTest() {
	      Response response = GetAPIRequest();
		  
		  int responseStatus = response.getStatusCode();
		  Assert.assertEquals(responseStatus, 200, "The status code isn't right.");
		 
	}
	
	
	@Test(priority = 2)
	public void tasksCountthoseHaveCatergoryTest() {
		 Response response = GetAPIRequest();
		 String jsonResponse = response.asString();
		 Task[] jsonAsArrayList = gson.fromJson(jsonResponse, Task[].class);
		 //Way #2 - ArrayList<Map<String,String>> jsonAsArrayList = ( ArrayList<Map<String,String>>) gson.fromJson(jsonRepsonse,ArrayList.class);

         int catergoryForTaskCount=0;
         for(Task singleTask : jsonAsArrayList) {
            if(singleTask.getCategory().contentEquals("")) {
            		catergoryForTaskCount++;
            }
         }
         System.out.println(String.format("\r\nTasks which do not have Category -  %d", catergoryForTaskCount));
    }
	
	@Test(priority = 3)
	public void printTaskNamesTest() {
		 Response response = GetAPIRequest();
		 String jsonResponse = response.asString();
		 Task[] jsonAsArrayList = gson.fromJson(jsonResponse, Task[].class);
		 //Way #2 - ArrayList<Map<String,String>> jsonAsArrayList = ( ArrayList<Map<String,String>>) gson.fromJson(jsonRepsonse,ArrayList.class);

          System.out.println("\r\nThe aggregation of all task names is as follows - ");
		 for(Task singleTask : jsonAsArrayList) {
			 System.out.println(String.format(singleTask.getTaskName()));
         }
        
    }
	
	
	@Test(priority = 4)
	public void printTaskBasedOnDueDateDescTest() {
		 Response response = GetAPIRequest();
		 String jsonResponse = response.asString();
		 Task[] jsonAsArrayList = gson.fromJson(jsonResponse, Task[].class);
		 Arrays.sort(jsonAsArrayList, new TaskOrder());
		 System.out.println("\r\nTasks in order of descending due date - ");
         for(Task singleTask : jsonAsArrayList) {
        	 System.out.println(singleTask.getTaskName());
         }
        
    }
	
	@Test(priority = 5)
	public void countTaskNamesTest() {
		 Response response = GetAPIRequest();
		 String jsonResponse = response.asString();
		 Task[] jsonAsArrayList = gson.fromJson(jsonResponse, Task[].class);
		 //Way #2 - ArrayList<Map<String,String>> jsonAsArrayList = ( ArrayList<Map<String,String>>) gson.fromJson(jsonRepsonse,ArrayList.class);
		 int taskNameCount = jsonAsArrayList.length;
		 System.out.println(String.format("\r\nTask Name Count -  %d", taskNameCount));
		 Assert.assertEquals(taskNameCount, 10);
        
    }
	
	//Test to validate whether all five attributes are returned for (each task) in each JSON array
	
	@Test(priority = 6)
	public void validateAllValuesAreReturnedTest() {
		 Response response = GetAPIRequest();
		 String jsonResponse = response.asString();
		 ArrayList<Map<String,String>> jsonAsArrayList = ( ArrayList<Map<String,String>>) gson.fromJson(jsonResponse,ArrayList.class);
		 SoftAssert sa = new SoftAssert();
		 for(Map<String, String> singleTask : jsonAsArrayList) {
			 int numberOfValues = singleTask.size();
			 sa.assertEquals(numberOfValues, 5);
	     }
         sa.assertAll();    
    }
	
	//All completed tasks
	
	@Test(priority = 7)
	public void allCompletedTasksTest() {
		 Response response = GetAPIRequest();
		 String jsonResponse = response.asString();
		 Task[] jsonAsArrayList = gson.fromJson(jsonResponse, Task[].class);
		
		 for(Task singleTask : jsonAsArrayList) {
        	 if(singleTask.getStatus().equals("c")) {
        		 System.out.println(singleTask.getTaskName());
        	 }
         }
        
    }
	
	
	
	
}
