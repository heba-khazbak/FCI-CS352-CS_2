package com.FCI.SWE.ModelServices;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class FriendPost extends Post {

	public FriendPost(String owner, String content, String onWall,
			String privacy ,String customUsers) {
		super(owner, content, onWall, privacy , customUsers);
		type = 2;
	}
	
	public FriendPost(String owner, String content, String onWall,
			String privacy) {
		super(owner, content, onWall, privacy);
		type = 2;
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
		
		PostFilter.filter(this.ID, this.content);
		return this.ID;
	}
	
	public static Post getPost(Entity entity)
	{
		String ID = entity.getProperty("ID").toString();
		String owner = entity.getProperty("owner").toString();
		String content = entity.getProperty("content").toString();
		String onWall = entity.getProperty("onWall").toString();
		String privacy = entity.getProperty("privacy").toString();
		
		Post p = new FriendPost(owner,content,onWall,privacy);
		p.setID(ID);
		return p;
	}

}