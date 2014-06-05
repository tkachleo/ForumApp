package com.forum.managers.DBinterface;

import java.util.ArrayList;

import com.forum.helper.Reply;

public interface I_DBUserActionManager {
	public ArrayList<Reply> getReplies(String fName, String sName, String tName);
	public void addNewReply(String fName, String sName, String tName, String user, String text);
	public void deleteReply(String fName, String sName, String tName, String text);
	public void deleteSubForum(String fName, String sName);
	public void deleteThreadForum(String fName, String sName, String tName);
	public void addNewThread(String fName, String sName, String user, String caption, String text);
	public void removeMgr(String user, String name);
	public void removeMod(String user, String forum, String sub);
	public boolean addFriend(String user, String name);
	public void banUser(String user, String name);
	public ArrayList<String> getFriendList(String user);
	public void SetFriendList(String user, ArrayList<String> list);
	public boolean addComplaint(String user, String mod, String forum, String sub, String text);
	public ArrayList<String> getComplaints();
}
