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

import com.FCI.SWE.Models.User;


/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class UserController {
	
	
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}
	
	
	

	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Failed";
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home( @Context HttpServletRequest request ,@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		String serviceUrl = "http://localhost:8888/rest/LoginService";
		String urlParameters = "uname=" + uname + "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			HttpSession session = request.getSession(true);
			session.setAttribute("email", user.getEmail());
			session.setAttribute("name", user.getName());
			
			
			return Response.ok(new Viewable("/jsp/home",map)).build();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}
	
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
		String serviceUrl = "http:/localhost:8888/rest/sendFriendRequestService";
		HttpSession session = request.getSession(true);
		String urlParameters = "uname=" + uname + "&currentUser=" + session.getAttribute("name");
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		
		try {
			JSONParser parser = new JSONParser();
			parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return "Failed";
			if (object.get("Status").equals("Exists"))
				return "You're already friends!";
			if (object.get("Status").equals("yourself"))
				return "can't send friend request to yourself!";
			
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
		HttpSession session = request.getSession(true);
		String urlParameters = "uname=" + uname + "&currentUser=" + session.getAttribute("name");
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return "Failed";
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Success";

	}
	
	/**
	 *  Action function to response to logout request. This function will act as a
	 * controller part, it will free the session from the current user then redirect him to 
	 * login page
	 * 
	 * @param request session
	 * @return login page
	 */

	@GET
	@Path("/logout")
	@Produces("text/html")
	public Response logout(@Context HttpServletRequest request ) {
		try {
			HttpSession session = request.getSession(true);
			session.setAttribute("email", "");
			session.setAttribute("name", "");
			
			
			return Response.ok(new Viewable("/jsp/login")).build();	
		}catch(Exception e)
		{
			
		}
		return null;
		
		}
	
	/*
	@POST
	@Path("/doSearch")
	public Response usersList(@FormParam("uname") String uname){
		System.out.println(uname);
		String serviceUrl = "http://localhost/rest/SearchService";
		String urlParameters = "uname=" + uname;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		return null;
	}
	
	@GET
	@Path("/search")
	public Response search(){
		return Response.ok(new Viewable("/jsp/search")).build();
	}
	*/
	
}