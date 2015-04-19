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
			String link = "<form action='viewHashtagPosts' method='POST'><input type='hidden' name='hashtagName' value='"+hashtags.get(i).name+"'><div class='buttonHolder'><input type='submit' class='submitLink' value='"+hashtags.get(i).name+"'></div></form><br><br>";
			hashtag.put("hashtagLink",link);
			array.add(hashtag);
		}
		
		return array.toString();
	}
	
	@POST
	@Path("/GetHashtagPostsService")
	public String getHashtagPostsService(@FormParam("hashtagName") String hashtagName, @FormParam("uname") String uname){
		Vector<String> posts = Hashtag.getHashtagPosts(hashtagName,uname);
		JSONArray postsArray = new JSONArray();
		
		for (int i = 0 ; i < posts.size() ; i++)
		{
			JSONObject myPost = new JSONObject();
			myPost.put("post",posts.get(i));
			postsArray.add(myPost);
		}
		
		return postsArray.toString();
		
	}
}
