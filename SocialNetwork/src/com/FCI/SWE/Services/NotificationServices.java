package com.FCI.SWE.Services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.appengine.api.datastore.Entity;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ModelServices.*;
import com.FCI.SWE.ModelServices.Observer.FriendRequest;
import com.FCI.SWE.ModelServices.Observer.Notification;
import com.FCI.SWE.ModelServices.Observer.UserFriendObserver;

@Path("/")
@Produces("text/html")

public class NotificationServices {
	
	/**
	 * send Friend Request Rest service, this service will be called to send
	 * friend request. This function will store request in datastore
	 * @param uname friend user name
	 * @param currentUser current user in the system
	 * @return status in json format
	 */
	
	@POST
	@Path("/sendFriendRequestService")
	public String addFriendService(@FormParam("uname") String uname,
			@FormParam("currentUser") String currentUser) {
		JSONObject object = new JSONObject();
		FriendRequest request = new FriendRequest (currentUser , uname );
		new UserFriendObserver (request);
		int success = request.sendFriendRequest();
		if (success == 0) {
			object.put("Status", "Failed");

		} 
		else if(success==1){
			object.put("Status", "Exists");
		}
		else if(success==3)
		{
			object.put("Status", "yourself");
		}
		else {
			object.put("Status", "OK");
			
		}

		return object.toString();

	}
	
	/**
	 * accept Friend Request Rest service, this service will be called to accept
	 * friend request. This function will store friends in datastore
	 * @param uname friend user name
	 * @param currentUser current user in the system
	 * @return status in json format
	 */
	/**
	 */
	
//	@POST
//	@Path("/acceptFriendRequestService")
//	public String acceptFriendService(@FormParam("uname") String uname,
//			@FormParam("currentUser") String currentUser) {
//		JSONObject object = new JSONObject();
//		FriendRequest request = new FriendRequest (currentUser , uname );
//		new UserFriendObserver (request);
//		boolean sucess = request.acceptFriendRequest();
//		if (sucess == false) {
//			object.put("Status", "Failed");
//
//		} else {
//			object.put("Status", "OK");
//			
//		}
//
//		return object.toString();
//
//	}
	
	@POST
	@Path("/getAllNotificationsService")
	public String getAllNotificationsService(@FormParam("uname") String uname) {
		List<Entity> notifications = Notification.getNotifications();
		JSONArray notificationsArray = new JSONArray();
		
		for (Iterator iterator = notifications.iterator(); iterator.hasNext();) {
			Entity entity = (Entity) iterator.next();
			if(uname.equals(entity.getProperty("userName"))) {
				
				
				if (entity.getProperty("type").toString().equals("3"))
				{
					// check pending zero 
					String friendReqID = Notification.getNotification(entity.getProperty("ID").toString());
					Entity friend = FriendRequest.getFriendRequest(friendReqID);
					
					if (friend.getProperty("pending").toString().equals("0"))
						continue;
				}
				
					JSONObject notification = new JSONObject();
				notification.put("ID", entity.getProperty("ID").toString());
				notification.put("userName", entity.getProperty("userName").toString());
				notification.put("type", entity.getProperty("type").toString());
				notification.put("NotificationID", entity.getProperty("NotificationID").toString());
				notification.put("sender", entity.getProperty("sender").toString());
				notificationsArray.add(notification);
				
				
			}
		}
		
		return notificationsArray.toString();
	}
    
}
