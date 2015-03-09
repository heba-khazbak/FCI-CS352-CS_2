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
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.UserEntity;

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
public class Service {
	
	

		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}

		return object.toString();

	}
	
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
		int success = UserEntity.sendFriendRequest(uname , currentUser);
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
	public String acceptFriendService(@FormParam("uname") String uname,
			@FormParam("currentUser") String currentUser) {
		JSONObject object = new JSONObject();
		boolean sucess = UserEntity.acceptFriendRequest(uname , currentUser);
		if (sucess == false) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			
		}

		return object.toString();

	}

}