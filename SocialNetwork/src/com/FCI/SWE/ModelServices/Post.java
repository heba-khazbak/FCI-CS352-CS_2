package com.FCI.SWE.ModelServices;

import java.util.Vector;




import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public abstract class Post {
	public String ID;
	public String owner;
	public String content;
	public String onWall;
	public int type;
	public String privacy;
	public String customUsers;
	
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
	
	public static String getOwner (String PostID)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("ID").toString().equals(PostID))
				return entity.getProperty("owner").toString();
		}
		
		return null;
		
	}
	
	public static  Vector<Post> getAllPostsForUser(String currentUser)
	{
		Vector<Post> allPosts = new Vector<Post>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Privacy myPrivacy = null;
		Post post = null;
		
		for (Entity entity : pq.asIterable()) {
			post = null;
		
			String onWall = entity.getProperty("onWall").toString();
			
			if (entity.getProperty("privacy").toString().equals(Privacy.PUBLIC)) 
				myPrivacy = new PublicPrivacy();
			else if (entity.getProperty("privacy").toString().equals(Privacy.PRIVATE))
				myPrivacy = new PrivatePrivacy();
			else if (entity.getProperty("privacy").toString().equals(Privacy.CUSTOM))
				myPrivacy = new CustomPrivacy();
				
			if (entity.getProperty("type").toString().equals("1"))
				post = myPrivacy.canSeeUserPost(entity ,onWall,currentUser);		
				
			else if (entity.getProperty("type").toString().equals("2"))
				post = myPrivacy.canSeeFriendPost(entity ,onWall,currentUser);
	
			else if (entity.getProperty("type").toString().equals("3"))
				post = myPrivacy.canSeePagePost(entity ,onWall,currentUser);
			
			else if (entity.getProperty("type").toString().equals("4"))
				post = myPrivacy.canSeeSharedPost(entity ,onWall,currentUser);

			if (post != null)
				allPosts.add(post);
		}
		return allPosts;
		
	}
	
	public static  Vector<String> getPostsForTimeLine(String onWall, String currentUser)
	{
		Vector<String> allPosts = new Vector<String>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Privacy myPrivacy = null;
		Post post = null;
		
		for (Entity entity : pq.asIterable()) {
			post = null;
			
		if (entity.getProperty("onWall").toString().equals(onWall))
		{
			if (entity.getProperty("privacy").toString().equals(Privacy.PUBLIC)) 
				myPrivacy = new PublicPrivacy();
			else if (entity.getProperty("privacy").toString().equals(Privacy.PRIVATE))
				myPrivacy = new PrivatePrivacy();
			else if (entity.getProperty("privacy").toString().equals(Privacy.CUSTOM))
				myPrivacy = new CustomPrivacy();
				
			if (entity.getProperty("type").toString().equals("1"))
				post = myPrivacy.canSeeUserPost(entity ,onWall,currentUser);		
				
			else if (entity.getProperty("type").toString().equals("2"))
				post = myPrivacy.canSeeFriendPost(entity ,onWall,currentUser);
	
			else if (entity.getProperty("type").toString().equals("3"))
				post = myPrivacy.canSeePagePost(entity ,onWall,currentUser);
			
			else if (entity.getProperty("type").toString().equals("4"))
				post = myPrivacy.canSeeSharedPost(entity ,onWall,currentUser);

		}
			
			if (post != null)
				allPosts.add(PostFilter.formatPost(post));
		}
		return allPosts;
		
	}

	
	public static Post getPostbyID(String postID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for(Entity entity:pq.asIterable()){
			if(entity.getProperty("ID").toString().equals(postID)){
				int postType=Integer.parseInt(entity.getProperty("type").toString());
				if(postType==1)return UserPost.getPost(entity);
				else if(postType==2)return FriendPost.getPost(entity);
				else if(postType==3)return PagePost.getPost(entity);
				else if(postType==4)return SharePost.getPost(entity);
			}
		}
		return null;
	}




}
