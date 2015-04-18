package com.FCI.SWE.Services;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.FCI.SWE.ModelServices.Hashtag;
import com.FCI.SWE.ModelServices.HashtagComparator;
import com.FCI.SWE.ModelServices.Post;

@Path("/")
@Produces("text/html")
public class HashtagServices {
	
	@POST
	@Path("/GetTrendsService")
	public String getTrendsService(){
		Vector<Hashtag> hashtags = Hashtag.getAllHashtags();
		JSONArray array = new JSONArray();
		
		Comparator<Hashtag> comp = new HashtagComparator();
		
		Collections.sort(hashtags, comp);
		
		int sz=hashtags.size();
		for (int i = 0 ; i < Math.min(10, sz) ; ++i)
		{
			JSONObject hashtag = new JSONObject();
			hashtag.put("name",hashtags.get(i).name);
			hashtag.put("postIDs", hashtags.get(i).postIDs);
			hashtag.put("postsCount",String.valueOf(hashtags.get(i).postsCount));
			array.add(hashtag);
		}
		
		return array.toString();
	}
	
	@POST
	@Path("/GetHashtagPostsService")
	public String getHashtagPostsService(@FormParam("hashtagName") String hashtagName){
		Vector<Post> posts = Hashtag.getHashtagPosts(hashtagName);
		JSONArray postsArray = new JSONArray();
		
		for (int i = 0 ; i < posts.size() ; i++)
		{
			JSONObject myPost = new JSONObject();
			myPost.put("ID",posts.get(i).ID);
			myPost.put("owner", posts.get(i).owner);
			myPost.put("content",posts.get(i).content );
			myPost.put("onWall",posts.get(i).onWall );
			myPost.put("type",posts.get(i).type );
			myPost.put("privacy",posts.get(i).privacy );
			postsArray.add(myPost);
		}
		
		return postsArray.toString();
		
	}
}
