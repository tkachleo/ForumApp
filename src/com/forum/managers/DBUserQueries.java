package com.forum.managers;

import java.util.ArrayList;

import com.forum.helper.Forum;
import com.forum.helper.Reply;
import com.forum.helper.SubForum;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

public class DBUserQueries {

	

	private DBUtils utils;
	private DBForumManager forumManager;
	private DBUserActionManager userAction;
	
	public DBUserQueries(DBUtils utils ,DBForumManager forumManager ,DBUserActionManager userAction)
	{
		this.utils=utils;
		this.forumManager=forumManager;
		this.userAction=userAction;
	}
	

	public boolean isFriendExists(String user, String Friend){
		ArrayList<String> list = userAction.getFriendList(user);
		boolean ans = list.contains(Friend);
		return ans;
	}
	
	public boolean isAdmin(String user){
		BasicDBObject obj = new BasicDBObject().append("name", user).append("admin", "true");
		DBCursor cur = utils.getCollection("users").find(obj);
		if(cur.hasNext())
			return true;
		else
			return false;
	}
	
	public boolean isMgr(String user, String forum){
		ArrayList<Forum> forums = forumManager.getAllForums();
		for(int i = 0 ; i < forums.size() ; i++){
			if(forums.get(i).getName().equals(forum)){
				ArrayList<String> mgrs = forums.get(i).getMgrs();
				for(int j = 0 ; j < mgrs.size() ; j++){
					if(mgrs.get(j).equals(user)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	public boolean isMod(String user, String forum, String sub){
		ArrayList<SubForum> subs = forumManager.getSubForums(forum);
		if(subs == null)
			return false;
		for(int i = 0 ; i < subs.size() ; i++){
			if(subs.get(i).getName().equals(sub)){
				for(int j = 0 ; j < subs.get(i).getMods().size() ; j++){
					if(subs.get(i).getMods().get(j).equals(user))
						return true;
				}
			}
		}
		return false;
	}
	
	public boolean isAuthor(String forum, String sub, String thread, String user, String text){
		ArrayList<Reply> replies =userAction.getReplies(forum, sub, thread);
		for(int i = 0 ; i < replies.size() ; i++)
			if(replies.get(i).getUser().equals(user) && replies.get(i).getText().equals(text))
				return true;
		return false;
	}

	public boolean isBanned(String user){
		BasicDBObject obj = new BasicDBObject().append("name", user).append("banned", "true");
		DBCursor cur = utils.getCollection("users").find(obj);
		if(cur.hasNext())
			return true;
		else
			return false;
	}
	
	public boolean isUserExists(String user){
		BasicDBObject obj = new BasicDBObject().append("name", user);
		DBCursor cur = utils.getCollection("users").find(obj);
		if(cur.hasNext()){
			LogManager.print("user exists");
			return true;
		}
		else{
			LogManager.print("user does not exist");
			return false;
		}
	}
	
	public boolean isVerified(String user){
		BasicDBObject query = new BasicDBObject().append("name", user);
		DBCursor cur = utils.getCollection("users").find(query);
		if(!cur.hasNext())
			return false;
		else{
			cur.next();
			BasicDBObject obj = (BasicDBObject) cur.curr();
			String verified = (String) obj.get("verified");
			if(verified.equals("true"))
				return true;
			else
				return false;
		}
	}
	
	public boolean isUserRegisteredToForum(String user, String forum){
		BasicDBObject query = new BasicDBObject().append("name", user);
		DBCursor cur = utils.getCollection("users").find(query);
		if(!cur.hasNext())
			return false;
		else{
			cur.next();
			BasicDBList list = (BasicDBList) cur.curr().get("regs");
			for(int i = 0 ; i < list.size() ; i++){
				BasicDBObject obj = (BasicDBObject) list.get(i);
				if(obj.get("forum").equals(forum))
					return true;
			}
			return false;
		}
	}
	
}
