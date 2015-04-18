package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

import com.FCI.SWE.ModelServices.LikePage;
import com.FCI.SWE.ModelServices.LikePost;
import com.FCI.SWE.ModelServices.Page;
import com.FCI.SWE.ModelServices.Post;
import com.FCI.SWE.ModelServices.Observer.LikeObserver;


@Path("/")
@Produces("text/html")

public class PageService 
{
	
	@POST
	@Path("/CreatePage")
	public String CreatePageService
	(@FormParam("name") String name,
	 @FormParam("owner") String owner,
	 @FormParam("category") String category,
	 @FormParam("numberOfLikes") String numberOfLikes)
	{
		Page page = new Page (name, owner, category);
		page.savePage();
		JSONObject object = new JSONObject();
		
		//need to check if page name already exists
		
		
		object.put("Status","OK");
		return object.toString();
	}
	
	@POST
	@Path("/LikePage")
	public String LikePage(@FormParam("currentUser") String currentUser,@FormParam("pageID") String pageID) 
	{
		JSONObject object = new JSONObject();
		LikePost myLike = new LikePost (currentUser , pageID);
		if (LikePage.isLikePage(pageID, currentUser))
		{
			object.put("Status", "you've already liked this page");
		}
		else
		{
			//new LikeObserver (myLike , Page.getOwner(pageID));
			myLike.saveLiker();
			object.put("Status", "ok");
		}
		
		
		return object.toString();
		
	}
	

	
}
