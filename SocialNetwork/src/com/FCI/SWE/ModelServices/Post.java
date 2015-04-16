package com.FCI.SWE.ModelServices;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

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
	

	public static  Vector<Post> getPostsForTimeLine(String onWall, String currentUser)
	{
		Vector<Post> allPosts = new Vector<Post>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Privacy myPrivacy;
		Post post = null;
		
		for (Entity entity : pq.asIterable()) {
			post = null;
			if (entity.getProperty("onWall").toString().equals("onWall"))
			{
				if (entity.getProperty("privacy").toString().equals(Privacy.PUBLIC)) {
					myPrivacy = new PublicPrivacy();
					
					if (entity.getProperty("type").toString().equals("1"))
					{
						post = myPrivacy.canSeeUserPost(entity ,onWall,currentUser);		
					}
					else if (entity.getProperty("type").toString().equals("2"))
					{
						post = myPrivacy.canSeeFriendPost(entity ,onWall,currentUser);
					}
					else if (entity.getProperty("type").toString().equals("3"))
					{
						post = myPrivacy.canSeePagePost(entity ,onWall,currentUser);
					}
					else if (entity.getProperty("type").toString().equals("4"))
					{
						post = myPrivacy.canSeeSharedPost(entity ,onWall,currentUser);
					}
					
				}
				
				else if (entity.getProperty("privacy").toString().equals(Privacy.PRIVATE))
				{
					myPrivacy = new PrivatePrivacy();
					
					if (entity.getProperty("type").toString().equals("1"))
					{
						post = myPrivacy.canSeeUserPost(entity ,onWall,currentUser);		
					}
					else if (entity.getProperty("type").toString().equals("2"))
					{
						post = myPrivacy.canSeeFriendPost(entity ,onWall,currentUser);
					}
					else if (entity.getProperty("type").toString().equals("3"))
					{
						post = myPrivacy.canSeePagePost(entity ,onWall,currentUser);
					}
					else if (entity.getProperty("type").toString().equals("4"))
					{
						post = myPrivacy.canSeeSharedPost(entity ,onWall,currentUser);
					}
				}
				else if (entity.getProperty("privacy").toString().equals(Privacy.CUSTOM))
				{
					myPrivacy = new CustomPrivacy();
					
					if (entity.getProperty("type").toString().equals("1"))
					{
						post = myPrivacy.canSeeUserPost(entity ,onWall,currentUser);		
					}
					else if (entity.getProperty("type").toString().equals("2"))
					{
						post = myPrivacy.canSeeFriendPost(entity ,onWall,currentUser);
					}
					else if (entity.getProperty("type").toString().equals("3"))
					{
						post = myPrivacy.canSeePagePost(entity ,onWall,currentUser);
					}
					else if (entity.getProperty("type").toString().equals("4"))
					{
						post = myPrivacy.canSeeSharedPost(entity ,onWall,currentUser);
					}
					
				}
			}
			
			
			if (post != null)
				allPosts.add(post);
		}
		return null;
		
	}


}
