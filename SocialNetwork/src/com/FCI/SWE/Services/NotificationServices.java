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
	
	@POST
	@Path("/acceptFriendRequestService")
	public String accpetFriendService(@FormParam("uname") String uname,
			@FormParam("currentUser") String currentUser) {
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
	
	@POST
	@Path("/getAllNotificationsService")
	public String getAllNotificationsService(@FormParam("uname") String uname) {
		JSONObject object = new JSONObject();
		List<Entity> notifications = Notification.getNotifications();
		JSONArray notificationsArray = new JSONArray();
		
		for (Iterator iterator = notifications.iterator(); iterator.hasNext();) {
			Entity entity = (Entity) iterator.next();
			if(uname.equals(entity.getProperty("userName"))) {
				JSONObject notification = new JSONObject();
				notification.put("ID", entity.getProperty("ID"));
				notification.put("userName", entity.getProperty("userName"));
				notification.put("type", entity.getProperty("type"));
				notification.put("NotificationID", entity.getProperty("NotificationID"));
				notificationsArray.add(notification);
			}
		}
		object.put("Notifications", notificationsArray);
		
		return object.toString();
	}
    

	@POST
	@Path("/handleNotificationService")
	public String handleNotificationService(@FormParam("notification_id") String notification_id) {
		JSONObject object = new JSONObject();
		
		// TODO: retrieve the notification from the datastore
		
		// create an object according to the type of the notification
		String notification_type = ""; // TODO: set the notification type with the type form the datastore
		ICommand temp = null;
		try {
			// send ID
//			temp = (ICommand) Class.forName(notification_type).newInstance();
//			temp.execute(); // this will execute the ConcreteCommand (Polymorphism)
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// TODO: return something in the json object
		
		return object.toString();
	}

}
