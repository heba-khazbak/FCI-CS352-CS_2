package com.FCI.SWE.ModelServices;
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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Path("/")
@Produces("text/html")
public class MessageReceiver {

	public MessageReceiver ()
	{
		
	}
	
	@GET
	@Path("/readPersonalMessage")
	public String readPersonalMessage(@FormParam("ID") String ID)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		// service
		String msgID = "";
		JSONObject object = new JSONObject();
		
		// use notificationID
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("NotificationID").toString().equals(ID)) {
				// to get ID of message
				msgID = entity.getProperty("ID").toString();
				
			}
		}
		
		// table personal msg hangeb l row
		if(!msgID.equals("")) {
			gaeQuery = new Query("personalMsg");
			pq = datastore.prepare(gaeQuery);
			for (Entity entity : pq.asIterable()) {
				if (entity.getProperty("id").toString().equals(msgID)) {
					// return sender receiver content
					object.put("sender", entity.getProperty("sender"));
					object.put("receiver", entity.getProperty("receiver"));
					object.put("content", entity.getProperty("content"));
					object.put("status", entity.getProperty("sender") + " sent you a message: " + entity.getProperty("content"));
				}
			}
		}
		
		return object.toString();
	}

	@GET
	@Path("/readGroupMessage")
	public String readGroupMessage(@FormParam("ID") String ID) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		// service
		String msgID = "";
		JSONObject object = new JSONObject();
		
		// use notificationID
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("NotificationID").toString().equals(ID)) {
				// to get ID of message
				msgID = entity.getProperty("ID").toString();
				
			}
		}
		
		// table group msg hangeb l row
		if(!msgID.equals("")) {
			gaeQuery = new Query("groupMsg");
			pq = datastore.prepare(gaeQuery);
			for (Entity entity : pq.asIterable()) {
				if (entity.getProperty("id").toString().equals(msgID)) {
					// return sender receiver content
					object.put("sender", entity.getProperty("sender"));
					object.put("receiver", entity.getProperty("receiver"));
					object.put("content", entity.getProperty("content"));
					object.put("status", entity.getProperty("sender") + " sent you a message: " + entity.getProperty("content"));
				}
			}
		}
		
		return object.toString();
	}
	
}
