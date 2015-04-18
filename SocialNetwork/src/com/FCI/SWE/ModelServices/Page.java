package com.FCI.SWE.ModelServices;

import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
		numberOfLikes=0;
	}
	public Page (String name, String owner, Sting category )
	{
		this.name=name;
		this.owner=owner;
		this.category=category;
		numberOfLikes=0;
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

	public void incrementLikes ()
	{
		numberOfLikes++;
	}

	public void savePage()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			// page name already existed
			if(entity.getProperty("name").toString().equals(this.name))
			{
				return;
			}
		}
		
		Entity entity=new Entity("Page");
		entity.setProperty("name", this.name);
		entity.setProperty("category", this.category);
		entity.setProperty("owner", this.owner);
		entity.setProperty("numberOfLikes", String.valueOf(this.numberOfLikes));
		entity.setProperty("ID", this.ID);
		
		datastore.put(entity);
	}
	
	public static Page getPage (Entity entity)
	{
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		String pageName=entity.getProperty("name").toString();
		String IDs=entity.getProperty("ID").toString();
		String pageOwner= entity.getProperty("owner").toString();
		String categ = entity.getProperty("category").toString();
		int likes=Integer.parseInt(entity.getProperty("numberOfLikes").toString());
		
		
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity2 : pq.asIterable())
		{
			if (entity2.getProperty("ID").toString().equals(ID))
			{
				Page p = new new Page(pageName, pageOwner, categ, likes, IDs);
				p.setID(ID);
				return p;
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
	
	
}

