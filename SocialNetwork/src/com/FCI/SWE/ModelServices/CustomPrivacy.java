package com.FCI.SWE.ModelServices;

import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ModelServices.Observer.FriendRequest;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


public class CustomPrivacy extends Privacy {
	
	@Override
	public Post canSeeUserPost(Entity entity, String onWall,
			String currentUser) {
		// should be in custom table for this postID 
		// regardless post type
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		String ID = entity.getProperty("ID").toString();
		String owner = entity.getProperty("owner").toString();
		String content = entity.getProperty("content").toString();
		String feeling = "";
		
		boolean inCustom = isInCustom(ID, currentUser);
		if (inCustom)
		{
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
			
			Post p = new UserPost(owner,content,onWall,CUSTOM,feeling);
			p.setID(ID);
			return p;
		}
		return null;
	}

	@Override
	public Post canSeeFriendPost(Entity entity, String onWall,
			String currentUser) {
		String ID = entity.getProperty("ID").toString();
	
		boolean inCustom = isInCustom(ID, currentUser);
		if (inCustom)
		{
			String owner = entity.getProperty("owner").toString();
			String content = entity.getProperty("content").toString();
			
			Post p = new FriendPost(owner,content,onWall,CUSTOM);
			p.setID(ID);
			return p;
		}
		return null;
	}

	@Override
	public Post canSeePagePost(Entity entity, String onWall, String currentUser) {
		String ID = entity.getProperty("ID").toString();
		
		boolean inCustom = isInCustom(ID, currentUser);
		if (inCustom)
		{
			String owner = entity.getProperty("owner").toString();
			String content = entity.getProperty("content").toString();
			
			Post p = new PagePost(owner,content,onWall,CUSTOM);
			p.setID(ID);
			
			if (entity.getProperty("owner").toString().equals("currentUser"))
				((PagePost) p).calculateNumberofSeen();
			return p;
		}
		return null;
	}

	@Override
	public Post canSeeSharedPost(Entity entity, String onWall,
			String currentUser) {
		String ID = entity.getProperty("ID").toString();
		String owner = entity.getProperty("owner").toString();
		String content = entity.getProperty("content").toString();
		
		Post p = new PagePost(owner,content,onWall,CUSTOM);
		p.setID(ID);
		
		
		boolean ok = isInCustom (ID ,currentUser);
		if (ok)
			ok = OriginalSharedPostPrivacy(ID , currentUser);
		
		if (ok)
			return p;
		
		return null;
	}
	
	
	public static boolean saveCustomUsers(String postID , String jsonUsers)
	{
		Vector<String> usersVector = ParseUsersToVector(jsonUsers);
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("customPrivacy");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		
		int i = 1;
		for (String s : usersVector)
		{
			Entity custom = new Entity("customPrivacy", list.size() + i);
			custom.setProperty("postID", postID);
			custom.setProperty("userName", s);
			datastore.put(custom);
			++i;
		}
		return true;
		
	}

	public static Vector<String> ParseUsersToVector (String jsonUsers)
	{
		Vector<String> usersVector = new Vector<String>();
		JSONParser parser = new JSONParser();
		JSONArray usersArray = null;
		try {
			usersArray =(JSONArray) parser.parse(jsonUsers);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0 ; i < usersArray.size();i++)
		{
			JSONObject obj = (JSONObject)usersArray.get(i);
			String s = (String) obj.get("Name");
			usersVector.add(s);
		}
		return usersVector;
	}
	
	public static boolean isInCustom(String postID , String userName)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query gaeQuery = new Query("customPrivacy");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if(entity.getProperty("postID").toString().equals(postID) &&
					entity.getProperty("userName").toString().equals(userName))
			{
				return true;
			}
		}
		
		return false;
		
	}


	
}
