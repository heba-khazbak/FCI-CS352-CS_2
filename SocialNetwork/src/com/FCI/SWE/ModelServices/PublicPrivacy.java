package com.FCI.SWE.ModelServices;

import java.util.List;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PublicPrivacy extends Privacy {
	
	
	public Post canSeeUserPost(Entity entity ,String onWall,String currentUser)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
			String ID = entity.getProperty("ID").toString();
			String owner = entity.getProperty("owner").toString();
			String content = entity.getProperty("content").toString();
			String feeling = "";
	
			Query gaeQuery = new Query("feeling");
			PreparedQuery pq = datastore.prepare(gaeQuery);
			for (Entity entity2 : pq.asIterable())
			{
				if (entity2.getProperty("postID").toString().equals(ID))
				{
					feeling = entity2.getProperty("state").toString();
					break;
				}
			}
			
			Post p = new UserPost(owner,content,onWall,PUBLIC,feeling);
			p.setID(ID);
			return p;
			
		
	}
	
	@Override
	public Post canSeeFriendPost(Entity entity, String onWall,
			String currentUser) {
		String ID = entity.getProperty("ID").toString();
		String owner = entity.getProperty("owner").toString();
		String content = entity.getProperty("content").toString();
		
		Post p = new FriendPost(owner,content,onWall,PUBLIC);
		p.setID(ID);
		return p;
			
	}

	@Override
	public Post canSeePagePost(Entity entity, String onWall, String currentUser) {
		
		String ID = entity.getProperty("ID").toString();
		String owner = entity.getProperty("owner").toString();
		String content = entity.getProperty("content").toString();
		
		Post p = new PagePost(owner,content,onWall,PUBLIC);
		p.setID(ID);
		
		if (entity.getProperty("owner").toString().equals("currentUser"))
			((PagePost) p).calculateNumberofSeen();
			
		return p;
			
	}

	@Override
	public Post canSeeSharedPost(Entity entity, String onWall,
			String currentUser) {
		
			String ID = entity.getProperty("ID").toString();
			String owner = entity.getProperty("owner").toString();
			String content = entity.getProperty("content").toString();
			
			Post p = new PagePost(owner,content,onWall,PUBLIC);
			p.setID(ID);
			boolean ok = OriginalSharedPostPrivacy(ID , currentUser);
			
			if (ok)
				return p;
			
		return null;
	}
	
	
	
	

}
