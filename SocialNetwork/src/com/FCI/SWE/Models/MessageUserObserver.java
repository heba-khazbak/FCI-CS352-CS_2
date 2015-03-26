package com.FCI.SWE.Models;

public class MessageUserObserver implements NotificationObserver {
	Message myMsg;
	String userName;
	public MessageUserObserver (Message myMsg , String name)
	{
		this.myMsg= myMsg;
	    this.myMsg.attach(this);
	    userName = name;
	}
	
	@Override
	public void update() {
		// write notification in datastore
		
		Notification notification = new Notification(userName,myMsg.type,myMsg.ID);
		notification.saveNotification();
		
	}

}
