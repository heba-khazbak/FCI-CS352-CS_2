package com.FCI.SWE.Services.com.FCI.SWE.Services;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.Services.NotificationInvoker;

public class NotificationInvokerTest {
	
	NotificationInvoker invoker = new NotificationInvoker();
	
	@DataProvider(name = "invoker")
	public static Object[][] invokerCheck()
	{
		return new Object[][]{{"{\"Status\":\"OK\"}","1" ,"3"}, 
				{"{\"Status\":\"Heba sent you a message: Hii group1\",\"receiver\":\"Group1\",\"sender\":\"Heba\",\"content\":\"Hii group1\"}","1" ,"2"},
				{"","10" ,"9"},{"" ,"",""}};
	}

  @Test (dataProvider = "invoker")
  public void handleNotificationService(String result , String ID , String type) {
	  Assert.assertEquals(result, invoker.handleNotificationService(ID , type));
  }
}
