package com.FCI.SWE.ModelServices;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class SeenPagePost {
	
	String postID;
	String userName;
	public SeenPagePost(String postID , String userName)
	{
		this.postID = postID;
		this.userName = userName;
	}
	
	public void saveSeen() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("postSeen");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			// this user  already seen this post
			if(entity.getProperty("postID").toString().equals(this.postID) && 
				(entity.getProperty("userName").toString().equals(this.userName)) )
			{
				return;
			}
		}
		
		Entity entity=new Entity("postSeen");
		entity.setProperty("postID", this.postID);
		entity.setProperty("userName", this.userName);
		
		datastore.put(entity);
	}
	
	public static int getNumberOfSeen(String ID)
	{
		int counter = 0;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("postSeen");
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
