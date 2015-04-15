package com.FCI.SWE.ModelServices;

public class UserPost extends Post {
	
	String feeling;
	boolean isfeeling;

	public UserPost(String owner, String content, String onWall, String privacy,
			String customUsers,String feeling) {
		super(owner, content, onWall, customUsers ,privacy);
		type = 1;
		this.feeling = feeling;
		
		if (feeling.equals(""))
			isfeeling = false;
		else
			isfeeling = true;
	}
	
	public UserPost(String owner, String content, String onWall, String privacy,
			String feeling) {
		super(owner, content, onWall,privacy);
		type = 1;
		this.feeling = feeling;
		
		if (feeling.equals(""))
			isfeeling = false;
		else
			isfeeling = true;
	}

	@Override
	public int savePost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
