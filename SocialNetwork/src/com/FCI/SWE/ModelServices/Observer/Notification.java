package com.FCI.SWE.ModelServices.Observer;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Notification {
	int ID;
	String userName ;
	int type;;
	int NotificationID;
	String sender;
	
	Notification(String userName , int type , int NotificationID, String sender)
	{
		this.userName = userName;
		this.type = type;
		this.NotificationID = NotificationID;
		this.sender = sender;
	}
	
	
	
	public int getID() {
		return ID;
	}

	public String getUserName() {
		return userName;
	}

	public int getType() {
		return type;
	}

	public int getNotificationID() {
		return NotificationID;
	}

	public boolean saveNotification()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity theMessage = new Entity("Notifications", list.size() + 1);
		
		this.ID = list.size() + 1;
		theMessage.setProperty("ID", this.ID);
		theMessage.setProperty("userName", this.userName);
		theMessage.setProperty("type", this.type);
		theMessage.setProperty("NotificationID", this.NotificationID);
		theMessage.setProperty("sender", this.sender);
		datastore.put(theMessage);

		return true;
	}
	
	public static String getNotification(String ID){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("ID").toString().equals(ID) ) {
				return entity.getProperty("NotificationID").toString();
			}
		}
		return "-1";
	}
	
	/**
	 * This method gets all notifications from datastore
	 * @return list of notifications
	 */
	public static List<Entity> getNotifications(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		return pq.asList(FetchOptions.Builder.withDefaults());
	}
}
