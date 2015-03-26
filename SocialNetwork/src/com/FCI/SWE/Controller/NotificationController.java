package com.FCI.SWE.Controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ModelServices.UserEntity;

@Path("/")
@Produces("text/html")
public class NotificationController {
	

	/**
	 * Action function to render send friend request page, this function will be executed
	 * using url like this /rest/sendFriend
	 * 
	 * @return send friend request page
	 */
	
	@GET
	@Path("/sendFriend")
	public Response addFriendPage()
	{
		return Response.ok(new Viewable("/jsp/sendfriend")).build();
		
	}
	
	/**
	 * Action function to render notifications page which contains friend requests, this function will be executed
	 * using url like this /rest/notifications
	 * 
	 * @return notifications page
	 */
	
	@GET
	@Path("/notifications")
	public Response notificationsPage()
	{
		
		return Response.ok(new Viewable("/jsp/notifications")).build();
		
	}
	
	/**
	 * Action function to response to send Friend Request. This function will act as a
	 * controller part, it will calls send Friend Request service to send friend request and check
	 * that they are not already friends and the user exists in datastore
	 * 
	 * @param request the session 
	 * @param uname user name of the friend that the request will be sent to
	 * @return status string
	 */
	@POST
	@Path("/sendFriendRequest")
	@Produces("text/html")
	public String addFriend(@Context HttpServletRequest request ,@FormParam("uname") String uname) {
		String serviceUrl = "http://localhost:8888/rest/sendFriendRequestService";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);
			
			String urlParameters = "uname=" + uname + "&currentUser=" + session.getAttribute("name");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return "Failed";
			if (object.get("Status").equals("Exists"))
				return "You're already friends!";
			if (object.get("Status").equals("yourself"))
				return "can't send friend request to yourself!";
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Friend request succesfully sent";

	}
	
	/**
	 * Action function to response to accept Friend Request. This function will act as a
	 * controller part, it will calls accept Friend Request service to accept friend request and add
	 * them in datastore
	 * 
	 * @param request the session 
	 * @param uname user name of the friend 
	 * @return status string
	 */
	
	@POST
	@Path("/acceptFriendRequest")
	@Produces("text/html")
	public String acceptFriend(@Context HttpServletRequest request ,@FormParam("dropNotifications") String uname) {
		String serviceUrl = "http://localhost:8888/rest/acceptFriendRequestService";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);
			
			
			String urlParameters = "uname=" + uname + "&currentUser=" + session.getAttribute("name");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000);  //60 Seconds
			connection.setReadTimeout(60000);  //60 Seconds
			
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return "Failed";
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Success";

	}

}
