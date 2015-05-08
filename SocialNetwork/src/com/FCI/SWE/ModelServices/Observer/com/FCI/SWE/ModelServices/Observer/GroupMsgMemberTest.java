package com.FCI.SWE.ModelServices.Observer.com.FCI.SWE.ModelServices.Observer;

import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.FCI.SWE.ModelServices.Observer.GroupMsgMember;

public class GroupMsgMemberTest {
	
	@DataProvider(name = "GroupNames")
	public static Object[][] GroupNames()
	{
		Set<String> s1 = new HashSet <String>();
		Set<String> s2 = new HashSet <String>();
		s1.add("Dalia");
		s1.add("Heba");
		
		return new Object[][]{{s1 , "Group1"},{s2 , "notGroup"},{s2 , ""}};
	}

  @Test
  public void getAllGroupNames() {
	  Set<String> s = new HashSet <String>();
	   s.add("Group1");
	   Assert.assertEquals(s, GroupMsgMember.getAllGroupNames());
  }

  @Test (dataProvider = "GroupNames")
  public void getAllMembers(Set<String> Result ,String GroupName) {
	  Assert.assertEquals(Result, GroupMsgMember.getAllMembers(GroupName));
  }

  @Test
  public void saveGroupMsgMember() {
    throw new RuntimeException("Test not implemented");
  }
}
