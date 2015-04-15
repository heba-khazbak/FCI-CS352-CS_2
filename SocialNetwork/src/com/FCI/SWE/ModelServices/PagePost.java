package com.FCI.SWE.ModelServices;

public class PagePost extends Post {

	public PagePost(String owner, String content, String onWall, String privacy) {
		super(owner, content, onWall, privacy);
		type = 3;
	}

	@Override
	public int savePost() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	

}
