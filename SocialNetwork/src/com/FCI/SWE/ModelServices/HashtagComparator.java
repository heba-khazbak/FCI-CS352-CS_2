package com.FCI.SWE.ModelServices;

import java.util.Comparator;

public class HashtagComparator implements Comparator<Hashtag>{
	public int compare(Hashtag x,Hashtag y){
		if(x.postsCount<y.postsCount)return 1;
		return -1;
	}
}
