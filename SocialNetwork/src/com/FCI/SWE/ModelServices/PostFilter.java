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
		String formattedPost="";
		if(post.type==1){
			UserPost userPost=(UserPost)post;
			formattedPost=post.owner+" posted<br>"+post.content;
			if(userPost.isfeeling)formattedPost+="<br>feeling "+userPost.feeling;
		}
		else if(post.type==2){
			formattedPost=post.owner+" posted on "+post.onWall+"'s timeline<br>"+post.content;
		}
		else if(post.type==3){
			PagePost pagePost=(PagePost)post;
			formattedPost=post.owner+" posted on "+post.onWall+"'s timeline<br>"+post.content;
			if(pagePost.numberOfSeen>0)formattedPost+="<br>Seen by "+String.valueOf(pagePost.numberOfSeen);
		}
		else if(post.type==4){
			SharePost sharedPost=(SharePost)post;
			String postedOn=post.onWall;
			if(postedOn.equals(post.owner))postedOn="<br>";
			else postedOn=" on "+postedOn+"'s timeline<br>";
			formattedPost="<b>"+post.owner+" posted"+postedOn+post.content+"</b><br>";
			
			return formattedPost+formatPost(Post.getPostbyID(sharedPost.originalPostID));
		}
		formattedPost+="<br>";
		formattedPost+="<form action='LikePost' method='POST' style ='display:inline;'><input type='hidden' name='postID' value='"+post.ID+"'><input type='submit' value='Like'></form><pre style ='display:inline;'>       </pre>";
		formattedPost+="<form action='sharePost' method='POST' style ='display:inline;'><input type='hidden' name='postID' value='"+post.ID+"'><input type='submit' value='Share'></form><br><br><br><br>";
		
		int likes = LikePost.getNumberOfLikes(post.ID);
		if(likes > 0)formattedPost+="<br>Liked by "+String.valueOf(likes);
		return formattedPost;
	}
}
