package api.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class )
	public void testPostuser(String userID, String userName, String fname, String lName, String userEmail, String pwd, String phone)
	{
		User userPayLoad=new User();
		userPayLoad.setId(Integer.parseInt(userID));
		userPayLoad.setUsername(userName);
		userPayLoad.setFirstName(fname);
		userPayLoad.setLastName(lName);
		userPayLoad.setEmail(userEmail);
		userPayLoad.setPassword(pwd);
		userPayLoad.setPhone(phone);
		
		Response response=UserEndPoints.createUser(userPayLoad);
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteuserbyName(String userName)
	{
		
		Response response=UserEndPoints.deleteUser(userName);
		response.then().log().all();
		
		AssertJUnit.assertEquals(response.getStatusCode(), 200);

	}
	
	
}
