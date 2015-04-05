package com.FCI.SWE.ModelServices;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class GroupMessage extends Message {
	
	String groupName;
	 
	
	public GroupMessage(int id, String sender, String content,String groupName) {
		super(id, sender, content);
		// TODO Auto-generated constructor stub
		this.groupName = groupName;
		type = 2; // constant
	}

	

	@Override
	public boolean sendMessage() {
		// write row in datastore
		
		boolean ok = saveMessage();
		
		notifyAllObservers();
		return ok;
	}
	
	public boolean saveMessage()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupMsg");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity theMessage = new Entity("groupMsg", list.size() + 1);

		theMessage.setProperty("ID", this.ID );
		theMessage.setProperty("sender", this.sender);
		theMessage.setProperty("receiverGroupName", this.groupName);
		theMessage.setProperty("content", this.content);
		datastore.put(theMessage);

		return true;
	}
	
	public static int getNewID()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupMsg");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		return list.size() + 1;

	}
	
	public static Entity getGroupMsg(String id)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupMsg");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		
		for (Entity entity : pq.asIterable())
		{
			if (entity.getProperty("ID").toString().equals(id))
				return entity;
		}
		return null;
		
	}


}
