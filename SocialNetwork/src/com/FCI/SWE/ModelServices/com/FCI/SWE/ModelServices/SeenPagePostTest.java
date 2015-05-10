package com.FCI.SWE.ModelServices.com.FCI.SWE.ModelServices;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.ModelServices.SeenPagePost;

public class SeenPagePostTest {

	  @DataProvider ( name = "pageSeen")
	  public Object[][] dp1() {
	    return new Object[][] {
	      new Object[] { 3, "3"},
	      new Object[] { 1, "1" },
	    };
	  }


	
  @Test ( dataProvider = "pageSeen")
  public void getNumberOfSeen(int num,  String ID) 
  {
		Assert.assertEquals(num, SeenPagePost.getNumberOfSeen(ID));
}
}
