package com.FCI.SWE.ModelServices;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public abstract class Privacy {
	public static final String PUBLIC = "public";
	public static final String PRIVATE = "private";
	public static final String CUSTOM = "custom";
	
	public abstract Post canSeeUserPost(Entity entity, String onWall,String currentUser);
	public abstract Post canSeeFriendPost(Entity entity, String onWall,String currentUser);
	public abstract Post canSeePagePost(Entity entity, String onWall,String currentUser);
	public abstract Post canSeeSharedPost(Entity entity, String onWall,String currentUser);
	
	public boolean OriginalSharedPostPrivacy(String ID , String currentUser)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		boolean ok = false;
		String originalID = "";
		String originalPrivacy = "";
		String originalOnWall= "";
		String originalType = "";
		String originalOwner = "";
		
		Query gaeQuery = new Query("sharing");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity2 : pq.asIterable())
		{
			if (entity2.getProperty("postID").toString().equals(ID))
			{
				originalID = entity2.getProperty("originalID").toString(); 
				break;
			}
		}
		
		// search for original post to get it's privacy
		gaeQuery = new Query("post");
		 pq = datastore.prepare(gaeQuery);
		for (Entity entity2 : pq.asIterable())
		{
			if (entity2.getProperty("postID").toString().equals(originalID))
			{
				originalPrivacy = entity2.getProperty("privacy").toString(); 
				originalOwner = entity2.getProperty("owner").toString(); 
				originalOnWall = entity2.getProperty("onWall").toString(); 
				originalType =  entity2.getProperty("type").toString(); 
				break;
			}
		}
		
		// can this user sees this post ?
		if (originalPrivacy.equals(PUBLIC))
		{
			ok = true;
		}
		else if (originalPrivacy.equals(PRIVATE))
		{
			ok = PrivatePrivacy.handlingSharedPost(originalOwner,originalOnWall,currentUser,originalType);
		}
		else if (originalPrivacy.equals(CUSTOM))
		{
			ok = CustomPrivacy.isInCustom(originalID ,currentUser);
	
		}
		return ok;
	}

}
