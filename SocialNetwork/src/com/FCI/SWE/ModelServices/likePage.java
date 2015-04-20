package com.FCI.SWE.ModelServices;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class LikePage extends Like {
	

	public LikePage(String userName, String ID) {
		super(userName, ID);
		// TODO Auto-generated constructor stub
		type = 5;
	}

	public void saveLiker( )
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("PageLike");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			// page like already existed for this user
			if(entity.getProperty("Liker").toString().equals(this.Liker) && 
				(entity.getProperty("pageID").toString().equals(this.LikedID)) )
			{
				return;
			}
		}
		
		Entity entity=new Entity("PageLike");
		entity.setProperty("Liker", this.Liker);
		entity.setProperty("pageID", this.LikedID);
		
		datastore.put(entity);
		
		notifyAllObservers();
	}
	
	public static boolean isLikePage (String PageID , String userName)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("PageLike");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		Like lp = new LikePage(userName, PageID);
		
		for (Entity entity : pq.asIterable()) 
		{
			// page like already existed for this user
			if(entity.getProperty("Liker").toString().equals(lp.Liker) && 
				(entity.getProperty("pageID").toString().equals(lp.LikedID)) )
			{
				return true;
			}
		}
		
		return false;
	}

}
