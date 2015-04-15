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
import com.FCI.SWE.ModelServices.Observer.GroupMessage;
import com.FCI.SWE.ModelServices.Observer.GroupMsgMember;
import com.FCI.SWE.ModelServices.Observer.Message;
import com.FCI.SWE.ModelServices.Observer.MessageUserObserver;
import com.FCI.SWE.ModelServices.Observer.PersonalMessage;

@Path("/")
@Produces("text/html")
public class PostsServices {
	
	@POST
	@Path("/CreateUserPost")
	public String CreateUserPostService(@FormParam("owner") String owner,@FormParam("content") String content,
			@FormParam("onWall") String onWall,@FormParam("privacy") String privacy,
			@FormParam("feeling") String feeling , @FormParam("custom") String customUsers) {
		
		UserPost myPost = new UserPost(owner,content,onWall,privacy,feeling);
		myPost.savePost();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
		
	}
	
	@POST
	@Path("/CreateFriendPost")
	public String CreateFriendPostService(@FormParam("owner") String owner,@FormParam("content") String content,
			@FormParam("onWall") String onWall,@FormParam("privacy") String privacy,
			@FormParam("custom") String customUsers) {
		
		FriendPost myPost = new FriendPost(owner,content,onWall,privacy);
		// check  onWall is friend to owner
		boolean ok = FriendRequest.isFriends(owner, onWall);
		JSONObject object = new JSONObject();
		
		if (ok)
		{
			boolean ok2 = myPost.savePost();
			if(ok)
				object.put("Status", "OK");
			else
				object.put("Status", "Failed");
		}
		else
		{
			object.put("Status", "You are not Friends!");
		}
		
		
		return object.toString();
		
	}
	
	@POST
	@Path("/CreatePagePost")
	public String CreatePagePostService(@FormParam("owner") String owner,@FormParam("content") String content,
			@FormParam("onWall") String onWall,@FormParam("privacy") String privacy,
			@FormParam("custom") String customUsers) {
		
		PagePost myPost = new PagePost(owner,content,onWall,privacy);
		myPost.savePost();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
		
	}
	
	@POST
	@Path("/SharePost")
	public String SharePostService(@FormParam("postID") String originalPostID,@FormParam("owner") String owner,
			@FormParam("content") String content,@FormParam("onWall") String onWall,
			@FormParam("privacy") String privacy, @FormParam("custom") String customUsers) {
		
		SharePost myPost = new SharePost(owner,content,onWall,privacy , originalPostID);
		myPost.savePost();
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
		
	}
	
	

}
