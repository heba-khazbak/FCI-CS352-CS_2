package com.FCI.SWE.ModelServices.CommandHandeler;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class CommandTypeMapper {

	
	public static void saveCommandTypes()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("CommandTypeMapper");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		if (list.size()==0)
		{
			Entity row = new Entity("CommandTypeMapper", list.size() +1);
			row.setProperty("type", "1");
			row.setProperty("className", "ReadPersonalMessageCommand");
			datastore.put(row);
			
			row = new Entity("CommandTypeMapper", list.size() +2);
			row.setProperty("type", "2");
			row.setProperty("className", "ReadGroupMessageCommand");
			datastore.put(row);
			
			row = new Entity("CommandTypeMapper", list.size() +3);
			row.setProperty("type", "3");
			row.setProperty("className", "AcceptFriendRequestCommand");
			datastore.put(row);
			
		}
		
		
	}
	
	public static String getTypeName (String type)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("CommandTypeMapper");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("type").toString().equals(type) ) {
				return entity.getProperty("className").toString();
			}
		}
		
		return null;
	}

}
