package com.FCI.SWE.ModelServices.Observer;

public class MessageUserObserver implements NotificationObserver {
	Message myMsg;
	String userName;
	String sender;
	public MessageUserObserver (Message myMsg , String name ,String sender)
	{
		this.myMsg= myMsg;
	    this.myMsg.attach(this);
	    userName = name;
	    this.sender = sender;
	}
	
	@Override
	public void update() {
		// write notification in datastore
		
		Notification notification = new Notification(userName,myMsg.type,myMsg.ID,sender);
		notification.saveNotification();
		
	}

}
