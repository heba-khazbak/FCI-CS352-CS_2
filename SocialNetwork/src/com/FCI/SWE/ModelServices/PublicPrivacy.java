package com.FCI.SWE.ModelServices;

import java.util.List;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PublicPrivacy implements Privacy {
	
	@Override
	public Vector<Post> getPostsForTimeLine(String onWall, String currentUser) {
		Vector<Post> allPosts = new Vector<Post>();
		Vector<Post> c = getUserPostsForTimeLine(onWall,currentUser);
		allPosts.addAll(c);
		
		c = getFriendPostsForTimeLine(onWall,currentUser);
		allPosts.addAll(c);
		
		c = getPagePostsForTimeLine(onWall,currentUser);
		allPosts.addAll(c);
		
		c = getSharePostsForTimeLine(onWall,currentUser);
		allPosts.addAll(c);
		
		
		return allPosts;
	}
	
	public Vector<Post> getUserPostsForTimeLine(String onWall, String currentUser)
	{
		Vector<Post> myPosts = new Vector<Post>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("PUBLIC").toString().equals(PUBLIC)) {
				if (entity.getProperty("onWall").toString().equals("onWall") && 
					entity.getProperty("type").toString().equals("1"))
				{
					String ID = entity.getProperty("ID").toString();
					String owner = entity.getProperty("owner").toString();
					String content = entity.getProperty("content").toString();
					String feeling = "";

					Query gaeQuery2 = new Query("feeling");
					PreparedQuery pq2 = datastore.prepare(gaeQuery);
					for (Entity entity2 : pq2.asIterable())
					{
						if (entity2.getProperty("postID").toString().equals(ID))
						{
							feeling = entity2.getProperty("state").toString();
							break;
						}
					}
					
					Post p = new UserPost(owner,content,onWall,PUBLIC,feeling);
					p.setID(ID);
					myPosts.add(p);
				}
				
			}
		}
		
		return myPosts;
	}
	
	
	public Vector<Post> getFriendPostsForTimeLine(String onWall, String currentUser)
	{
		Vector<Post> myPosts = new Vector<Post>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("PUBLIC").toString().equals(PUBLIC)) {
				if (entity.getProperty("onWall").toString().equals("onWall") && 
					entity.getProperty("type").toString().equals("2"))
				{
					String ID = entity.getProperty("ID").toString();
					String owner = entity.getProperty("owner").toString();
					String content = entity.getProperty("content").toString();
					
					Post p = new FriendPost(owner,content,onWall,PUBLIC);
					p.setID(ID);
					myPosts.add(p);
				}
				
			}
		}
		
		return myPosts;
	}
	
	public Vector<Post> getPagePostsForTimeLine(String onWall, String currentUser)
	{
		Vector<Post> myPosts = new Vector<Post>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("PUBLIC").toString().equals(PUBLIC)) {
				if (entity.getProperty("onWall").toString().equals("onWall") && 
					entity.getProperty("type").toString().equals("3"))
				{
					String ID = entity.getProperty("ID").toString();
					String owner = entity.getProperty("owner").toString();
					String content = entity.getProperty("content").toString();
					
					Post p = new PagePost(owner,content,onWall,PUBLIC);
					p.setID(ID);
					
					if (entity.getProperty("owner").toString().equals("currentUser"))
						((PagePost) p).calculateNumberofSeen();
						
					myPosts.add(p);
				}
				
			}
		}
		
		return myPosts;
	}
	
	public Vector<Post> getSharePostsForTimeLine(String onWall, String currentUser)
	{
		Vector<Post> myPosts = new Vector<Post>();
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("PUBLIC").toString().equals(PUBLIC)) {
				if (entity.getProperty("onWall").toString().equals("onWall") && 
					entity.getProperty("type").toString().equals("4"))
				{
					String ID = entity.getProperty("ID").toString();
					String owner = entity.getProperty("owner").toString();
					String content = entity.getProperty("content").toString();
					
					Post p = new PagePost(owner,content,onWall,PUBLIC);
					p.setID(ID);
					boolean ok = false;
					String originalID = "";
					String originalPUBLIC = "";
					
					Query gaeQuery2 = new Query("sharing");
					PreparedQuery pq2 = datastore.prepare(gaeQuery);
					for (Entity entity2 : pq2.asIterable())
					{
						if (entity2.getProperty("postID").toString().equals(ID))
						{
							originalID = entity2.getProperty("originalID").toString(); 
							break;
						}
					}
					
					// search for original post to get it's PUBLIC
					gaeQuery2 = new Query("post");
					 pq2 = datastore.prepare(gaeQuery);
					for (Entity entity2 : pq2.asIterable())
					{
						if (entity2.getProperty("postID").toString().equals(originalID))
						{
							originalPUBLIC = entity2.getProperty("PUBLIC").toString(); 
							break;
						}
					}
					
					// can this user sees this post ?
					if (originalPUBLIC.equals(PUBLIC))
					{
						ok = true;
					}
					else if (originalPUBLIC.equals(PRIVATE))
					{
						//ok = PrivatePrivacy.canSeePost(currentUser , originalID);
					}
					else if (originalPUBLIC.equals(CUSTOM))
					{
						//ok = CustomPrivacy.canSeePost(currentUser , originalID);
					}
					
					if (ok)
						myPosts.add(p);
				}
				
			}
		}
		
		return myPosts;
	}

}
