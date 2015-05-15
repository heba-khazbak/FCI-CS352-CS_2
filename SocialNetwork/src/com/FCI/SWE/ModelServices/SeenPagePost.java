package com.FCI.SWE.ModelServices;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * SeenPagePost class
 * This class act as a model and is responsible for handling who sees the page post 
 * @author Heba
 * @version 1.0
 * @since 20-4-2015
 *
 */

public class SeenPagePost {
	
	String postID;
	String userName;
	/**
	 * Constructor
	 * @param postID the post ID
	 * @param userName the current userName who sees the post
	 */
	public SeenPagePost(String postID , String userName)
	{
		this.postID = postID;
		this.userName = userName;
	}
	
	/**
	 * This function saves the data ( postID , user name who sees the post)
	 * in the datastore. It also checks that this current user doesn't already seen 
	 * the post , if so it does nothing.
	 */
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
	
	/**
	 * This static method will get number of users who saw specific post
	 * @param ID Post ID
	 * @return number of users who saw this post
	 */
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
