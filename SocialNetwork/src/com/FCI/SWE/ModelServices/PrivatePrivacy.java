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
		if (friends || onWall.equals(currentUser))
		{
			return UserPost.getPost(entity);
		}
		return null;
	}

	@Override
	public Post canSeeFriendPost(Entity entity, String onWall,
			String currentUser) {
		// should be friends
		boolean friends = FriendRequest.isFriends(onWall, currentUser);
		if (friends || onWall.equals(currentUser))
		{
			return FriendPost.getPost(entity);
		}
		return null;
	}

	@Override
	public Post canSeePagePost(Entity entity, String onWall, String currentUser) {
		// should like the page
		boolean likePage = false; //Page.likePage(onWall, currentUser);
		if (likePage)
		{
			Post p = PagePost.getPost(entity);
			if (entity.getProperty("owner").toString().equals(currentUser))
				((PagePost) p).calculateNumberofSeen();		
			
			return p;
		}
		
		return null;
	}

	@Override
	public Post canSeeSharedPost(Entity entity, String onWall,
			String currentUser) {
		boolean ok = false;
		String ID = entity.getProperty("ID").toString();
		
		boolean friends = FriendRequest.isFriends(onWall, currentUser);
		if (friends || onWall.equals(currentUser))
			ok = true;
		
		Post p = SharePost.getPost(entity);
		
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
			return FriendRequest.isFriends(onWall, currentUser) || onWall.equals(currentUser);
		}
		else if (type.equals("3"))
		{
			//return Page.likePage(onWall, currentUser);
		}
		return false;
	}

}
