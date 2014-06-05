package com.forum.helper;

import java.io.Serializable;
import java.util.ArrayList;

public class Forum implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1881780144643525383L;
	private String name;
	private String desc;
	private ArrayList<SubForum> subs;
	private ArrayList<String> mgrs;
	private String mgr_num;
	private String sub_num;
	
	public Forum(String name, String desc, ArrayList<String> mgrs, ArrayList<SubForum> subs, String mgr_num, String sub_num){
		this.name = name;
		this.desc = desc;
		this.subs = subs;
		this.mgrs = mgrs;
		this.mgr_num = mgr_num;
		this.sub_num = sub_num;
	}
	
	public String getMaxMgr(){
		return this.mgr_num;
	}
	
	public String getMaxSubs(){
		return this.sub_num;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDesc(){
		return this.desc;
	}
	
	public ArrayList<SubForum> getSubs(){
		return this.subs;
	}
	
	public ArrayList<String> getMgrs(){
		return this.mgrs;
	}
	
	public void addSub(String sub){
		this.subs.add(new SubForum(sub, new ArrayList<String>(), new ArrayList<Thread>()));
	}
	
	public void removeSub(String sub){
		for(int i = 0 ; i < this.subs.size() ; i++){
			if(this.subs.get(i).getName().equals(sub))
				this.subs.remove(i);
		}
	}
	
	public void addMgr(String mgr){
		this.mgrs.add(mgr);
	}
	
	public void removeMgr(String mgr){
		for(int i = 0 ; i < this.mgrs.size() ; i++){
			if(this.mgrs.get(i).equals(mgr))
				this.mgrs.remove(i);
		}
	}
	
}
