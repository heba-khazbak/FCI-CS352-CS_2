package com.FCI.SWE.ModelServices;

public class FriendPost extends Post {

	public FriendPost(String owner, String content, String onWall,
			String privacy ,String customUsers) {
		super(owner, content, onWall, privacy , customUsers);
		type = 2;
	}

	@Override
	public int savePost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
