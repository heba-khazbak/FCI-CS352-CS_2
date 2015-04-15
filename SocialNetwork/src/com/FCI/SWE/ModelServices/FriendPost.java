package com.FCI.SWE.ModelServices;

public class FriendPost extends Post {

	public FriendPost(String owner, String content, String onWall,
			String privacy) {
		super(owner, content, onWall, privacy);
		type = 2;
	}

	@Override
	public boolean savePost() {
		// TODO Auto-generated method stub
		return false;
	}

}
