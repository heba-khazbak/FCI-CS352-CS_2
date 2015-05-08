package com.FCI.SWE.ModelServices.com.FCI.SWE.ModelServices;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.ModelServices.FriendPost;
import com.FCI.SWE.ModelServices.PagePost;
import com.FCI.SWE.ModelServices.Post;
import com.FCI.SWE.ModelServices.PublicPrivacy;
import com.FCI.SWE.ModelServices.UserPost;
import com.google.appengine.api.datastore.Entity;

public class PublicPrivacyTest {

	PublicPrivacy myPublic = new PublicPrivacy();
	
	
	@DataProvider(name = "UserPost")
	public static Object[][] userPostCheck()
	{
		Entity E1 = new Entity("post",1);
		E1.setProperty("ID", "1");
		E1.setProperty("owner", "Ziad");
		E1.setProperty("content", "Testing hashtag system #test #SWE #WTH");
		E1.setProperty("onWall", "Ziad");
		E1.setProperty("type", 1);
		E1.setProperty("privacy", "public");
		Post p1 = new UserPost("Ziad","Testing hashtag system #test #SWE #WTH","Ziad","public","");
		return new Object[][]{{p1,E1,"Ziad" , "Sarah"},{p1,E1,"Ziad" , "Heba"},{p1,E1,"Ziad" , "Ziad"},
				{p1,E1,"Ziad" , ""}};
	}

	@Test(dataProvider = "UserPost")
	  public void canSeeUserPost(Post result,Entity entity, String onWall,String currentUser) {
		Assert.assertEquals(result, myPublic.canSeeUserPost(entity, onWall , currentUser));
	  }
	
	@DataProvider(name = "FriendPost")
	public static Object[][] friendPostCheck()
	{
		Entity E1 = new Entity("post",12);
		E1.setProperty("ID", "12");
		E1.setProperty("owner", "Heba");
		E1.setProperty("content", "mypost");
		E1.setProperty("onWall", "Dalia");
		E1.setProperty("type", 2);
		E1.setProperty("privacy", "public");
		Post p1 = new FriendPost("Heba","mypost","Dalia","public");
		return new Object[][]{{p1,E1,"Dalia" , "Sarah"},{p1,E1,"Dalia" , "Heba"},{p1,E1,"Dalia" , "Ziad"},
				{p1,E1,"Dalia" , ""}};
	}
  @Test(dataProvider = "FriendPost")
  public void canSeeFriendPost(Post result,Entity entity, String onWall,String currentUser) {
	  Assert.assertEquals(result, myPublic.canSeeFriendPost(entity, onWall , currentUser));
  }
  
  
  @DataProvider(name = "PagePost")
	public static Object[][] pagePostCheck()
	{
		Entity E1 = new Entity("post",13);
		E1.setProperty("ID", "13");
		E1.setProperty("owner", "Heba");
		E1.setProperty("content", "public post");
		E1.setProperty("onWall", "Page1");
		E1.setProperty("type", 3);
		E1.setProperty("privacy", "public");
		Post p1 = new PagePost("Heba","public post","Page1","public");
		return new Object[][]{{p1,E1,"Page1" , "Sarah"},{p1,E1,"Page1" , "Heba"},{p1,E1,"Page1" , "Dalia"},
				{p1,E1,"Page1" , ""}};
	}
  
  @Test(dataProvider = "PagePost")
  public void canSeePagePost(Post result,Entity entity, String onWall,String currentUser) {
	  Assert.assertEquals(result, myPublic.canSeePagePost(entity, onWall , currentUser));
  }

  

  @DataProvider(name = "SharedPost")
 	public static Object[][] sharedPostCheck()
 	{
	  
 		Entity E1 = new Entity("post",8);
 		E1.setProperty("ID", "8");
 		E1.setProperty("owner", "Heba");
 		E1.setProperty("content", "test");
 		E1.setProperty("onWall", "Heba");
 		E1.setProperty("type", 4);
 		E1.setProperty("privacy", "public");
 		Post p1 = new PagePost("Heba","test","Heba","public");
 		return new Object[][]{{p1,E1,"Heba" , "Sarah"},{p1,E1,"Heba" , "Heba"},{p1,E1,"Heba" , "Dalia"},
 				{p1,E1,"Heba" , ""}};
 	}
   
   @Test(dataProvider = "SharedPost")
  public void canSeeSharedPost(Post result,Entity entity, String onWall,String currentUser) {
	   Assert.assertEquals(result, myPublic.canSeeSharedPost(entity, onWall , currentUser));
  }
}
