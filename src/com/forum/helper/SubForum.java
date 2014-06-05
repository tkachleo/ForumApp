package com.forum.helper;

import java.io.Serializable;
import java.util.ArrayList;

public class SubForum implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3193329333709439327L;
	private String name;
	private ArrayList<Thread> threads;
	private ArrayList<String> mods;
	
	public SubForum(String name, ArrayList<String> mods, ArrayList<Thread> threads){
		this.name = name;
		this.threads = threads;
		this.mods = mods;
	}
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Thread> getThreads(){
		return this.threads;
	}
	
	public ArrayList<String> getMods(){
		return this.mods;
	}
	
	public void addThread(String user, String caption, String text){
		this.threads.add(new Thread(user, caption, text, new ArrayList<Reply>()));
	}
	
	public void removeThread(String caption){
		for(int i = 0 ; i < this.threads.size() ; i++){
			if(this.threads.get(i).getCaption().equals(caption))
				this.threads.remove(i);
		}
	}
	
	public void addMod(String mod){
		this.mods.add(mod);
	}
	
	public void removeMod(String mod){
		for(int i = 0 ; i < this.mods.size() ; i++){
			if(this.mods.get(i).equals(mod))
				this.mods.remove(i);
		}
	}
}
