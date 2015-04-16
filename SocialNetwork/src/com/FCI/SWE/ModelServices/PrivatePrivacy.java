package com.FCI.SWE.ModelServices;

import java.util.Vector;

import com.FCI.SWE.ModelServices.Observer.FriendRequest;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PrivatePrivacy extends Privacy {
	
	
	@Override
	public Post canSeeUserPost(Entity entity, String onWall,
			String currentUser) {
		// should be friends
		
		boolean friends = FriendRequest.isFriends(onWall, currentUser);
		if (friends)
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
				
				Post p = new UserPost(owner,content,onWall,PRIVATE,feeling);
				p.setID(ID);
				return p;
		}
		return null;
	}

	@Override
	public Post canSeeFriendPost(Entity entity, String onWall,
			String currentUser) {
		// should be friends
		boolean friends = FriendRequest.isFriends(onWall, currentUser);
		if (friends)
		{
			String ID = entity.getProperty("ID").toString();
			String owner = entity.getProperty("owner").toString();
			String content = entity.getProperty("content").toString();
			
			Post p = new FriendPost(owner,content,onWall,PRIVATE);
			p.setID(ID);
			return p;
		}
		return null;
	}

	@Override
	public Post canSeePagePost(Entity entity, String onWall, String currentUser) {
		// should like the page
		boolean likePage = false; //Page.likePage(onWall, currentUser);
		if (likePage)
		{
			String ID = entity.getProperty("ID").toString();
			String owner = entity.getProperty("owner").toString();
			String content = entity.getProperty("content").toString();
			
			Post p = new PagePost(owner,content,onWall,PRIVATE);
			p.setID(ID);
			
			if (entity.getProperty("owner").toString().equals("currentUser"))
				((PagePost) p).calculateNumberofSeen();
				
			return p;
		}
		
		return null;
	}

	@Override
	public Post canSeeSharedPost(Entity entity, String onWall,
			String currentUser) {
		
		String ID = entity.getProperty("ID").toString();
		String owner = entity.getProperty("owner").toString();
		String content = entity.getProperty("content").toString();
		
		Post p = new PagePost(owner,content,onWall,PRIVATE);
		p.setID(ID);
		
		// sharedPost type
		String newType = "";
		boolean ok = handlingSharedPost (onWall ,currentUser , newType);
		if (ok)
			ok = OriginalSharedPostPrivacy(ID , currentUser);
		
		if (ok)
			return p;
		
	return null;
		
	}
	
	public static boolean handlingSharedPost(String onWall, String currentUser , String type)
	{
		if (type.equals("1") || type.equals("2"))
		{
			return FriendRequest.isFriends(onWall, currentUser);
		}
		else if (type.equals("3"))
		{
			//return Page.likePage(onWall, currentUser);
		}
		return false;
	}

}
