package com.forum.managers;

import java.util.ArrayList;

import com.forum.helper.Forum;
import com.forum.helper.Reply;
import com.forum.helper.SubForum;
import com.forum.helper.Thread;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.forum.managers.DBinterface.*;

public class DBUserActionManager implements I_DBUserActionManager  {


	private DBUtils utils;
	private DBForumManager forumManager;
	private DBUserQueries UserQueries;

	public void setUserQueries(DBUserQueries userQueries) {
		UserQueries = userQueries;
	}



	public DBUserActionManager(DBUtils utils, DBForumManager forumManager){
		this.utils=utils;
		this.forumManager=forumManager;
	}



	public ArrayList<Reply> getReplies(String fName, String sName, String tName){
		ArrayList<Thread> threads = forumManager.getThreads(fName, sName);
		if(threads != null){
			for(int i = 0 ; i < threads.size() ; i++){
				if(threads.get(i).getCaption().equals(tName)){
					return threads.get(i).getReplies();
				}
			}
		}
		return null;
	}

	public void addNewReply(String fName, String sName, String tName, String user, String text){
		ArrayList<SubForum> subs = forumManager.getSubForums(fName);
		for(int i = 0 ; i < subs.size() ; i++){
			if(subs.get(i).getName().equals(sName)){
				ArrayList<Thread> threads = subs.get(i).getThreads();
				for(int j = 0 ; j < threads.size() ; j++){
					if(threads.get(j).getCaption().equals(tName)){
						threads.get(j).addReply(user, text);
						forumManager.updateForum(fName, subs);
						break;
					}
				}
			}
		}
		BasicDBObject obj = new BasicDBObject().append("name", user);
		DBCursor cur = utils.getCollection("users").find(obj);
		cur.next();
		BasicDBObject u1 = (BasicDBObject) cur.curr();
		String postCount = (String) u1.get("count");
		int count = Integer.parseInt(postCount);
		count++;
		BasicDBObject update = new BasicDBObject("$set", new BasicDBObject().append("count", ""+count));
		utils.getCollection("users").update(obj, update);
	}

	public void deleteReply(String fName, String sName, String tName, String text){
		ArrayList<SubForum> subs = forumManager.getSubForums(fName);
		for(int i = 0 ; i < subs.size() ; i++){
			if(subs.get(i).getName().equals(sName)){
				ArrayList<Thread> threads = subs.get(i).getThreads();
				for(int j = 0 ; j < threads.size() ; j++){
					if(threads.get(j).getCaption().equals(tName)){
						threads.get(j).removeReply(text);
						forumManager.updateForum(fName, subs);
					}
				}
			}
		}
	}

	public void deleteSubForum(String fName, String sName){
		LogManager.print("delete sub forum function - need to delete " + sName + " from " + fName + " forum!");
		ArrayList<Forum> forums = forumManager.getAllForums();
		for(int i = 0 ; i < forums.size() ; i++){
			LogManager.print(forums.get(i).getName());
			if(forums.get(i).getName().equals(fName)){
				forums.get(i).removeSub(sName);
				forumManager.updateForum(fName, forums.get(i).getSubs());
			}
		}
	}

	public void deleteThreadForum(String fName, String sName, String tName){
		LogManager.print("Trying to delete thread: " + tName);
		ArrayList<SubForum> subs = forumManager.getSubForums(fName);
		for(int i = 0 ; i < subs.size() ; i++){
			if(subs.get(i).getName().equals(sName)){
				subs.get(i).removeThread(tName);
				forumManager.updateForum(fName, subs);
			}
		}
	}

	public void addNewThread(String fName, String sName, String user, String caption, String text){
		ArrayList<SubForum> subs = forumManager.getSubForums(fName);
		for(int i = 0 ; i < subs.size() ; i++){
			if(subs.get(i).getName().equals(sName)){
				LogManager.print("Adding thread " + caption + " in " + fName + " -> " + sName);
				subs.get(i).addThread(user, caption, text);
				forumManager.updateForum(fName, subs);
				break;
			}
		}
		BasicDBObject obj = new BasicDBObject().append("name", user);
		DBCursor cur = utils.getCollection("users").find(obj);
		cur.next();
		BasicDBObject u1 = (BasicDBObject) cur.curr();
		String postCount = (String) u1.get("count");
		int count = Integer.parseInt(postCount);
		count++;
		BasicDBObject update = new BasicDBObject("$set", new BasicDBObject().append("count", ""+count));
		utils.getCollection("users").update(obj, update);
	}


	public void removeMgr(String user, String name){
		LogManager.print("Trying to remove manager " + user + " from forum - " + name);
		BasicDBObject query = new BasicDBObject().append("name", name);
		DBCursor cur = utils.getCollection("forums").find(query);
		cur.next();
		BasicDBObject obj = (BasicDBObject) cur.curr();
		BasicDBList list = (BasicDBList) obj.get("mgrs");
		BasicDBList newList = new BasicDBList();
		for(int i = 0 ; i < list.size() ; i++){
			BasicDBObject obj1 = (BasicDBObject) list.get(i);
			if(!obj1.get("name").equals(user)){
				LogManager.print((String)obj1.get("name"));
				newList.add(obj1);
			}
		}
		BasicDBObject update = new BasicDBObject("$set", new BasicDBObject().append("mgrs", newList));
		utils.getCollection("forums").update(query, update);
	}

