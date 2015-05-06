package com.FCI.SWE.ModelServices.com.FCI.SWE.ModelServices;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.ModelServices.CustomPrivacy;
import com.FCI.SWE.ModelServices.FriendPost;
import com.FCI.SWE.ModelServices.PagePost;
import com.FCI.SWE.ModelServices.Post;
import com.FCI.SWE.ModelServices.UserPost;
import com.google.appengine.api.datastore.Entity;



public class CustomPrivacyTest {
	
	CustomPrivacy custom = new CustomPrivacy();
	
	
	@DataProvider(name = "UserPost")
	public static Object[][] userPostCheck()
	{
		Entity E1 = new Entity("post",7);
		E1.setProperty("ID", "7");
		E1.setProperty("owner", "Heba");
		E1.setProperty("content", "trying custom post");
		E1.setProperty("onWall", "Heba");
		E1.setProperty("type", 1);
		E1.setProperty("privacy", "custom");
		Post p1 = new UserPost("Heba","trying custom post","Heba","custom","");
		return new Object[][]{{p1,E1,"Heba" , "Sarah"},{p1,E1,"Heba" , "Heba"},{null,E1,"Heba" , "Ziad"},
				{null,E1,"Heba" , ""}};
	}

	@Test(dataProvider = "UserPost")
	  public void canSeeUserPost(Post result,Entity entity, String onWall,String currentUser) {
		Assert.assertEquals(result, custom.canSeeUserPost(entity, onWall , currentUser));
	  }
	
	@DataProvider(name = "FriendPost")
	public static Object[][] friendPostCheck()
	{
		Entity E1 = new Entity("post",9);
		E1.setProperty("ID", "9");
		E1.setProperty("owner", "Heba");
		E1.setProperty("content", "Hello");
		E1.setProperty("onWall", "Ziad");
		E1.setProperty("type", 2);
		E1.setProperty("privacy", "custom");
		Post p1 = new FriendPost("Heba","Hello","Ziad","custom");
		return new Object[][]{{null,E1,"Ziad" , "Sarah"},{p1,E1,"Ziad" , "Heba"},{p1,E1,"Ziad" , "Ziad"},
				{null,E1,"Ziad" , ""}};
	}
  @Test(dataProvider = "FriendPost")
  public void canSeeFriendPost(Post result,Entity entity, String onWall,String currentUser) {
	  Assert.assertEquals(result, custom.canSeeFriendPost(entity, onWall , currentUser));
  }
  
  
  @DataProvider(name = "PagePost")
	public static Object[][] pagePostCheck()
	{
		Entity E1 = new Entity("post",10);
		E1.setProperty("ID", "10");
		E1.setProperty("owner", "Heba");
		E1.setProperty("content", "post on page");
		E1.setProperty("onWall", "Page1");
		E1.setProperty("type", 3);
		E1.setProperty("privacy", "custom");
		Post p1 = new PagePost("Heba","Hello","Page1","custom");
		return new Object[][]{{null,E1,"Page1" , "Sarah"},{p1,E1,"Page1" , "Heba"},{p1,E1,"Page1" , "Dalia"},
				{null,E1,"Page1" , ""}};
	}
  
  @Test(dataProvider = "PagePost")
  public void canSeePagePost(Post result,Entity entity, String onWall,String currentUser) {
	  Assert.assertEquals(result, custom.canSeePagePost(entity, onWall , currentUser));
  }

  

  @DataProvider(name = "SharedPost")
 	public static Object[][] sharedPostCheck()
 	{
	  
 		Entity E1 = new Entity("post",11);
 		E1.setProperty("ID", "11");
 		E1.setProperty("owner", "Heba");
 		E1.setProperty("content", "sharing");
 		E1.setProperty("onWall", "Heba");
 		E1.setProperty("type", 4);
 		E1.setProperty("privacy", "custom");
 		Post p1 = new PagePost("Heba","sharing","Heba","custom");
 		return new Object[][]{{null,E1,"Heba" , "Sarah"},{p1,E1,"Heba" , "Heba"},{p1,E1,"Heba" , "Dalia"},
 				{null,E1,"Heba" , ""}};
 	}
   
   @Test(dataProvider = "SharedPost")
  public void canSeeSharedPost(Post result,Entity entity, String onWall,String currentUser) {
	   Assert.assertEquals(result, custom.canSeeSharedPost(entity, onWall , currentUser));
  }

  

  @DataProvider(name = "inCustom")
	public static Object[][] inCustomCheck()
	{
		return new Object[][]{{true,"7" , "Sarah"},{false,"7" , "Ziad"},{false,"2","Dalia"},
				{true,"7" , "Heba"},{false,"" , ""}};
	}
  
  @Test(dataProvider = "inCustom")
  public void isInCustom(boolean result , String postID , String  userName) {
	  Assert.assertEquals(result, CustomPrivacy.isInCustom(postID, userName));
  }

  /*@Test
  public void saveCustomUsers() {
    throw new RuntimeException("Test not implemented");
  }
  
  @Test
  public void ParseUsersToVector() {
    
  }*/
}
