package com.forum.managers.DBinterface;

import java.util.ArrayList;

import com.forum.helper.Forum;
import com.forum.helper.ForumEntry;
import com.forum.helper.SubForum;
import com.forum.helper.Thread;

public interface I_DBForumManager {

	public boolean isSubforumsExists(String forum);
	
	public boolean isThereSubforumExists(String forum, String sub);
	public void addNewForum(ForumEntry forum);
	public void changePolicy(String forum, String mgrs_num, String subs_num);
	public void deleteForum(String name);
	public boolean forumExists(String name);
	public void removeMod(String user, String forum, String sub);
	public ArrayList<SubForum> getSubForums(String forumName);
	public boolean addNewSubForm(String forum, String sub);
	public void updateForum(String forumName, ArrayList<SubForum> subForums);
	public String getAllForumsRAW();
	public ArrayList<Forum> getAllForums();
	public ArrayList<Thread> getThreads(String fName, String sName);

}
