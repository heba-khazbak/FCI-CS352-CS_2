package com.FCI.SWE.ModelServices;

public class SharePost extends Post {
	
	String originalPostID;
	public SharePost(String owner, String content, String onWall, String privacy,String originalPostID
			,String customUsers) {
		super(owner, content, onWall, privacy,customUsers);
		this.originalPostID = originalPostID;
	}

	@Override
	public String savePost() {
		// TODO Auto-generated method stub
		return null;
	}

}
