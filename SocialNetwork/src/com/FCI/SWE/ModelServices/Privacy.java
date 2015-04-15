package com.FCI.SWE.ModelServices;

import java.util.Vector;

public interface Privacy {
	public final String PUBLIC = "public";
	public final String PRIVATE = "private";
	public final String CUSTOM = "custom";
	public Vector<Post> getPostsForTimeLine(String onWall , String currentUser);

}
