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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ModelServices.UserEntity;

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
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&email=" + email
					+ "&password=" + pass;
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
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";
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
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
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
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&password=" + pass;
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
				return null;
			Map<String, String> map = new HashMap<String, String>();
			UserEntity user = UserEntity.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			
			HttpSession session = request.getSession(true);
			
			session.setAttribute("email", user.getEmail());
			session.setAttribute("name", user.getName());
			
			
			return Response.ok(new Viewable("/jsp/home", map)).build();
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
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;

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
	
	@GET
	@Path("/sendPMessage")
	public Response personalMessagePage()
	{
		
		return Response.ok(new Viewable("/jsp/sendPersonalMessage")).build();
		
	}
	

	@GET
	@Path("/sendGMessage")
	public Response groupMessagePage()
	{
		
		return Response.ok(new Viewable("/jsp/sendGroupMessage")).build();
		
	}
	
	@GET
	@Path("/createGMessage")
	public Response createGroupMessagePage()
	{
		
		return Response.ok(new Viewable("/jsp/createConversation")).build();
		
	}
	
	/**
	 * This function will act as a controller part 
	 * and it will calls SendPersonalMessageService to make
	 * send a message between two users
	 * 
	 * @param receiver
	 *             name of user who will receive the message
	 * @param content
	 *            the message to be delivered
	 * @return Status string
	 */
	
	@POST
	@Path("/sendPersonalMessage")
	@Produces("text/html")
	public String send_PersonalPMessage(@Context HttpServletRequest request ,@FormParam("receiver") String receiver,
			@FormParam("content") String messageContent) {
		String serviceUrl = "http://localhost:8888/rest/SendPersonalMessageService";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);
			
			String urlParameters = "receiver=" + receiver + "&sender=" + session.getAttribute("name") +
			"&content=" + messageContent;
			
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
			if (object.get("Status").equals("NotExists"))
				return "User does not exist";
			if (object.get("Status").equals("Failed"))
				return "An error has occureed, can't send message!";
			if (object.get("Status").equals("yourself"))
				return "you cannot send a message to yourslef!";
		
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
		
		return "Message succesfully sent";

	}

	/**
	 * This function will act as a controller part 
	 * and it will calls CreateNewGroupMsgService to make
	 * a new entity of conversation among multiple users
	 * 
	 *  @param groupName
	 *            the name of the conversation to be created 
	 * @param receiver
	 *             name of users who will receive the message, in only one string
	 *             separated by a comma 
	 * @return Status string
	 */
	
	@POST
	@Path("/createGroupMessage")
	@Produces("text/html")
	public String create_GroupMessage(@Context HttpServletRequest request, @FormParam("groupName") String groupName ,
			@FormParam("receiver") String receiver) 
	{
		String serviceUrl = "http://localhost:8888/rest/CreateNewGroupMsgService";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);
			
			String []receivers = receiver.split("-");
			JSONArray membersArray = new JSONArray();
			
			
			for (String r:receivers )
			{
				JSONObject myMember = new JSONObject();
				myMember.put("Name", r);
				membersArray.add(myMember);
			}
			
			JSONObject myMember = new JSONObject();
			myMember.put("Name", session.getAttribute("name"));
			membersArray.add(myMember);
			
			
			String urlParameters = "GroupName=" + groupName + "&members=" + membersArray;
			
			
			//for(int i=0;i<receivers.length; i++)
				//urlParameters+=	("&members=" + receivers[i]);
			
				
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
			if (object.get("Status").equals("Exists"))
				return "Group Name already exists";
			if (object.get("Status").equals("Failed"))
				return "Failed to create group";
			if(object.get("Status").equals("OK"))
			{
				return "group chat '" + groupName + "' has been created successfully";
			}
			
			return (String) object.get("Status");
			
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
		
		return "ok";
		

	}

	/**
	 * This function will act as a controller part 
	 * and it will calls SendGroupMessageService to send
	 * a message to all users included in this specific converesation
	 * 
	 *  @param groupName
	 *            the name of the conversation to be created 
	 *  
	 *  @param content
	 *            the message to be delivered
	 * @return Status string
	 */
	
	@POST
	@Path("/sendGroupMessage")
	@Produces("text/html")
	public String send_GroupMessage(@Context HttpServletRequest request, @FormParam("groupName") String groupName, 
			@FormParam("content") String content ) 
	{
		String serviceUrl = "http://localhost:8888/rest/SendGroupMessageService";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);
			
			
			String urlParameters = "sender=" + session.getAttribute("name") + "&GroupName=" + groupName + "&content=" + content ;
			
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
			if (object.get("Status").equals("NotExists"))
				return "Group Name deos not exists";
			if (object.get("Status").equals("Failed"))
				return "Failed to send message";
			
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
		
		String res= "message has been sent to group chat '" + groupName + "' successfully";
		return res;

	}

	
	
}