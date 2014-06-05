package com.forum.helper;

import java.io.Serializable;

public class Reply implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2406910712059536075L;
	private String user;
	private String text;
	
	public Reply(String user, String text){
		this.user = user;
		this.text = text;
	}
	
	public String getUser(){
		return this.user;
	}
	
	public String getText(){
		return this.text;
	}
	
}
