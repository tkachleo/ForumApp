package com.forum.managers.DBinterface;

import java.util.ArrayList;

import com.forum.helper.User;

public interface I_DBUserHandling {
	public void addNewUser(User user, boolean admin);
	public void removeUser(User user);
	public void registerUserToForum(String user, String forum);
	public String getUserStatus(String user);
	public boolean makeMgr(String user, String name);
	public boolean makeAdmin(String user);
	public boolean makeMod(String user, String forum, String sub);
	public void verifyUser(String user);
	public ArrayList<String> getForumsMembers();
	public int getForumNumber();
	public ArrayList<String> getModReport(String forum);
	public int getSubPostCount(String forum, String sub);	
	public ArrayList<String> getAllUserMsgs(String user);

}
