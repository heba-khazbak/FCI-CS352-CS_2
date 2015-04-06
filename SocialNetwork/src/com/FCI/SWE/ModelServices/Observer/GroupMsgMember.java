package com.FCI.SWE.ModelServices.Observer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class GroupMsgMember {
	String GroupName;
	String username;
	
	public GroupMsgMember (String GroupName , String username)
	{
		this.GroupName = GroupName;
		this.username = username;
	}
	
	public synchronized boolean  saveGroupMsgMember()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupMsgMembers");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity group = new Entity("groupMsgMembers", list.size() + 1);

		group.setProperty("groupName", this.GroupName);
		group.setProperty("username", this.username);
		
		datastore.put(group);

		return true;
	}
	
	public static Set<String> getAllGroupNames()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupMsgMembers");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		Set<String> groups = new HashSet <String>();
		for (Entity entity : pq.asIterable()) {
			
			String G1 = entity.getProperty("groupName").toString();
			groups.add(G1);
			
		}
		
		
		return groups;
		
	}
	
	public static Set<String> getAllMembers(String GroupName)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("groupMsgMembers");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		Set<String> members = new HashSet <String>();
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("groupName").toString().equals(GroupName))
			{
				String M1 = entity.getProperty("username").toString();
				members.add(M1);
			}
			
		}
		
		return members;
		
	}
	
}
