package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PersonalMessage extends Message {
	
	String receiver;
	 
	
	public PersonalMessage(int id, String sender, String content,String receiver) {
		super(id, sender, content);
		this.receiver = receiver;
		type = 1;
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
		Query gaeQuery = new Query("personalMsg");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity theMessage = new Entity("personalMsg", list.size() + 1);

		theMessage.setProperty("ID", this.ID );
		theMessage.setProperty("sender", this.sender);
		theMessage.setProperty("receiver", this.receiver);
		theMessage.setProperty("content", this.content);
		datastore.put(theMessage);

		return true;
	}
	
	public static int getNewID()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("personalMsg");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		return list.size() + 1;

	}
}
