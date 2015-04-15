package com.FCI.SWE.ModelServices;

public class UserPost extends Post {
	
	String feeling;

	public UserPost(String owner, String content, String onWall, String privacy,
			String customUsers,String feeling) {
		super(owner, content, onWall, customUsers ,privacy);
		type = 1;
		this.feeling = feeling;
	}

	@Override
	public int savePost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
