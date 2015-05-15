package com.FCI.SWE.ModelServices.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Message {
	int ID;
	String sender;
	String content;
	String receiver;
	public int type;
	List<NotificationObserver> messageObservers;
	
	Message(int id , String sender, String content ,String receiver)
	{
		this.ID = id;
		this.sender = sender;
		this.content = content;
		this.receiver = receiver;
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
	
	public  boolean sendMessage()
	{
		// write row in datastore
		
		boolean ok = saveMessage();
		notifyAllObservers();
		return ok;
	}
	
	public abstract boolean saveMessage();

}
