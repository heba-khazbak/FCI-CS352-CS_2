package com.FCI.SWE.ModelServices;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class SharePost extends Post {
	
	String originalPostID;
	public SharePost(String owner, String content, String onWall, String privacy,String originalPostID
			,String customUsers) {
		super(owner, content, onWall, privacy,customUsers);
		this.originalPostID = originalPostID;
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
		
		if(this.privacy.equals("custom"))
			CustomPrivacy.saveCustomUsers(this.ID, this.customUsers);
		
		// add original ID
		gaeQuery = new Query("sharing");
		pq = datastore.prepare(gaeQuery);
		
		Entity sharing = new Entity("sharing");
		
		sharing.setProperty("postID", this.ID);
		sharing.setProperty("originalID", this.originalPostID);
		
		datastore.put(sharing);
		
		return this.ID;
	}
	
	public static Post getPost(Entity entity)
	{
		String ID = entity.getProperty("ID").toString();
		String owner = entity.getProperty("owner").toString();
		String content = entity.getProperty("content").toString();
		String onWall = entity.getProperty("onWall").toString();
		String privacy = entity.getProperty("privacy").toString();
		
		Post p = new PagePost(owner,content,onWall,privacy);
		p.setID(ID);
		return p;
	}

}