	public void removeMod(String user, String forum, String sub){
		ArrayList<SubForum> subs = forumManager.getSubForums(forum);
		LogManager.print("trying to remove moderator");
		LogManager.print("removing " + user + " from " + forum + " -> " + sub);
		for(int i = 0 ; i < subs.size() ; i++){
			if(subs.get(i).getName().equals(sub)){
				LogManager.print("found " + sub);
				for(int j = 0 ; j < subs.get(i).getMods().size() ; j++){
					LogManager.print("mod number " + j);
					if(subs.get(i).getMods().get(j).equals(user)){
						LogManager.print("trying to remove " + subs.get(i).getMods().get(j));
						subs.get(i).getMods().remove(j);
						forumManager.updateForum(forum, subs);
					}
				}
			}
		}
	}





	public boolean addFriend(String user, String name){
		if(UserQueries.isUserExists(user) && UserQueries.isUserExists(name)){
			ArrayList<String> list = getFriendList(user);
			list.add(name);
			SetFriendList(user, list);
			return true;
		}
		return false;
	}

	public void banUser(String user, String name){
		BasicDBObject obj = new BasicDBObject().append("name", name);
		BasicDBObject obj1  = new BasicDBObject().append("$set", new BasicDBObject().append("banned", "true"));
		utils.getCollection("users").update(obj, obj1);
	}

	public ArrayList<String> getFriendList(String user){
		BasicDBObject obj = new BasicDBObject().append("name", user);
		DBCursor cur = utils.getCollection("users").find(obj);
		if(cur.hasNext())
			cur.next();
		else
			return null;
		BasicDBObject obj1 = (BasicDBObject) cur.curr();
		BasicDBList list = (BasicDBList) obj1.get("friends");
		return utils.DBListToArray(list);
	}


	public void SetFriendList(String user, ArrayList<String> list){
		BasicDBObject obj = new BasicDBObject().append("name", user);
		DBCursor cur = utils.getCollection("users").find(obj);
		if(cur.hasNext())
			cur.next();
		BasicDBObject doc = new BasicDBObject().append("$set", new BasicDBObject().append("friends", utils.ArrayToDBList(list)));
		utils.getCollection("users").update(obj, doc);
	}

	public boolean addComplaint(String user, String mod, String forum, String sub, String text){
		if(UserQueries.isUserRegisteredToForum(user, forum) && forumManager.forumExists(forum) &&
				forumManager.isThereSubforumExists(forum, sub) && 
				UserQueries.isMod(mod, forum, sub) && UserQueries.isUserRegisteredToForum(mod, forum)){
			BasicDBObject doc = new BasicDBObject()
									.append("user", user)
									.append("mod", mod)
									.append("forum", forum)
									.append("sub", sub)
									.append("text", text);
			utils.getCollection("complaints").insert(doc);
			return true;
		}
		else
			return false;
	}
	
	public ArrayList<String> getComplaints(){
		DBCursor cur = utils.getCollection("complaints").find();
		ArrayList<String> result = new ArrayList<String>();
		
		while(cur.hasNext()){
			cur.next();
			String s = "";
			s += cur.curr().get("user") + "'s complaint about the mod: " +
				cur.curr().get("mod") + " from " + cur.curr().get("forum") + " forum, " + 
				cur.curr().get("sub") + " sub: " + cur.curr().get("text");
			result.add(s);
		}
		
		return result;
	}




	public boolean changePassword(String user, String newPass) {
		BasicDBObject obj = new BasicDBObject().append("name", user);
		DBCursor cur = utils.getCollection("users").find(obj);
		if(!cur.hasNext())
			return false;
		if(passExisted(user, newPass))
			return false;
		cur.next();
		BasicDBList list = (BasicDBList) cur.curr().get("prev_pass");
		list.add(new BasicDBObject("pass", newPass));
		BasicDBObject doc = new BasicDBObject().append("$set", new BasicDBObject().append("pass",newPass).append("prev_pass", list));
		utils.getCollection("users").update(obj, doc);
		return true;
	}
	
	private boolean passExisted(String name, String pass){
		BasicDBObject obj = new BasicDBObject().append("name", name);
		DBCursor cur = utils.getCollection("users").find(obj);
		if(!cur.hasNext())
			return false;
		cur.next();
		BasicDBList list = (BasicDBList) cur.curr().get("prev_pass");
		for(int i = 0 ; i < list.size() ; i++){
			BasicDBObject obj1 = (BasicDBObject) list.get(i);
			String prev = (String) obj1.get("pass");
			if(prev.equals(pass))
				return false;
		}
		return true;
	}



}
