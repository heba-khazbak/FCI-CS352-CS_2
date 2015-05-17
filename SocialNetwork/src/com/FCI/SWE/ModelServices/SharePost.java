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

	/**
	 * Constructor takes the shared post's data
	 * 
	 * @param owner
	 *            user who created the post
	 * @param content
	 *            content of the post
	 * @param onWall
	 *            user's wall
	 * @param privacy
	 *            privacy of the post(private, public, custom)
	 * @param originalPostID
	 *            the ID of the original post which is being shared
	 * @param customUsers
	 *            if the post's privacy was custom
	 */
	public SharePost(String owner, String content, String onWall,
			String privacy, String originalPostID, String customUsers) {
		super(owner, content, onWall, privacy, customUsers);
		type = 4;
		this.originalPostID = originalPostID;
	}

	/**
	 * Constructor takes the shared post's data
	 * 
	 * @param owner
	 *            user who created the post
	 * @param content
	 *            content of the post
	 * @param onWall
	 *            user's wall
	 * @param privacy
	 *            privacy of the post(private, public, custom)
	 * @param originalPostID
	 *            the ID of the original post which is being shared
	 */
	public SharePost(String owner, String content, String onWall,
			String privacy, String originalPostID) {
		super(owner, content, onWall, privacy);
		type = 4;
		this.originalPostID = originalPostID;
	}

	/**
	 * 
	 * This method will save a new shared post to the Datastore
	 */
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

		if (this.privacy.equals("custom"))
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

	/**
	 * 
	 * This method will get the shared post from the Datastore
	 * 
	 * @param entity
	 * @return Post
	 */
	public static Post getPost(Entity entity) {
		String ID = entity.getProperty("ID").toString();
		String owner = entity.getProperty("owner").toString();
		String content = entity.getProperty("content").toString();
		String onWall = entity.getProperty("onWall").toString();
		String privacy = entity.getProperty("privacy").toString();

		String originalID = "";
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("sharing");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity2 : pq.asIterable()) {
			if (entity2.getProperty("postID").toString().equals(ID)) {
				originalID = entity2.getProperty("originalID").toString();
				break;
			}
		}

		Post p = new SharePost(owner, content, onWall, privacy, originalID);
		p.setID(ID);
		return p;
	}

}
