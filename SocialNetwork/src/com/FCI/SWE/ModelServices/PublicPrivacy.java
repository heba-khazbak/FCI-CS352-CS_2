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
		
		return UserPost.getPost(entity);
	}
	
	@Override
	public Post canSeeFriendPost(Entity entity, String onWall,
			String currentUser) {
		
		return FriendPost.getPost(entity);
			
	}

	@Override
	public Post canSeePagePost(Entity entity, String onWall, String currentUser) {
		
		Post p = PagePost.getPost(entity);
		if (entity.getProperty("owner").toString().equals(currentUser))
			((PagePost) p).calculateNumberofSeen();		
		
		return p;
			
	}

	@Override
	public Post canSeeSharedPost(Entity entity, String onWall,
			String currentUser) {
		
			String ID = entity.getProperty("ID").toString();
			Post p = SharePost.getPost(entity);
			
			boolean ok = OriginalSharedPostPrivacy(ID , currentUser);
			
			if (ok)
				return p;
			
		return null;
	}
	
	
	
	

}
