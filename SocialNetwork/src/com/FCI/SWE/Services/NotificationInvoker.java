package com.FCI.SWE.Services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.appengine.api.datastore.Entity;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ModelServices.*;

public class NotificationInvoker {
	
	@POST
	@Path("/handleNotificationService")
	public String handleNotificationService(@FormParam("notification_id") String notification_id, String notification_type) {
		String data = "";
		
		// create an object according to the type of the notification
		ICommand temp = null;
		try {
			// send ID
			temp = (ICommand) Class.forName(notification_type).newInstance();
			data = temp.execute(notification_id); // this will execute the ConcreteCommand (Polymorphism)
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}


}
