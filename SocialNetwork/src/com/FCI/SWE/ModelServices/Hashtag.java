package com.FCI.SWE.ModelServices;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Hashtag {
	public String name;
	public String postIDs;
	public int postsCount;
	
	public Hashtag(String name, String postIDs, int postsCount){
		this.name=name;
		this.postIDs=postIDs;
		this.postsCount=postsCount;
	}
	
	public void saveHashtag(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if(entity.getProperty("name").toString().equals(this.name)){
				String IDs=entity.getProperty("postIDs").toString();
				IDs+=" "+this.postIDs;
				int count=1+Integer.parseInt(entity.getProperty("postsCount").toString());
				
				entity.setProperty("postsCount", String.valueOf(count));
				entity.setProperty("postIDs",IDs );
				datastore.put(entity);
				return;
			}
		}
		Entity entity=new Entity("Hashtag");
		entity.setProperty("name", this.name);
		entity.setProperty("postsCount", String.valueOf(this.postsCount));
		entity.setProperty("postIDs", this.postIDs);
		datastore.put(entity);
	}
	
	public static Vector<Hashtag> getAllHashtags(){
		Vector<Hashtag> hashtags = new Vector<Hashtag>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			String hashName=entity.getProperty("name").toString();
			int cnt=Integer.parseInt(entity.getProperty("postsCount").toString());
			String IDs=entity.getProperty("postIDs").toString();
			hashtags.add(new Hashtag(hashName,IDs,cnt));
		}
		return hashtags;
	}
	
	public static Vector<String> getHashtagPosts(String hashtagName,String uname){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Vector<Post> posts=Post.getAllPostsForUser(uname);
		HashMap<String,Boolean> map = new HashMap<String,Boolean>();
		
		Query gaeQuery = new Query("Hashtag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if(entity.getProperty("name").toString().equals(hashtagName)){
				String IDs=entity.getProperty("postIDs").toString();
				for(String id:IDs.split(" ")){
					map.put(id, true);
				}
				break;
			}
		}
		
		Vector<String> hashtagPosts=new Vector<String>();
		for(int i=0;i<posts.size();++i){
			if(map.containsKey(posts.get(i).ID))hashtagPosts.add(PostFilter.formatPost(posts.get(i)));
		}
		return hashtagPosts;
	}
}
