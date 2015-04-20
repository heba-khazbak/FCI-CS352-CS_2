package com.FCI.SWE.ModelServices;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class LikePost extends Like {

	public LikePost(String userName, String ID) {
		super(userName, ID);
		// TODO Auto-generated constructor stub
		type = 6;
	}

	@Override
	public void saveLiker() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("likePost");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			// page like already existed for this user
			if(entity.getProperty("Liker").toString().equals(this.Liker) && 
				(entity.getProperty("postID").toString().equals(this.LikedID)) )
			{
				return;
			}
		}
		
		Entity entity=new Entity("likePost");
		entity.setProperty("Liker", this.Liker);
		entity.setProperty("postID", this.LikedID);
		
		datastore.put(entity);
		notifyAllObservers();
		
	}
	
	public static boolean isLikePost (String postID , String userName)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("likePost");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		Like lp = new LikePage(userName, postID);
		
		for (Entity entity : pq.asIterable()) 
		{
			// page like already existed for this user
			if(entity.getProperty("Liker").toString().equals(lp.Liker) && 
				(entity.getProperty("postID").toString().equals(lp.LikedID)) )
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static int getNumberOfLikes(String ID)
	{
		int counter = 0;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("likePost");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			// this user  already seen this post
			if(entity.getProperty("postID").toString().equals(ID))
			{
				counter++;
			}
		}
		
		return counter;
	}

}
