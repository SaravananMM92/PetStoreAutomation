package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	
	//4 tests required - CRUD
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	//1.data creation
	@BeforeClass
	public void setup()
	{
		faker = new Faker(); // used to simulate data
		userPayload = new User(); //used to call pojo methods getter and setter
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger= LogManager.getLogger(this.getClass());
		
		logger.debug("**** Debugging ****");
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("************ Creating user *************");
		Response response=UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User is created *************");
		
	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("************ Reading a user *************");
		Response response=UserEndPoints.readUser(this.userPayload.getUsername());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ User reading completed *************");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		logger.info("************ Updating user *************");
		// Update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response=UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body().statusCode(200);
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//checking data after update
		Response responseAfterupdate=UserEndPoints.readUser(this.userPayload.getUsername());
		
		responseAfterupdate.then().log().all();
		Assert.assertEquals(responseAfterupdate.getStatusCode(), 200);
		
		logger.info("************ Update completed *************");
	}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		logger.info("************ Deleting a user *************");
		
		Response response=UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("************ Delete user completed *************");
		
	}
	

}
