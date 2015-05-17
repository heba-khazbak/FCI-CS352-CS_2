package com.FCI.SWE.ModelServices;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PagePost extends Post {
	int numberOfSeen;

	/**
	 * Constructor takes the page post's data
	 * 
	 * @param owner
	 *            user who created the post
	 * @param content
	 *            content of the post
	 * @param onWall
	 *            page's wall
	 * @param privacy
	 *            privacy of the post(private, public, custom)
	 * @param customUsers
	 *            if the post's privacy was custom
	 */
	public PagePost(String owner, String content, String onWall,
			String privacy, String customUsers) {
		super(owner, content, onWall, privacy, customUsers);
		type = 3;
		numberOfSeen = -1;
	}

	/**
	 * Constructor takes the page post's data
	 * 
	 * @param owner
	 *            user who created the post
	 * @param content
	 *            content of the post
	 * @param onWall
	 *            page's wall
	 * @param privacy
	 *            privacy of the post(private, public, custom)
	 */
	public PagePost(String owner, String content, String onWall, String privacy) {
		super(owner, content, onWall, privacy);
		type = 3;
		numberOfSeen = -1;
	}

	/**
	 * 
	 * This method will save a new page post to the Datastore
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

		PostFilter.filter(this.ID, this.content);
		return this.ID;
	}

	/**
	 * 
	 * This method will calculate number of seen users for the post
	 */
	public void calculateNumberofSeen() {
		numberOfSeen = SeenPagePost.getNumberOfSeen(ID);
	}

	public boolean addSeenUser() {
		// check not seen
		// then add his in table
		return false;
	}

	/**
	 * 
	 * This method will get the page post from the Datastore
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

		Post p = new PagePost(owner, content, onWall, privacy);
		p.setID(ID);

		return p;
	}

}
