package com.FCI.SWE.ModelServices;

public abstract class Like {
	
	public String Liker;
	public String LikedID; // post aw page aw comment ID .....

	public Like (String userName, String ID)
	{
		this.Liker=userName;
		this.LikedID=ID;
	}
	
	
	public abstract void saveLiker();
	
	
}
