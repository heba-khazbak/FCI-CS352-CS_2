package com.FCI.SWE.Models;

import java.util.List;

public class UserFriendObserver implements NotificationObserver {
	FriendRequest request;
	
	
	public UserFriendObserver (FriendRequest request)
	{
		this.request= request;
	    this.request.attach(this);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		Notification notification = new Notification(request.receiver,request.type,request.ID);
		notification.saveNotification();
		
	}

}
