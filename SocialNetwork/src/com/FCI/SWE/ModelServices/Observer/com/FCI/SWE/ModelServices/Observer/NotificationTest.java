package com.FCI.SWE.ModelServices.Observer.com.FCI.SWE.ModelServices.Observer;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.ModelServices.Observer.Notification;


public class NotificationTest {
	
	//Notification notification = new Notification ("Heba" , 10 ,15 ,"Ziad");
	
	@DataProvider(name = "NotificationByID")
	public static Object[][] NotificationByID()
	{
		return new Object[][]{{"1","2"},{"3","6"},{"3","7"},{"-1","50"},
				{"-1" , ""}};
	}

  @Test(dataProvider = "NotificationByID")
  public void getNotification(String result,String ID) {
	  Assert.assertEquals(result, Notification.getNotification(ID));
  }

  @Test
  public void saveNotification() {
    throw new RuntimeException("Test not implemented");
  }
}
