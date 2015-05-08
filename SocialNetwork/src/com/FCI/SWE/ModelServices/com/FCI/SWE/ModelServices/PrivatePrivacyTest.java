package com.FCI.SWE.ModelServices.com.FCI.SWE.ModelServices;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.ModelServices.FriendPost;
import com.FCI.SWE.ModelServices.PagePost;
import com.FCI.SWE.ModelServices.Post;
import com.FCI.SWE.ModelServices.PrivatePrivacy;
import com.FCI.SWE.ModelServices.UserPost;
import com.google.appengine.api.datastore.Entity;

public class PrivatePrivacyTest {

	PrivatePrivacy myPrivate = new PrivatePrivacy();
	
	
	@DataProvider(name = "UserPost")
	public static Object[][] userPostCheck()
	{
		Entity E1 = new Entity("post",5);
		E1.setProperty("ID", "5");
		E1.setProperty("owner", "Ziad");
		E1.setProperty("content", "blabla #SWE");
		E1.setProperty("onWall", "Ziad");
		E1.setProperty("type", 1);
		E1.setProperty("privacy", "private");
		Post p1 = new UserPost("Ziad","blabla #SWE","Ziad","private","");
		return new Object[][]{{p1,E1,"Ziad" , "Ziad"},{p1,E1,"Ziad" , "Heba"},{null,E1,"Ziad" , "Sarah"},
				{null,E1,"Ziad" , ""}};
	}

	@Test(dataProvider = "UserPost")
	  public void canSeeUserPost(Post result,Entity entity, String onWall,String currentUser) {
		Assert.assertEquals(result, myPrivate.canSeeUserPost(entity, onWall , currentUser));
	  }
	
	@DataProvider(name = "FriendPost")
	public static Object[][] friendPostCheck()
	{
		Entity E1 = new Entity("post",15);
		E1.setProperty("ID", "9");
		E1.setProperty("owner", "Heba");
		E1.setProperty("content", "Hii Dalia");
		E1.setProperty("onWall", "Dalia");
		E1.setProperty("type", 2);
		E1.setProperty("privacy", "private");
		Post p1 = new FriendPost("Heba","Hii Dalia","Dalia","private");
		return new Object[][]{{null,E1,"Dalia" , "Sarah"},{p1,E1,"Dalia" , "Heba"},{p1,E1,"Dalia" , "Ziad"},
				{p1,E1,"Dalia" , "Dalia"} ,{null,E1,"Dalia" , ""}};
	}
  @Test(dataProvider = "FriendPost")
  public void canSeeFriendPost(Post result,Entity entity, String onWall,String currentUser) {
	  Assert.assertEquals(result, myPrivate.canSeeFriendPost(entity, onWall , currentUser));
  }
  
  
  @DataProvider(name = "PagePost")
	public static Object[][] pagePostCheck()
	{
		Entity E1 = new Entity("post",14);
		E1.setProperty("ID", "14");
		E1.setProperty("owner", "Heba");
		E1.setProperty("content", "my private post");
		E1.setProperty("onWall", "Page1");
		E1.setProperty("type", 3);
		E1.setProperty("privacy", "private");
		Post p1 = new PagePost("Heba","my private post","Page1","private");
		return new Object[][]{{null,E1,"Page1" , "Sarah"},{p1,E1,"Page1" , "Heba"},{p1,E1,"Page1" , "Ziad"},
				{null,E1,"Page1" , ""}};
	}
  
  @Test(dataProvider = "PagePost")
  public void canSeePagePost(Post result,Entity entity, String onWall,String currentUser) {
	  Assert.assertEquals(result, myPrivate.canSeePagePost(entity, onWall , currentUser));
  }

  

  @DataProvider(name = "SharedPost")
 	public static Object[][] sharedPostCheck()
 	{
	  
 		Entity E1 = new Entity("post",16);
 		E1.setProperty("ID", "16");
 		E1.setProperty("owner", "Heba");
 		E1.setProperty("content", "sharingg");
 		E1.setProperty("onWall", "Heba");
 		E1.setProperty("type", 4);
 		E1.setProperty("privacy", "private");
 		Post p1 = new PagePost("Heba","sharingg","Heba","private");
 		return new Object[][]{{p1,E1,"Heba" , "Ziad"},{p1,E1,"Heba" , "Heba"},{null,E1,"Heba" , "Sarah"},
 				{p1,E1,"Ziad" , "Dalia"} ,{null,E1,"Ziad" , ""}};
 	}
   
   @Test(dataProvider = "SharedPost")
  public void canSeeSharedPost(Post result,Entity entity, String onWall,String currentUser) {
	   Assert.assertEquals(result, myPrivate.canSeeSharedPost(entity, onWall , currentUser));
  }
   
   
   @DataProvider(name = "HandlePost")
	public static Object[][] HandlePostCheck()
	{
		return new Object[][]{{true,"Heba" ,"Heba", "Ziad" , 1}, {true,"Heba" ,"Dalia", "Ziad" , 2},
				{true,"Heba" ,"Page1", "Ziad" , 3}, {true,"Heba" ,"Heba", "Ziad" , 4},
				{false,"Heba" ,"Heba", "" , 1},{false,"Heba" ,"Dalia", "Sarah" , 2},
				{false,"Heba" ,"Page1", "Dalia" , 3},{false,"Heba" ,"Heba", "Sarah" , 4}};
	}
   
  @Test(dataProvider = "HandlePost")
  public void handlingSharedPost(boolean result ,String owner,String onWall, String currentUser , String type) {
	  Assert.assertEquals(result, PrivatePrivacy.handlingSharedPost(owner,onWall , currentUser , type));
  }
}
