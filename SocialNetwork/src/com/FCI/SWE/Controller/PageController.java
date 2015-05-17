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

import com.FCI.SWE.ModelServices.Page;


/**
 * <h1>Page controller class</h1>
 * <p>
 * This class will act as a controller for page, it will get info from 
 * the services and connect it to the page Entity
 * </p>
 *
 * @author Sarah Hany
 * @version 1.0
 * @since 2014-04
 */
@Path("/")
@Produces("text/html")
public class PageController 
{
	
	
	@GET
	@Path("/createPage_Info")
	public Response createPageInfo() 
	{
		return Response.ok(new Viewable("/jsp/createPage")).build();
	}

	/**
	 * create Page method, is used to allow a user to create a specific page
	 * @param pageName
	 *            the new page name
	 * @param category
	 *            the new page category
	 * @return Status JSON
	 */
	
	@POST
	@Path("/createPage")
	@Produces("text/html")
	public String create_Page(@Context HttpServletRequest request,
			@FormParam("pageName") String name,
			@FormParam("category") String category) 
	{
		String serviceUrl = "http://localhost:8888/rest/CreatePage";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);
			
			String urlParameters = "name=" + name 
			+ "&owner="+ session.getAttribute("name") 
			+ "&category=" + category;

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
				{
					return "Page name already existed";
				}
			
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		return "Page created successfully";
	}
	
	/**
	 * Like Page method, is used to allow a user to like a specific page
	 * @param pageName
	 *            the chosen page to be liked
	 * @return Status JSON
	 */
	@POST
	@Path("/LikePage")
	public String LikePage(@Context HttpServletRequest request, 
			@FormParam("pageName") String pageName) 
	{
		String serviceUrl = "http://localhost:8888/rest/LikePage";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);
			
			String urlParameters = "currentUser=" + session.getAttribute("name")+ "&pageName=" + pageName ;
			
			
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
			
			if (object.get("Status").equals("already"))
				return "you've already liked this page";
			else if (object.get("Status").equals("ok"))
				return "You've successfully Liked ' " + pageName + " ' page";
			
			
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	/*@GET
	@Path("/getPage")
	public Response getPage() 
	{
		return Response.ok(new Viewable("/jsp/searchPage")).build();
	}
	
	@POST
	@Path("/searchPage")
	@Produces("text/html")
	public Response searchPage(@Context HttpServletRequest request,
			@FormParam("pageName") String name) 
	{
		String serviceUrl = "http://localhost:8888/rest/viewPage";
		try {
			URL url = new URL(serviceUrl);
			HttpSession session = request.getSession(true);
			
			String urlParameters = "uname=" + session.getAttribute("name") + "&name=" + name;
			
			
			
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
			if (!Page.checkPageEsistance(name))
				return null;
			
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		///3ayzaa ab3atlo page name b2a 3ashan yegebha
		return Response.ok(new Viewable("/jsp/Page")).build();
	}*/

}
