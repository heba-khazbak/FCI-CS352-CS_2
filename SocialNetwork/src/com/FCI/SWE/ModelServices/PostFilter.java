package com.FCI.SWE.ModelServices;


public class PostFilter {
	
	public static void filter(String postID, String content){
		String currentHashtag="";
		boolean check=false;
		for(int i=0;i<content.length();++i){
			if(check){
				if(Character.isLetterOrDigit(content.charAt(i)) || content.charAt(i)=='_')
					currentHashtag+=content.charAt(i);
				else{
					check=false;
					if(!currentHashtag.isEmpty())
						new Hashtag(currentHashtag,postID,1).saveHashtag();
					currentHashtag="";
				}
			}
			else if(content.charAt(i)=='#')check=true;
		}
		if(!currentHashtag.isEmpty())new Hashtag(currentHashtag,postID,1).saveHashtag();
	}
}
