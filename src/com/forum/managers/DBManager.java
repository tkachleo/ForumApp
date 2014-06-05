package com.forum.managers;

import java.util.ArrayList;

import com.forum.helper.Forum;
import com.forum.helper.ForumEntry;
import com.forum.helper.Reply;
import com.forum.helper.SubForum;
import com.forum.helper.Thread;
import com.forum.helper.User;
import com.mongodb.MongoException;
import com.forum.managers.DBinterface.*;

public class DBManager implements I_DBManager {

	private static DBManager instance = null;
	private DBForumManager forumManager = null;
	private DBUserActionManager userActionManager = null;
	private DBUserHandling userHandling = null;
	private DBUtils utils =null;
	private DBUserQueries UserQueries;

	protected DBManager(){
		try {
			this.utils = DBUtils.getInstance();
			this.forumManager = new DBForumManager(utils);
			this.userActionManager = new DBUserActionManager(utils ,this.forumManager);
			this.userHandling = new DBUserHandling(utils ,this.forumManager);
			this.utils.setUserHandling(this.userHandling);
			this.UserQueries = new DBUserQueries(utils, forumManager, userActionManager);
			setUserQuaries(UserQueries);
						
		} catch (MongoException e) {

			e.printStackTrace();
		}
	}

	private void setUserQuaries(DBUserQueries userQueries) {
		this.userActionManager.setUserQueries(userQueries);
		this.userHandling.setUserQueries(userQueries);
		
	}

	public static DBManager getInstance(){
		if (instance == null  ){
			instance = new DBManager();
		}
		
		return instance;
	}


	
	public void cleanDB(){
		this.utils.cleanDB();
	}

	public void addNewForum(ForumEntry forum){
		forumManager.addNewForum(forum);
	}
	
	public void changePolicy(String forum, String mgrs_num, String subs_num){
		forumManager.changePolicy(forum, mgrs_num, subs_num);
	}

	public ArrayList<SubForum> getSubForums(String forumName){
		return forumManager.getSubForums(forumName);
	}

	public boolean addNewSubForm(String forum, String sub){
		return forumManager.addNewSubForm(forum, sub);
	}

	public void updateForum(String forumName, ArrayList<SubForum> subForums){
		forumManager.updateForum(forumName, subForums);
	}


	public ArrayList<Forum> getAllForums(){
		return forumManager.getAllForums();
	}
	
	public String getAllForumsRAW(){
		return forumManager.getAllForumsRAW();
	}

	public void deleteForum(String name){
		forumManager.deleteForum(name);
	}

	public ArrayList<Reply> getReplies(String fName, String sName, String tName){
		return userActionManager.getReplies(fName, sName, tName);
	}

	public void addNewReply(String fName, String sName, String tName, String user, String text){
		userActionManager.addNewReply(fName, sName, tName, user, text);
	}

	public void deleteReply(String fName, String sName, String tName, String text){
		userActionManager.deleteReply(fName, sName, tName, text);
	}

	public void deleteSubForum(String fName, String sName){
		userActionManager.deleteSubForum(fName, sName);
	}

	public void deleteThreadForum(String fName, String sName, String tName){
		userActionManager.deleteThreadForum(fName, sName, tName);
	}

	public ArrayList<Thread> getThreads(String fName, String sName){
		return forumManager.getThreads(fName, sName);
	}

	public void addNewThread(String fName, String sName, String user, String caption, String text){
		userActionManager.addNewThread(fName, sName, user, caption, text);
	}

	public void verifyUser(String user){
		this.userHandling.verifyUser(user);
	}
	
	public boolean isVerified(String user){
		return UserQueries.isVerified(user);
	}
	
	public boolean forumExists(String name){
		return this.forumManager.forumExists(name);
	}

	public void addNewUser(User user, boolean admin){
		userHandling.addNewUser(user, admin);
	}
	
	public void removeUser(User user){
		userHandling.removeUser(user);
	}

	public void registerUserToForum(String user, String forum){
		userHandling.registerUserToForum(user, forum);
	}
	
	public boolean isUserRegisteredToForum(String user, String forum){
		return UserQueries.isUserRegisteredToForum(user, forum);
	}
	
	public boolean isUserExists(String user){
		return UserQueries.isUserExists(user);
	}

	public ArrayList<String> getFriendList(String user){
		return this.userActionManager.getFriendList(user);
	}

	public void SetFriendList(String user, ArrayList<String> list){
		this.userActionManager.SetFriendList(user, list);
	}
	
	public boolean addComplaint(String user, String mod, String forum, String sub, String text){
		return this.userActionManager.addComplaint(user, mod, forum, sub, text);
	}
	
	public ArrayList<String> getComplaints(){
		return this.userActionManager.getComplaints();
	}

	public boolean addFriend(String user, String name){
		return this.userActionManager.addFriend(user, name);
	}
	
	public boolean isFriendExists(String user, String Friend){
		return this.UserQueries.isFriendExists(user, Friend);
	}
	
	public void banUser(String user, String name){
		this.userActionManager.banUser(user, name);
	}

	public String getUserStatus(String user){
		return this.userHandling.getUserStatus(user);
	}
	
	public boolean makeMgr(String user, String name){//name=forum
		return this.userHandling.makeMgr(user, name);
	}
	
	public boolean makeAdmin(String user){
		return this.userHandling.makeAdmin(user);
	}

	public boolean makeMod(String user, String forum, String sub){
		return this.userHandling.makeMod(user, forum, sub);
	}
	
	public void removeMgr(String user, String name){
		this.userActionManager.removeMgr(user, name);
	}
	
	public void removeMod(String user, String forum, String sub){
		this.userActionManager.removeMod(user, forum, sub);
	}

	public boolean isAdmin(String user){
		return this.UserQueries.isAdmin(user);
	}

	public boolean isMgr(String user, String forum){
		return this.UserQueries.isMgr(user, forum);
	}

	public boolean isMod(String user, String forum, String sub){
		return this.UserQueries.isMod(user, forum, sub);
	}
	
	public boolean isAuthor(String forum, String sub, String thread, String user, String text){
		return this.UserQueries.isAuthor(forum, sub, thread, user, text);
	}

	public boolean isBanned(String user){
		return this.UserQueries.isBanned(user);
	}
	
	public boolean isSubforumsExists(String forum){
		return this.forumManager.isSubforumsExists(forum);
	}
	
	public boolean isThereSubforumExists(String forum, String sub){
		return this.forumManager.isThereSubforumExists(forum, sub);
	}
	
	public ArrayList<String> getForumsMembers(){
		return this.userHandling.getForumsMembers();
	}
	
	public int getForumNumber(){
		return this.userHandling.getForumNumber();
	}
	
	public ArrayList<String> getModReport(String forum){
		return this.userHandling.getModReport(forum);
	}
	
	public int getSubPostCount(String forum, String sub){
		return this.userHandling.getSubPostCount(forum, sub);
	}
	
	public ArrayList<String> getAllUserMsgs(String user){
		return this.userHandling.getAllUserMsgs(user);
	}
	
	public boolean changePassword(String user , String newPass){
		return userActionManager.changePassword(user,newPass);
	}
	
	public String getPassword(String user){
		return this.userHandling.getPassword(user);
	}

	public User getUser(String name) {
		return this.userHandling.getUser(name);
		
	}
	
	
}
