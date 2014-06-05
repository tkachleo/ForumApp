package com.forum.managers.DBinterface;

import java.util.ArrayList;

import com.forum.helper.Forum;
import com.forum.helper.ForumEntry;
import com.forum.helper.Reply;
import com.forum.helper.SubForum;
import com.forum.helper.Thread;
import com.forum.helper.User;

public interface I_DBManager {
	
	public void addNewForum(ForumEntry forum);
	
	public void changePolicy(String forum, String mgrs_num, String subs_num);
	
	public ArrayList<SubForum> getSubForums(String forumName);
	
	public boolean addNewSubForm(String forum, String sub);
	
	public void updateForum(String forumName, ArrayList<SubForum> subForums);

	public ArrayList<Forum> getAllForums();

	public void deleteForum(String name);

	public ArrayList<Reply> getReplies(String fName, String sName, String tName);

	public void addNewReply(String fName, String sName, String tName, String user, String text);

	public void deleteReply(String fName, String sName, String tName, String text);

	public void deleteSubForum(String fName, String sName);
	
	public void deleteThreadForum(String fName, String sName, String tName);

	public ArrayList<Thread> getThreads(String fName, String sName);
	
	public void addNewThread(String fName, String sName, String user, String caption, String text);

	public void verifyUser(String user);
	
	public boolean isVerified(String user);
	
	public boolean forumExists(String name);
	
	public boolean isSubforumsExists(String forum);

	public void addNewUser(User user, boolean admin);

	public void removeUser(User user);
	
	public void registerUserToForum(String user, String forum);
	
	public boolean isUserRegisteredToForum(String user, String forum);
	
	public boolean isUserExists(String user);
	
	public boolean addFriend(String user, String name);

	public void banUser(String user, String name);

	public String getUserStatus(String user);
	
	public boolean makeMgr(String user, String name);
	
	public boolean makeAdmin(String user);

	public boolean makeMod(String user, String forum, String sub);
	
	public void removeMgr(String user, String name);
	
	public void removeMod(String user, String forum, String sub);

	public boolean isAdmin(String user);

	public boolean isMgr(String user, String forum);

	public boolean isMod(String user, String forum, String sub);
	
	public boolean isAuthor(String forum, String sub, String thread, String user, String text);

	public boolean isBanned(String user);	
	
	public boolean isFriendExists(String user, String Friend);
	
	public boolean isThereSubforumExists(String forum, String sub);
	
	public boolean addComplaint(String user, String mod, String forum, String sub, String text);
	
	public ArrayList<String> getComplaints();
	
	public ArrayList<String> getForumsMembers();
	
	public int getForumNumber();
	
	public ArrayList<String> getModReport(String forum);
	
	public int getSubPostCount(String forum, String sub);
	
	public ArrayList<String> getAllUserMsgs(String user);
}
