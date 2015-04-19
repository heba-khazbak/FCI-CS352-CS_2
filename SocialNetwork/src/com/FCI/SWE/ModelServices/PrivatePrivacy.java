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
		String owner = entity.getProperty("owner").toString();
		boolean friends = FriendRequest.isFriends(owner, currentUser);
		if (friends || owner.equals(currentUser))
		{
			return UserPost.getPost(entity);
		}
		return null;
	}

	@Override
	public Post canSeeFriendPost(Entity entity, String onWall,
			String currentUser) {
		// should be friends
		String owner = entity.getProperty("owner").toString();
		boolean friends = FriendRequest.isFriends(owner, currentUser);
		if (friends || owner.equals(currentUser))
		{
			return FriendPost.getPost(entity);
		}
		return null;
	}

	@Override
	public Post canSeePagePost(Entity entity, String onWall, String currentUser) {
		// should like the page
		String pageID = ""; // Page.getPageID(onWall);
		boolean likePage = LikePage.isLikePage(pageID, currentUser);
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
		String owner = entity.getProperty("owner").toString();
		
		boolean friends = FriendRequest.isFriends(owner, currentUser);
		if (friends || owner.equals(currentUser))
			ok = true;
		
		Post p = SharePost.getPost(entity);
		
		if (ok)
			ok = OriginalSharedPostPrivacy(ID , currentUser);
		
		if (ok)
			return p;
		
	return null;
		
	}
	
	public static boolean handlingSharedPost(String owner, String currentUser , String type)
	{
		if (type.equals("1") || type.equals("2"))
		{
			return FriendRequest.isFriends(owner, currentUser) || owner.equals(currentUser);
		}
		else if (type.equals("3"))
		{
			String pageID = ""; // Page.getPageID(onWall);
			return LikePage.isLikePage(pageID, currentUser);
			
		}
		return false;
	}

}
