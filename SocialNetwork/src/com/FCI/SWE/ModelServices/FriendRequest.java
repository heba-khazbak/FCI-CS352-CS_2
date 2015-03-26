package com.FCI.SWE.ModelServices;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class FriendRequest  {
	int ID;
	int type;
	String sender;
	String receiver;
	List<NotificationObserver> friendRequestObservers;
	
	public FriendRequest(String sender , String receiver )
	{
		this.sender = sender;
		this.receiver = receiver;
		friendRequestObservers = new ArrayList<NotificationObserver>();
		
	}
	
	public void attach(NotificationObserver observer){
		friendRequestObservers.add(observer);  
	  }
	
	public void notifyAllObservers(){
	      for (NotificationObserver friendObs : friendRequestObservers) {
	    	  friendObs.update();
	      }
	   } 
	
	/**
	 * This  method will save friend request in datastore and makes some checks 
	 * @param toUser friend user name
	 * @param currentUser current user
	 * @return error number or success 
	 */
	
	public int sendFriendRequest () {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		type = 3;
		String toUser = receiver;
		String currentUser = sender;
		
		if (toUser.equals(currentUser))
			return 3;
		
		boolean ok = false;
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			
			if (entity.getProperty("name").toString().equals(toUser)) {
				ok = true;
				break;
				}
		}
		
		if (!ok)
			return 0;
		
		gaeQuery = new Query("friends");
		pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			
			if ((entity.getProperty("user1").toString().equals(toUser) && entity.getProperty("user2").toString().equals(currentUser)) || (entity.getProperty("user1").toString().equals(currentUser) && entity.getProperty("user2").toString().equals(toUser))) {
				ok = false;
				break;
				}
		}
		
		if(!ok)return 1;
		
		gaeQuery = new Query("friendRequests");
		pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity friend = new Entity("friendRequests", list.size() + 1);
		this.ID = list.size() + 1;
		
		friend.setProperty("ID", ID);
		friend.setProperty("sender", currentUser);
		friend.setProperty("receiver", toUser);
		friend.setProperty("pending", 1);
		datastore.put(friend);
		
		notifyAllObservers();
		return 2;
	}
	
	/**
	 * This static method will accept friend request and add them as friends in datastore
	 * @param toUser friend user name
	 * @param currentUser current user
	 * @return success or failed
	 */
	public  boolean acceptFriendRequest () {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		type = 4;
		String toUser = receiver;
		String currentUser = sender;
		
		Query gaeQuery = new Query("friendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if(entity.getProperty("sender").equals(toUser) && entity.getProperty("receiver").equals(currentUser)){
				entity.setProperty("pending", 0);
				
				this.ID = Integer.parseInt( entity.getProperty("ID").toString()); 
				datastore.put(entity);
				
				gaeQuery = new Query("friends");
				pq = datastore.prepare(gaeQuery);
				List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

				Entity friend = new Entity("friends", list.size() + 1);

				friend.setProperty("user1", currentUser);
				friend.setProperty("user2", toUser);
				datastore.put(friend);
				
				notifyAllObservers();
				return true;
			}
		}
		return false;
	}

	

}
