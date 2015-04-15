package com.FCI.SWE.ModelServices;

public abstract class Post {
	String ID;
	String owner;
	String content;
	String onWall;
	int type;
	String privacy;
	
	public Post(String owner ,String content , String onWall,String privacy)
	{
		this.owner = owner;
		this.content = content;
		this.onWall = onWall;
		this.privacy = privacy;
	}
	
	public abstract int savePost();
	// return postID

}
