package com.forum.helper;

import java.io.Serializable;
import java.util.ArrayList;

public class Thread implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9059340763334217640L;
	private String user;
	private String caption;
	private String text;
	private ArrayList<Reply> replies;
	
	public Thread(String user, String caption, String text, ArrayList<Reply> replies){
		this.user = user;
		this.caption = caption;
		this.text = text;
		this.replies = replies;
	}
	
	public String getUser(){
		return this.user;
	}
	
	public String getCaption(){
		return this.caption;
	}
	
	public String getText(){
		return this.text;
	}
	
	public ArrayList<Reply> getReplies(){
		return this.replies;
	}
	
	public void addReply(String user, String text){
		this.replies.add(new Reply(user, text));
	}
	
	public void removeReply(String text){
		for(int i = 0 ; i < this.replies.size() ; i++){
			if(this.replies.get(i).getText().equals(text)){
				this.replies.remove(i);
			}
		}
	}
	
}
