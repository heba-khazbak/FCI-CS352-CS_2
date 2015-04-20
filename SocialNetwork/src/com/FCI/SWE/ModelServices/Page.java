package com.FCI.SWE.ModelServices;

import java.util.List;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Page 
{
	
	public String ID;
	public String name;
	public String owner;
	public String category;
	public int numberOfLikes;

	
	public Page (String name, String owner )
	{
		this.name=name;
		this.owner=owner;
		this.category=null;
		numberOfLikes=1;
	}
	public Page (String name, String owner, String category )
	{
		this.name=name;
		this.owner=owner;
		this.category=category;
		numberOfLikes=1;
	}
	
	
	public Page (String name, String owner, String category, int likes )
	{
		
		this.name=name;
		this.owner=owner;
		this.category=category;
		numberOfLikes=likes;
	}
	
	public Page (String name, String owner, String category, int likes, String ID )
	{
		
		this.name=name;
		this.owner=owner;
		this.category=category;
		numberOfLikes=likes;
		this.ID=ID;
	}
	
	public void setID (String ID)
	{
		this.ID = ID;
	}
	
	public String getID ()
	{
		return this.ID;
	}
	

	public static void incrementLikes (String pageID)
	{
		Page p= getPage(pageID);
		int likes= p.numberOfLikes;
		likes++;
		p.numberOfLikes=likes;
		p.savePage();
	}

	public void savePage()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		for (Entity entity : pq.asIterable()) 
		{
			// page name already existed
			if(entity.getProperty("name").toString().equals(this.name))
			{
				return;
			}
		}
		
		Entity entity=new Entity("Page");
		this.ID = Integer.toString(list.size() + 1);
		entity.setProperty("name", this.name);
		entity.setProperty("category", this.category);
		entity.setProperty("owner", this.owner);
		entity.setProperty("numberOfLikes", String.valueOf(this.numberOfLikes));
		entity.setProperty("ID", this.ID);
		
		datastore.put(entity);
		
		//add liker
		Like lp = new LikePage(owner, ID);
		lp.saveLiker();
	}
	
	public static Page getPage (String pageID)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable())
		{
			if (entity.getProperty("name").toString().equals(pageID))
			{
				String pageName=entity.getProperty("name").toString();
				String IDs=entity.getProperty("ID").toString();
				String pageOwner= entity.getProperty("owner").toString();
				String categ = entity.getProperty("category").toString();
				int likes=Integer.parseInt(entity.getProperty("numberOfLikes").toString());
				
				Page p = new  Page(pageName, pageOwner, categ, likes, IDs);
				return p;
			}
		}
		
		return null;
	}	
		
	
	public static boolean checkPageEsistance (String pageName)
	{
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity2 : pq.asIterable())
		{
			if (entity2.getProperty("name").toString().equals(pageName))
			{
				return true;
			}
		}
		
		return false;
				
	}
	
	public static Page getPage_byName (String pageName)
	{
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable())
		{
			if (entity.getProperty("name").toString().equals(pageName))
			{
				String IDs=entity.getProperty("ID").toString();
				String pageOwner= entity.getProperty("owner").toString();
				String categ = entity.getProperty("category").toString();
				int likes=Integer.parseInt(entity.getProperty("numberOfLikes").toString());
				
				Page p = new  Page(pageName, pageOwner, categ, likes, IDs);
				return p;
			}
		}
		
		return null;
			
			
	}
	
	public static String getPageOwner (String pageID)
	{
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable())
		{
			if (entity.getProperty("ID").toString().equals(pageID))
			{
				String pageOwner= entity.getProperty("owner").toString();
				return pageOwner;
			}
		}
		
		return null;
	}



	
	public static Vector<Page> getAllPages()
	{
		Vector<Page> pages = new Vector<Page>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) 
		{
			String pageName=entity.getProperty("name").toString();
			String IDs=entity.getProperty("ID").toString();
			String pageOwner= entity.getProperty("owner").toString();
			String categ = entity.getProperty("category").toString();
			int likes=Integer.parseInt(entity.getProperty("numberOfLikes").toString());
			
			pages.add(new Page(pageName, pageOwner, categ, likes, IDs));
		}
		return pages;
	}
	
	public static boolean isOwner(String pageName, String owner) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		for (Entity entity : list) 
		{
			if(entity.getProperty("name").toString().equals(pageName))
			{
				if(entity.getProperty("owner").equals(owner)) {
					return true;
				}
			}
		}
		return false;
	}
}

