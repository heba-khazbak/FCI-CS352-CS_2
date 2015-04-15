package com.FCI.SWE.ModelServices;

public class UserPost extends Post {
	
	String feeling;

	public UserPost(String owner, String content, String onWall, String privacy,String feeling) {
		super(owner, content, onWall, privacy);
		type = 1;
		this.feeling = feeling;
	}

	@Override
	public int savePost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
