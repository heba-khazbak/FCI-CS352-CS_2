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
import java.util.Vector;

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
import com.FCI.SWE.ModelServices.Observer.LikeObserver;
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
		
		UserPost myPost = new UserPost(owner,content,onWall,privacy,customUsers ,feeling);
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
		
		FriendPost myPost = new FriendPost(owner,content,onWall,privacy , customUsers);
		// check  onWall is friend to owner
		boolean ok = FriendRequest.isFriends(owner, onWall);
		JSONObject object = new JSONObject();
		
		if (ok)
		{
			myPost.savePost();
			object.put("Status", "OK");
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
		
		JSONObject object = new JSONObject();
		// check owner is the owner of the page
		// note: onWall is pageID 
		if(Page.isOwner(onWall, owner)) {
			PagePost myPost = new PagePost(owner,content,onWall,privacy,customUsers);
			myPost.savePost();
			object.put("Status", "OK");
		}
		else {
			object.put("Status", "You are not the owner of this page");
		}
		return object.toString();
		
	}
	
	@POST
	@Path("/SharePost")
	public String SharePostService(@FormParam("postID") String originalPostID,@FormParam("owner") String owner,
			@FormParam("content") String content,@FormParam("onWall") String onWall,
			@FormParam("privacy") String privacy, @FormParam("custom") String customUsers) {
		
		JSONObject object = new JSONObject();
		if (!owner.equals(onWall))
		{
			boolean ok = FriendRequest.isFriends(owner, onWall);
			if(!ok)
			{
				object.put("Status", "you are not friends");
				return object.toString();
			}
				
				
		}
		SharePost myPost = new SharePost(owner,content,onWall,privacy , originalPostID,customUsers);
		myPost.savePost();
		
		object.put("Status", "OK");
		return object.toString();
		
	}
	
	@POST
	@Path("/GetPostsForTimeLine")
	public String GetPostsForTimeLineService(@FormParam("onWall") String onWall,
			@FormParam("uname") String currentUser) {
		Vector<String> posts = Post.getPostsForTimeLine(onWall, currentUser);
		JSONArray postsArray = new JSONArray();
		
		JSONObject first = new JSONObject();
		first.put("type","0");
		if (Page.checkPageEsistance(onWall))
		{
			first.put("type","3");
			first.put("name",onWall);
		}
		else if (UserEntity.isUser(onWall))
			first.put("name",onWall);
		else
			first.put("name","There is no such TimeLine");
		
		
		postsArray.add(first);
		
		for (int i = 0 ; i < posts.size() ; i++)
		{
			JSONObject myPost = new JSONObject();
			myPost.put("post",posts.get(i));
			postsArray.add(myPost);
		}
		
		return postsArray.toString();
		
	}
	
	
	@POST
	@Path("/GetNewsFeed")
	public String GetNewsFeed(@FormParam("uname") String currentUser) {
		Vector<Post> posts = Post.getAllPostsForUser(currentUser);
		JSONArray postsArray = new JSONArray();
		
		for (int i = 0 ; i < posts.size() ; i++)
		{
			JSONObject myPost = new JSONObject();
			myPost.put("post",PostFilter.formatPost(posts.get(i)));
			postsArray.add(myPost);
		}
		
		return postsArray.toString();
		
	}
	
	@POST
	@Path("/LikePost")
	public String LikePost(@FormParam("currentUser") String currentUser,@FormParam("postID") String postID) 
	{
		JSONObject object = new JSONObject();
		LikePost myLike = new LikePost (currentUser , postID);
		if (LikePost.isLikePost(postID, currentUser))
		{
			object.put("Status", "you've already liked this post");
		}
		else
		{
			new LikeObserver (myLike , Post.getOwner(postID));
			myLike.saveLiker();
			object.put("Status", "ok");
		}
		
		
		return object.toString();
		
	}
	
	

}
