package com.forum.client.util;

import java.util.ArrayList;

public class Sessions {

	private ArrayList<MySession> sessions;
	protected static Sessions instance;
	
	protected Sessions(){
		this.sessions = new ArrayList<MySession>();
	}

	public static Sessions getInstance(){
		if(instance == null){
			instance = new Sessions();
		}
		
		return instance;
	}
	
	public ArrayList<MySession> getSessions() {
		return this.sessions;
	}

}
