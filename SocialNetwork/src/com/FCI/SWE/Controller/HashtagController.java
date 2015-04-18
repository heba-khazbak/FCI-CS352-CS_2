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

import com.FCI.SWE.Controller.NotificationController.Type;
import com.FCI.SWE.ModelServices.Post;

@Path("/")
@Produces("text/html")
public class HashtagController {
	
	public class Type{
		public String owner;
		public String content;
		public String onWall;
		public String privacy;
		public Type(String owner, String content, String onWall,String privacy)
		{
			this.owner = owner;
			this.content = content;
			this.onWall = onWall;
			this.privacy = privacy;
		}
	}
	
	
	public static Vector<String> hashtags;
	
	public static Vector<Type> posts;
	
	@GET
	@Path("/showTrends")
	public Response showTrends(@Context HttpServletRequest request)
	{
		HttpSession session = request.getSession(true);
		
		String serviceUrl = "http://localhost:8888/rest/GetTrendsService";
		try {
			URL url = new URL(serviceUrl);
			
			
			String urlParameters = "uname=" + session.getAttribute("name") ;
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
			hashtags = new Vector<String>();
			
			for (int i = 0 ; i < array.size() ;i++)
			{
				JSONObject obj = (JSONObject)array.get(i);
				String name = (String) obj.get("name");
				hashtags.add(name);
			}
			
			return Response.ok(new Viewable("/jsp/showTrends")).build();
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
	
	@POST
	@Path("/viewHashtagPosts")
	@Produces("text/html")
	public Response viewHashtagPosts(@Context HttpServletRequest request, @FormParam("hashtagName") String hashtagName)
	{
		HttpSession session = request.getSession(true);
		
		String serviceUrl = "http://localhost:8888/rest/GetHashtagPostsService";
		try {
			URL url = new URL(serviceUrl);
			
			
			String urlParameters = "uname=" + session.getAttribute("name")+"&hashtagName="+hashtagName;
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
			posts = new Vector<Type>();
			
			for (int i = 0 ; i < array.size() ;i++)
			{
				JSONObject obj = (JSONObject)array.get(i);
				String owner = (String) obj.get("owner");
				String content = (String) obj.get("content");
				String onWall = (String) obj.get("onWall");
				String privacy = (String) obj.get("privacy");
				posts.add(new Type(owner,content,onWall,privacy));
			}
			
			return Response.ok(new Viewable("/jsp/viewHashtagPosts")).build();
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
	@Path("/viewHashtag")
	public Response personalMessagePage()
	{
		
		return Response.ok(new Viewable("/jsp/viewHashtag")).build();
		
	}
}
