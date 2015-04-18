package com.FCI.SWE.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class PostController {
	
	public static Vector<String> posts;

	@GET
	@Path("/userPost")
	public Response userPostPage() {
		return Response.ok(new Viewable("/jsp/createUserPost")).build();
	}

	@GET
	@Path("/friendPost")
	public Response friendPostPage() {
		return Response.ok(new Viewable("/jsp/createFriendPost")).build();
	}

	@POST
	@Path("/createUserPost")
	@Produces("text/html")
	public String createUserPost(@Context HttpServletRequest request,
			@FormParam("privacy") String privacy,
			@FormParam("content") String postContent,
			@FormParam("feeling") String feeling,
			@FormParam("customUsers") String customUsers) {
		String serviceUrl = "http://localhost:8888/rest/CreateUserPost";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);
			
			String urlParameters = "privacy=" + privacy + "&owner="
					+ session.getAttribute("name") + "&content=" + postContent
					+ "&onWall=" + session.getAttribute("name") + "&feeling="
					+ feeling + "&custom=" + customUsers;

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

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

			// if there is any checks, we will add it here, but up till now,
			// there is nothing returned from the service called here except
			// status = OK
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "ok";
	}

	@POST
	@Path("/createFriendPost")
	@Produces("text/html")
	public String createFriendPost(@Context HttpServletRequest request,
			@FormParam("privacy") String privacy,
			@FormParam("content") String postContent,
			@FormParam("onWall") String onWall,
			@FormParam("customUsers") String customUsers) {
		String serviceUrl = "http://localhost:8888/rest/CreateFriendPost";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);

			String urlParameters = "privacy=" + privacy + "&owner="
					+ session.getAttribute("name") + "&content=" + postContent
					+ "&onWall=" + onWall + "&custom=" + customUsers;

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

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

			// if there is any checks, we will add it here, but up till now,
			// there is nothing returned from the service called here except
			// status = OK
			
			return object.get("Status").toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "ok";
	}
	
	
	@POST
	@Path("/viewTimeline")
	@Produces("text/html")
	public Response GetPostsForTimeLine(@Context HttpServletRequest request, @FormParam("onWall") String onWall)
	{
		HttpSession session = request.getSession(true);
		
		String serviceUrl = "http://localhost:8888/rest/GetPostsForTimeLine";
		try {
			URL url = new URL(serviceUrl);
			
			String urlParameters = "uname=" + session.getAttribute("name") +"&onWall="+onWall;
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
			JSONArray array = (JSONArray) parser.parse(retJson);;
			//Map<String, String> map = new HashMap<String, String>();
			posts = new Vector<String>();
			
			for (int i = 0 ; i < array.size() ;i++)
			{
				JSONObject obj = (JSONObject)array.get(i);
				String post = (String) obj.get("post");
				posts.add(post);
			}
			
			return Response.ok(new Viewable("/jsp/viewTimeline")).build();
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
		
		
		return null;
		
	}
	
	@GET
	@Path("/searchTimeline")
	public Response personalMessagePage()
	{
		
		return Response.ok(new Viewable("/jsp/searchTimeline")).build();
		
	}

}
