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
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.FCI.SWE.Models.*;

@Path("/")
@Produces("text/html")
public class MessagesServices {
	
	@POST
	@Path("/SendGroupMessageService")
	public String sendGroupMessageService(@FormParam("sender") String sender,
			@FormParam("GroupName") String GroupName , @FormParam("content") String content) {
		JSONObject object = new JSONObject();
		int ID = GroupMessage.getNewID();
		Message Msg = new GroupMessage(ID,sender,content,GroupName);
		
		// check new group or existing 
		// if new group... error
		boolean ok = false;
		Set<String> currentGroups = GroupMsgMember.getAllGroupNames();
		for (String s : currentGroups)
		{
			if (s.equals(GroupName))
			{
				ok = true;
				break;
			}
		}
		if (!ok)
		{
			object.put("Status", "NotExisits");
			return object.toString();
		}
		
		// if existing
		// get all UserObervers for this group name
		Set<String> currentmembers = GroupMsgMember.getAllMembers(GroupName);
		for (String s : currentGroups)
		{
			new MessageUserObserver (Msg , s);
		}
		
		ok = Msg.sendMessage();
		if (ok)
			object.put("Status", "OK");
		else
			object.put("Status", "Failed");
			
		return object.toString();
	}
	
	@POST
	@Path("/CreateNewGroupMsg")
	public String CreateNewGroupMsg(@FormParam("GroupName") String GroupName,
			@FormParam("members") List<String> members) {
		JSONObject object = new JSONObject();
		GroupMsgMember groupMsg;
		Set<String> currentGroups = GroupMsgMember.getAllGroupNames();
		for (String s : currentGroups)
		{
			if (s.equals(GroupName))
			{
				object.put("Status", "Exisits");
				return object.toString();
			}
		}
		
		for (String m : members)
		{
			groupMsg = new GroupMsgMember(GroupName , m);
			groupMsg.saveGroupMsgMember();
		}

		
		object.put("Status", "OK");
		return object.toString();
	}

}
