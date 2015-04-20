package com.FCI.SWE.ModelServices;

import java.util.ArrayList;
import java.util.List;

import com.FCI.SWE.ModelServices.Observer.NotificationObserver;

public abstract class Like {
	
	public String Liker;
	public String LikedID; // post aw page aw comment ID .....
	public int type;
	List<NotificationObserver> likeObservers;

	public Like (String userName, String ID)
	{
		this.Liker=userName;
		this.LikedID=ID;
		likeObservers = new ArrayList<NotificationObserver>();
	}
	
	
	public void attach(NotificationObserver observer){
		likeObservers.add(observer);  
	  }
	
	public void notifyAllObservers(){
	      for (NotificationObserver likeObs : likeObservers) {
	    	  likeObs.update();
	      }
	   } 
	
	public abstract void saveLiker();
	
	
}
