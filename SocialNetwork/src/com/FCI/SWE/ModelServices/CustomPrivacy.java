package com.FCI.SWE.ModelServices;

import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


public class CustomPrivacy implements Privacy {
	
	@Override
	public Vector<Post> getPostsForTimeLine(String onWall, String currentUser) {
		// TODO Auto-generated method stub
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
}
