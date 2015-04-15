package com.FCI.SWE.ModelServices;

public class SharePost extends Post {
	
	String originalPostID;
	public SharePost(String owner, String content, String onWall, String privacy,String originalPostID) {
		super(owner, content, onWall, privacy);
		this.originalPostID = originalPostID;
	}

	@Override
	public boolean savePost() {
		// TODO Auto-generated method stub
		return false;
	}

}
