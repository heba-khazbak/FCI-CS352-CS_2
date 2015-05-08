package com.FCI.SWE.ModelServices.Observer.com.FCI.SWE.ModelServices.Observer;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.ModelServices.PagePost;
import com.FCI.SWE.ModelServices.Post;
import com.FCI.SWE.ModelServices.Observer.GroupMessage;
import com.google.appengine.api.datastore.Entity;

public class GroupMessageTest {
	
	@DataProvider(name = "groupMsg")
 	public static Object[][] sharedPostCheck()
 	{
	  
 		Entity E1 = new Entity("groupMsg",1);
 		
 		E1.setProperty("ID", "1" );
 		E1.setProperty("sender", "Heba");
 		E1.setProperty("receiverGroupName", "Group1");
 		E1.setProperty("content", "Hii group1");
 		return new Object[][]{{E1,"1"},{null,"10"},{null,""}};
 	}

  @Test(dataProvider = "groupMsg")
  public void getGroupMsg(Entity result, String ID) {
	  Assert.assertEquals(result, GroupMessage.getGroupMsg(ID));
  }

  @Test
  public void getNewID() {
	  Assert.assertEquals(2,GroupMessage.getNewID());
  }

  
  // same (save)
  
  @Test
  public void saveMessage() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void sendMessage() {
    throw new RuntimeException("Test not implemented");
  }
}
