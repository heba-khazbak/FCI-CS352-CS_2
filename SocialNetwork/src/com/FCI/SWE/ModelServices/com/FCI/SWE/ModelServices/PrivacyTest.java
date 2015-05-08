package com.FCI.SWE.ModelServices.com.FCI.SWE.ModelServices;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.ModelServices.Privacy;
import com.FCI.SWE.ModelServices.PublicPrivacy;

public class PrivacyTest {
	
	Privacy privacy = new PublicPrivacy();
	
	@DataProvider(name = "OriginalPost")
	public static Object[][] OriginalPostCheck()
	{
		return new Object[][]{{true,"8" ,"Heba"}, {true,"8" ,"Ziad"},{true,"16" ,"Sarah"},
				{false,"17" ,"Sarah"},{false,"17" ,""}};
	}

  @Test(dataProvider = "OriginalPost")
  public void OriginalSharedPostPrivacy(boolean result ,String ID , String currentUser) {
	  Assert.assertEquals(result, privacy.OriginalSharedPostPrivacy(ID , currentUser));
  }
}
