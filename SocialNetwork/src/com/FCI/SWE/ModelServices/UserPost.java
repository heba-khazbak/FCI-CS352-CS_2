package com.FCI.SWE.ModelServices;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class UserPost extends Post {
	
	String feeling;
	boolean isfeeling;

	public UserPost(String owner, String content, String onWall, String privacy,
			String customUsers,String feeling) {
		super(owner, content, onWall, privacy ,customUsers );
		type = 1;
		this.feeling = feeling;
		
		if (feeling.equals(""))
			isfeeling = false;
		else
			isfeeling = true;
	}
	
	public UserPost(String owner, String content, String onWall, String privacy,
			String feeling) {
		super(owner, content, onWall,privacy);
		type = 1;
		this.feeling = feeling;
		
		if (feeling.equals(""))
			isfeeling = false;
		else
			isfeeling = true;
	}

	@Override
	public String savePost() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity post = new Entity("post", list.size() + 1);
		this.ID = Integer.toString(list.size() + 1);
		
		post.setProperty("ID", this.ID);
		post.setProperty("owner", this.owner);
		post.setProperty("content", this.content);
		post.setProperty("onWall", this.onWall);
		post.setProperty("type", this.type);
		post.setProperty("privacy", this.privacy);
		
		datastore.put(post);

		// check if post is custom
		if(this.privacy.equals("custom"))
			CustomPrivacy.saveCustomUsers(this.ID, this.customUsers);
		
		// check if a feeling exists
		if(isfeeling) {
			gaeQuery = new Query("feeling");
			pq = datastore.prepare(gaeQuery);
			
			Entity feeling = new Entity("feeling");
			
			feeling.setProperty("postID", this.ID);
			feeling.setProperty("state", this.feeling);
			
			datastore.put(feeling);
		}
		PostFilter.filter(this.ID, this.content);
		return this.ID;
	}
	
	public static Post getPost(Entity entity)
	{
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
			String ID = entity.getProperty("ID").toString();
			String owner = entity.getProperty("owner").toString();
			String content = entity.getProperty("content").toString();
			String onWall = entity.getProperty("onWall").toString();
			String privacy = entity.getProperty("privacy").toString();
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
			
			Post p = new UserPost(owner,content,onWall,privacy,feeling);
			p.setID(ID);
			return p;
		
	}
}
