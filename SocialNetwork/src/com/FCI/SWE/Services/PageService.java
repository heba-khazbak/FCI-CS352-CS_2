package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;
import com.FCI.SWE.ModelServices.Page;


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
		Page page = new Page (name, owner, category, numberOfLikes);
		page.savPage();
		JSONObject object = new JSONObject();
		
		//need to check if page name already exists
		
		
		object.put("Status", "OK");
		return object.toString();
	}
	

	
}
