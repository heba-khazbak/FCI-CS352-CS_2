package com.FCI.SWE.ModelServices;

public abstract class Post {
	String ID;
	String owner;
	String content;
	String onWall;
	int type;
	String privacy;
	String customUsers;
	
	public Post(String owner ,String content , String onWall,String privacy, String customUsers)
	{
		this.owner = owner;
		this.content = content;
		this.onWall = onWall;
		this.privacy = privacy;
		this.customUsers = customUsers;
	}
	
	public Post(String owner ,String content , String onWall,String privacy)
	{
		this.owner = owner;
		this.content = content;
		this.onWall = onWall;
		this.privacy = privacy;
	}
	public void setID (String ID)
	{
		this.ID = ID;
	}
	
	public abstract String savePost();
	// return postID
	// in this function .. if privacy == custom then call 
	//CustomPrivacy.saveCustomUsers(postID , jsonArrayofUsers);

}
