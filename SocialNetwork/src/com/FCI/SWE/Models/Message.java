package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

public abstract class Message {
	int ID;
	String sender;
	String content;
	public int type;
	List<NotificationObserver> messageObservers;
	
	Message(int id , String sender, String content)
	{
		this.ID = id;
		this.sender = sender;
		this.content = content;
		messageObservers = new ArrayList<NotificationObserver>();
	}
	
	public void attach(NotificationObserver observer){
		messageObservers.add(observer);  
	  }
	
	public void notifyAllObservers(){
	      for (NotificationObserver msgObs : messageObservers) {
	    	  msgObs.update();
	      }
	   } 
	
	public abstract boolean sendMessage();

}
