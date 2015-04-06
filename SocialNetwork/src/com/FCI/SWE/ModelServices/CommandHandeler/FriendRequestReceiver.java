package com.FCI.SWE.ModelServices.CommandHandeler;

import javax.ws.rs.FormParam;

import org.json.simple.JSONObject;

import com.FCI.SWE.ModelServices.Observer.FriendRequest;
import com.FCI.SWE.ModelServices.Observer.Notification;
import com.FCI.SWE.ModelServices.Observer.UserFriendObserver;
import com.google.appengine.api.datastore.Entity;

public class FriendRequestReceiver {
	String ID;
	
	public FriendRequestReceiver(String _ID){
		ID=_ID;
	}
	
	public String acceptFriendService() {
		String notificationID=Notification.getNotification(ID);
		
		Entity friendRequestEntity=FriendRequest.getFriendRequest(notificationID);
		
		String currentUser=friendRequestEntity.getProperty("receiver").toString();
		String uname=friendRequestEntity.getProperty("sender").toString();
		
		JSONObject object = new JSONObject();
		FriendRequest request = new FriendRequest (currentUser , uname );
		new UserFriendObserver (request);
		boolean sucess = request.acceptFriendRequest();
		if (sucess == false) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			
		}

		return object.toString();

	}
}
