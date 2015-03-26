package com.FCI.SWE.Models;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class UserEntity {
	private String name;
	private String email;
	private String password;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;

	}
	
	public UserEntity(){}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}

	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static UserEntity getUser(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new UserEntity(object.get("name").toString(), object.get(
					"email").toString(), object.get("password").toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static UserEntity getUser(String name, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("name").toString());
			if (entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());
				return returnedUser;
			}
		}

		return null;
	}

	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity employee = new Entity("users", list.size() + 1);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		datastore.put(employee);

		return true;

	}
	
	/**
	 * This static method will save friend request in datastore and makes some checks 
	 * @param toUser friend user name
	 * @param currentUser current user
	 * @return error number or success 
	 */
	
	public static int sendFriendRequest (String toUser, String currentUser) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		if (toUser.equals(currentUser))
			return 3;
		
		boolean ok = false;
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			
			if (entity.getProperty("name").toString().equals(toUser)) {
				ok = true;
				break;
				}
		}
		
		if (!ok)
			return 0;
		
		gaeQuery = new Query("friends");
		pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			
			if ((entity.getProperty("user1").toString().equals(toUser) && entity.getProperty("user2").toString().equals(currentUser)) || (entity.getProperty("user1").toString().equals(currentUser) && entity.getProperty("user2").toString().equals(toUser))) {
				ok = false;
				break;
				}
		}
		
		if(!ok)return 1;
		
		gaeQuery = new Query("friendRequests");
		pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

		Entity friend = new Entity("friendRequests", list.size() + 1);

		friend.setProperty("currentUser", currentUser);
		friend.setProperty("toUser", toUser);
		friend.setProperty("pending", 1);
		datastore.put(friend);
		
		return 2;
	}
	
	/**
	 * This static method will accept friend request and add them as friends in datastore
	 * @param toUser friend user name
	 * @param currentUser current user
	 * @return success or failed
	 */
	public static boolean acceptFriendRequest (String toUser, String currentUser) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("friendRequests");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if(entity.getProperty("currentUser").equals(toUser) && entity.getProperty("toUser").equals(currentUser)){
				entity.setProperty("pending", 0);
				datastore.put(entity);
				
				gaeQuery = new Query("friends");
				pq = datastore.prepare(gaeQuery);
				List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

				Entity friend = new Entity("friends", list.size() + 1);

				friend.setProperty("user1", currentUser);
				friend.setProperty("user2", toUser);
				datastore.put(friend);
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method gets all notifications from datastore
	 * @return list of notifications
	 */
	public List<Entity> getNotifications(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		return pq.asList(FetchOptions.Builder.withDefaults());
	}
	
	
	/**
	 * This method gets all users from datastore
	 * @return list of users
	 */
	public static List<Entity> getAllUsers(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		return pq.asList(FetchOptions.Builder.withDefaults());
	}
	
}
