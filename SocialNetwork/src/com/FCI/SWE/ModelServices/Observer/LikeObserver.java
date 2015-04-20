package com.FCI.SWE.ModelServices.Observer;

import com.FCI.SWE.ModelServices.Like;

public class LikeObserver implements NotificationObserver {
	
	Like like;
	String userName;
	public LikeObserver(Like like , String userName)
	{
		this.like = like;
		this.userName = userName;
		this.like.attach(this);
	}
	@Override
	public void update() {
		// write notification in datastore
		int ID = Integer.parseInt(like.LikedID);
		Notification notification = new Notification(userName,like.type,ID,like.Liker);
		notification.saveNotification();
		
	}

}
