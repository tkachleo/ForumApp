package com.forum.client.util;

public class MySession {

	private String session = null;
	private String username = null;
	private String password = null;
	private String forum = null;
	private String sub = null;
	private String thread = null;
	private boolean logged;
	
	public MySession(String session){
		this.session = session;
		this.setLogged(false);
	}

	public String getSession() {
		return session;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getForum() {
		return forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}
}
