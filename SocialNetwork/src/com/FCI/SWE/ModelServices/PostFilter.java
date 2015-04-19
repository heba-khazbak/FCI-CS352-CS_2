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
	
	public static String formatPost(Post post){
		String ret="";
		if(post.type==1){
			UserPost U=(UserPost)post;
			ret=post.owner+" posted<br>"+post.content;
			if(U.isfeeling)ret+="<br>feeling "+U.feeling;
		}
		else if(post.type==2){
			ret=post.owner+" posted on "+post.onWall+"'s timeline<br>"+post.content;
		}
		else if(post.type==3){
			PagePost p=(PagePost)post;
			ret=post.owner+" posted on "+post.onWall+"'s timeline<br>"+post.content;
			if(p.numberOfSeen>0)ret+="<br>Seen by "+String.valueOf(p.numberOfSeen);
		}
		else if(post.type==4){
			SharePost sh=(SharePost)post;
			ret=post.owner+" posted on "+post.onWall+"'s timeline<br>"+formatPost(Post.getPostbyID(sh.originalPostID));
		}
		ret+="<br>";
		ret+="<form action='like' method='POST' style ='display:inline;'><input type='hidden' name='postID' value='"+post.ID+"'><input type='submit' value='Like'></form><pre style ='display:inline;'>       </pre>";
		ret+="<form action='share' method='POST' style ='display:inline;'><input type='hidden' name='postID' value='"+post.ID+"'><input type='submit' value='Share'></form><br><br><br><br>";
		return ret;
	}
}
