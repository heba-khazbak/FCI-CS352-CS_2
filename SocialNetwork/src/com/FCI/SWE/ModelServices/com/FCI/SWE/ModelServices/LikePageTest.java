package com.FCI.SWE.ModelServices.com.FCI.SWE.ModelServices;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.ModelServices.LikePage;

public class LikePageTest {

	  @DataProvider ( name = "pageLikes")
	  public Object[][] dp1() {
	    return new Object[][] {
	      new Object[] { true, "3", "Sarah" },
	      new Object[] { false, "3", "Heba" },
	    };
	  }


  @Test ( dataProvider = "pageLikes")
  public void isLikePage( boolean result , String ID,  String UserName) 
  {
		Assert.assertEquals(result, LikePage.isLikePage(ID, UserName));
}

}
