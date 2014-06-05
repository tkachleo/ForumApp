package com.forum.helper;

public class ForumEntry {
	
	private String name;
	private String desc;
	
	public ForumEntry(String name, String desc){
		this.name = name;
		this.desc = desc;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDesc(){
		return this.desc;
	}
}
