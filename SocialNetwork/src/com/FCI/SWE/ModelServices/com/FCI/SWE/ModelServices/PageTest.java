package com.FCI.SWE.ModelServices.com.FCI.SWE.ModelServices;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import com.FCI.SWE.ModelServices.Page;

public class PageTest {
 

  @DataProvider ( name = "pageNames")
  public Object[][] dp1() {
    return new Object[][] {
      new Object[] { true, "Fluffy" },
      new Object[] { false, "zfsnjss" },
    };
  }

  @Test (dataProvider = "pageNames")
  public void checkPageEsistance(boolean result , String n) 
  {
		Assert.assertEquals(result, Page.checkPageEsistance(n));  
}
  
  @DataProvider ( name = "pageIDs")
  public Object[][] dp2() {
    return new Object[][] {
      new Object[] { "Sarah", "3" },
      new Object[] { null, "-4" },
    };
  }

  @Test (dataProvider = "pageIDs")
   public void getPageOwner(String  owner , String IDss) 
   {
 		Assert.assertEquals(owner, Page.getPageOwner(IDss));  
 }

  @Test (dataProvider = "pageNames")
  public void getPage_byName(boolean result , String n) 
  {
		Assert.assertNotNull( Page.getPage_byName(n));  
}

  @DataProvider ( name = "pages")
  public Object[][] dp3() {
    return new Object[][] {
      new Object[] { true, "Sarah", "Fluffy" },
      new Object[] { false, "Mohsen", "I love music" },
    };
  }

  @Test (dataProvider= "pages")
  public void isOwner( boolean result , String O,  String n) 
  {
		Assert.assertEquals(result, Page.isOwner(O, n));
}

}
