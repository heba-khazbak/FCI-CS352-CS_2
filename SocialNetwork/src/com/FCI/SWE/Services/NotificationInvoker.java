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
import com.FCI.SWE.ModelServices.CommandHandeler.AcceptFriendRequestCommand;
import com.FCI.SWE.ModelServices.CommandHandeler.CommandTypeMapper;
import com.FCI.SWE.ModelServices.CommandHandeler.ICommand;
import com.FCI.SWE.ModelServices.CommandHandeler.ReadGroupMessageCommand;
import com.FCI.SWE.ModelServices.CommandHandeler.ReadPersonalMessageCommand;

@Path("/")
@Produces("text/html")
public class NotificationInvoker {
	/**
	 * Handle Notifications Rest service, this service will be called to handle
	 * notifications according to its type using Java Reflection.
	 * 
	 * @param notification_id
	 *            ID of the notification
	 * @param notification_type
	 *            type of the notification
	 * @return status in JSON format
	 */

	@POST
	@Path("/handleNotificationService")
	public String handleNotificationService(
			@FormParam("notification_id") String notification_id,
			@FormParam("type") String notification_type) {
		String data = "";

		// create an object according to the type of the notification
		ICommand temp = null;
		try {
			CommandTypeMapper.saveCommandTypes();
			String myclassName = CommandTypeMapper
					.getTypeName(notification_type);

			myclassName = "com.FCI.SWE.ModelServices.CommandHandeler."
					+ myclassName;
			temp = (ICommand) Class.forName(myclassName).newInstance();
			/*
			 * if (notification_type.equals("1")) temp = new
			 * ReadPersonalMessageCommand(); else if
			 * (notification_type.equals("2")) temp = new
			 * ReadGroupMessageCommand(); else if
			 * (notification_type.equals("3")) temp = new
			 * AcceptFriendRequestCommand();
			 */
			data = temp.execute(notification_id); // this will execute the
													// ConcreteCommand
													// (Polymorphism)

		} catch (Exception e) {
			e.printStackTrace();
		}

		// data .. json ("status")
		return data;

	}

}
