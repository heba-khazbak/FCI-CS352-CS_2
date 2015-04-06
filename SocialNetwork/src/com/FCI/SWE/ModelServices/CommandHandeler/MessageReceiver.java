package com.FCI.SWE.ModelServices.CommandHandeler;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import javax.ws.rs.FormParam;

import org.json.simple.JSONObject;

import com.FCI.SWE.ModelServices.Observer.FriendRequest;
import com.FCI.SWE.ModelServices.Observer.Notification;
import com.FCI.SWE.ModelServices.Observer.UserFriendObserver;
import com.google.appengine.api.datastore.Entity;


public class MessageReceiver {

	public MessageReceiver ()
	{
		
	}
	
	public String readPersonalMessage(String ID)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		// service
		String msgID = "";
		String data = "";
		JSONObject object = new JSONObject();
		
		// use notificationID
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("ID").toString().equals(ID)) {
				// to get ID of message
				msgID = entity.getProperty("NotificationID").toString();
				break;
				
			}
		}
		
		// table personal msg hangeb l row
		if(!msgID.equals("")) {
			gaeQuery = new Query("personalMsg");
			pq = datastore.prepare(gaeQuery);
			for (Entity entity : pq.asIterable()) {
				if (entity.getProperty("ID").toString().equals(msgID)) {
					// return sender receiver content
					object.put("sender", entity.getProperty("sender"));
					object.put("receiver", entity.getProperty("receiver"));
					object.put("content", entity.getProperty("content"));
					object.put("Status", entity.getProperty("sender") + " sent you a message: " + entity.getProperty("content"));
				
					//data = entity.getProperty("sender") + " sent you a message: " + entity.getProperty("content");
					break;
				}
			}
		}
		
		return object.toString();
	}

	public String readGroupMessage(String ID) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		// service
		String msgID = "";
		JSONObject object = new JSONObject();
		
		// use notificationID
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("ID").toString().equals(ID)) {
				// to get ID of message
				msgID = entity.getProperty("NotificationID").toString();
				break;
				
			}
		}
		
		// table group msg hangeb l row
		if(!msgID.equals("")) {
			gaeQuery = new Query("groupMsg");
			pq = datastore.prepare(gaeQuery);
			for (Entity entity : pq.asIterable()) {
				if (entity.getProperty("ID").toString().equals(msgID)) {
					// return sender receiver content
					object.put("sender", entity.getProperty("sender"));
					object.put("receiver", entity.getProperty("receiverGroupName"));
					object.put("content", entity.getProperty("content"));
					object.put("Status", entity.getProperty("sender") + " sent you a message: " + entity.getProperty("content"));
				}
			}
		}
		
		return object.toString();
	}
	
}
