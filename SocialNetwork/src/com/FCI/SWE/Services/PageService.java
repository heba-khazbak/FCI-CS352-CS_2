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
	

	/**
	 * create Page service, is used to allow a user to create a specific page
	 * @param owner
	 *            the current user who is attempting to create the page
	 * @param name
	 *            the new page name
	 * @param category
	 *            the new page category
	 * @return Status JSON
	 */
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
		
		boolean checkIfExists = Page.checkPageEsistance(name);
		
		if (checkIfExists)
		{
			object.put("Status","Failed");
		}	
		else
		object.put("Status","OK");
		
		return object.toString();
	}
	
	
	
	/**
	 * Like Page service, is used to allow a user to like a specific page
	 * @param currentUser
	 *            the current user who is attempting to like the page
	 * @param pageName
	 *            the chosen page to be liked
	 * @return Status JSON
	 */
	
	@POST
	@Path("/LikePage")
	public String LikePage(@FormParam("currentUser") String currentUser,@FormParam("pageName") String pageName) 
	{
		JSONObject object = new JSONObject();
		
		Page p= Page.getPage_byName(pageName);
		
		String pageID = p.getID();
		String owner = p.owner;
		
		LikePage myLike = new LikePage (currentUser , pageID);
		if (LikePage.isLikePage(pageID, currentUser))
		{
			object.put("Status", "already");
		}
		else
		{
			new LikeObserver (myLike , owner);
			myLike.saveLiker();
			object.put("Status", "ok");
		}
		
		
		return object.toString();
		
	}
	

	
}
