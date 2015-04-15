package com.FCI.SWE.ModelServices;

public class PagePost extends Post {
	int numberOfSeen;
	public PagePost(String owner, String content, String onWall, String privacy,String customUsers) {
		super(owner, content, onWall, privacy, customUsers);
		type = 3;
		numberOfSeen = -1;
	}
	
	public PagePost(String owner, String content, String onWall, String privacy) {
		super(owner, content, onWall, privacy);
		type = 3;
		numberOfSeen = -1;
	}

	@Override
	public int savePost() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void calculateNumberofSeen()
	{
		numberOfSeen = 0;
		// use ID to count number of seen 
		
	}
	
	
	

}
