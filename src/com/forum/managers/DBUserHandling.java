package com.forum.managers;

import java.util.ArrayList;

import com.forum.helper.Forum;
import com.forum.helper.SubForum;
import com.forum.helper.Thread;
import com.forum.helper.User;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.forum.managers.DBinterface.*;

public class DBUserHandling implements I_DBUserHandling{
	private DBUtils utils;
	private DBForumManager forumManager;
	private DBUserQueries userQueries;

	protected DBUserHandling(DBUtils utils, DBForumManager forumManager){
		this.utils=utils;
		this.forumManager=forumManager;
	}


	public void addNewUser(User user, boolean admin){
		LogManager.print("Adding a new user: " + user.getName());
		BasicDBList list = new BasicDBList();
		list.add(new BasicDBObject().append("pass", user.getPassword()));
		if(admin){
			BasicDBObject obj = new BasicDBObject()
			.append("name", user.getName())
			.append("email", user.getEmail())
			.append("question", user.getQuestion())
			.append("answer", user.getAnswer())
			.append("pass", user.getPassword())
			.append("prev_pass", list)
			.append("date", user.getPasswordTogo())
			.append("verified", "true")
			.append("banned", "false")
			.append("admin", "true")
			.append("count", "0")
			.append("friends", new BasicDBList())
			.append("regs", new BasicDBList());
			utils.getCollection("users").insert(obj);
		}
		else{
			BasicDBObject obj = new BasicDBObject()
			.append("name", user.getName())
			.append("email", user.getEmail())
			.append("question", user.getQuestion())
			.append("answer", user.getAnswer())
			.append("pass", user.getPassword())
			.append("prev_pass", list)
			.append("date", user.getPasswordTogo())
			.append("verified", "false")
			.append("banned", "false")
			.append("admin", "false")
			.append("count", "0")
			.append("friends", new BasicDBList())
			.append("regs", new BasicDBList());
			utils.getCollection("users").insert(obj);
		}
	}

	public void removeUser(User user){
		BasicDBObject query = new BasicDBObject().append("name", user.getName());
		utils.getCollection("users").remove(query);
	}

	public void registerUserToForum(String user, String forum){
		if(!userQueries.isUserExists(user)){
			return;
		}
		else{
			BasicDBObject query = new BasicDBObject().append("name", user);
			DBCursor cur = utils.getCollection("users").find(query);
			cur.next();
			BasicDBList list = (BasicDBList) cur.curr().get("regs");
			list.add(new BasicDBObject().append("forum", forum));
			BasicDBObject update = new BasicDBObject().append("$set", new BasicDBObject("regs", list));
			utils.getCollection("users").update(query, update);
		}
	}

	public String getUserStatus(String user){
		String result = "";
		if(userQueries.isUserExists(user)){
			BasicDBObject obj = new BasicDBObject().append("name", user);
			DBCursor cur = utils.getCollection("users").find(obj);
			cur.next();
			BasicDBObject u1 = (BasicDBObject) cur.curr();
			String postCount = (String) u1.get("count");
			int count = Integer.parseInt(postCount);
			if(count < 15)
				result = "normal";
			else if(count < 30)
				result = "silver";
			else
				result = "gold";
		}
		else {
			result = "user not exist";
		}
		return result;
	}



	public boolean makeMgr(String user, String name){//name=forum
		boolean changed = false;
		if (userQueries.isUserExists(user) && userQueries.isUserRegisteredToForum(user,name)){
			String status = getUserStatus(user);
			if(status.equals("silver") || status.equals("gold") || DBManager.getInstance().isAdmin(user)){
				ArrayList<Forum> forums = forumManager.getAllForums();
				for(int i = 0 ; i < forums.size() ; i++){
					if(forums.get(i).getName().equals(name)){
						if(forums.get(i).getMgrs().size() < Integer.parseInt(forums.get(i).getMaxMgr()) || forums.get(i).getMaxMgr().equals("0")){
							forums.get(i).addMgr(user);
							changed = true;
						}
					}
				}
			}
		}
		if(changed){
			BasicDBObject query = new BasicDBObject().append("name", name);
			DBCursor cur = utils.getCollection("forums").find(query);
			BasicDBObject obj = (BasicDBObject) cur.next();
			BasicDBList list = (BasicDBList) obj.get("mgrs");
			list.add(new BasicDBObject().append("name", user));
			BasicDBObject update = new BasicDBObject().append("$set", new BasicDBObject().append("mgrs", list));
			utils.getCollection("forums").update(query, update);
		}
		return changed;
	}

	public boolean makeAdmin(String user){
		boolean result = false;
		if(userQueries.isUserExists(user)){
			String status = getUserStatus(user);
			if(status.equals("gold")){
				BasicDBObject query = new BasicDBObject().append("name", user);
				BasicDBObject update = new BasicDBObject("$set", new BasicDBObject().append("admin", "true"));
				utils.getCollection("users").update(query, update);
			}
		}
		return result;
	}

	public boolean makeMod(String user, String forum, String sub){
		boolean changed=false;
		if (userQueries.isUserExists(user) && userQueries.isUserRegisteredToForum(user, forum) ){
			ArrayList<SubForum> subs = forumManager.getSubForums(forum);
			for(int i = 0 ; i < subs.size() ; i++){
				if(subs.get(i).getName().equals(sub)){
					changed=true;
					subs.get(i).addMod(user);
					forumManager.updateForum(forum, subs);
				}
			}
		}
		return changed;
	}


	public void verifyUser(String user){
		LogManager.print("Trying to verify user " + user);
		BasicDBObject query = new BasicDBObject().append("name", user);
		DBCursor cur = utils.getCollection("users").find(query);
		if(!cur.hasNext())
			return;
		else{
			cur.next();
			BasicDBObject update = new BasicDBObject().append("$set", new BasicDBObject().append("verified", "true"));
			utils.getCollection("users").update(query, update);
		}
	}


	public void setUserQueries(DBUserQueries userQueries2) {
		userQueries=userQueries2;

	}

	public ArrayList<String> getAllUserMsgs(String user){
		ArrayList<String> list = new ArrayList<String>();

		ArrayList<Forum> forums = forumManager.getAllForums();
		for(int i = 0 ; i < forums.size() ; i++){
			for(int j = 0 ; j < forums.get(i).getSubs().size() ; j++){
				for(int k = 0 ; k < forums.get(i).getSubs().get(j).getThreads().size() ; k++){
					if(forums.get(i).getSubs().get(j).getThreads().get(k).getUser().equals(user)){
						list.add(forums.get(i).getSubs().get(j).getThreads().get(k).getText());
					}
					for(int l = 0 ; l < forums.get(i).getSubs().get(j).getThreads().get(k).getReplies().size() ; l++){
						if(forums.get(i).getSubs().get(j).getThreads().get(k).getReplies().get(l).getUser().equals(user)){
							list.add(forums.get(i).getSubs().get(j).getThreads().get(k).getReplies().get(l).getText());
						}
					}
				}
			}
		}

		return list;
	}

	public int getSubPostCount(String forum, String sub){
		ArrayList<Thread> threads = forumManager.getThreads(forum, sub);
		int result = 0;

		for(int i = 0 ; i < threads.size() ; i++){
			result++;
			result += threads.get(i).getReplies().size();
		}

		return result;
	}

	public ArrayList<String> getModReport(String forum){
		ArrayList<SubForum> subs = forumManager.getSubForums(forum);
		ArrayList<String> list;
		String s;
		ArrayList<String> result = new ArrayList<String>();
		for(int i = 0 ; i < subs.size() ; i++){
			for(int j = 0 ; j < subs.get(i).getMods().size() ; j++){
				list = getAllUserMsgs(subs.get(i).getMods().get(j));
				s = "";
				for(int k = 0 ; k < list.size() ; k++)
					s += list.get(i) + ", ";
				result.add("name: " + subs.get(i).getMods().get(j) +
						", Sub: " + subs.get(i).getName() + 
						", Appointed by: " + "TODO" +
						", Date: " + "TODO" +
						", Posts: " + s);
			}
		}
		return result;
	}

	public int getForumNumber(){
		return forumManager.getAllForums().size();
	}

	public ArrayList<String> getForumsMembers(){
		ArrayList<String> result = new ArrayList<String>();

		DBCursor cur = utils.getCollection("users").find();
		while(cur.hasNext()){
			cur.next();
			BasicDBObject obj = (BasicDBObject) cur.curr();
			String user = (String) obj.get("name") + " :  ";
			ArrayList<String> list = utils.DBListToArray((BasicDBList) obj.get("regs"));
			for(int i = 0 ; i < list.size() ; i++){
				user += list.get(i) + ", ";
			}
			result.add(user);
		}
		return result;
	}


	public String getPassword(String user) {
		if (userQueries.isUserExists(user)){
			BasicDBObject obj = new BasicDBObject().append("name", user);
			DBCursor cur = utils.getCollection("users").find(obj);
			cur.next();
			BasicDBObject u1 = (BasicDBObject) cur.curr();
			String pass = (String) u1.get("pass");
			return pass;
		}
		else {
			return "user not exist";
		}
		
	}


	public User getUser(String name) {
		BasicDBObject obj = new BasicDBObject().append("name", name);
		DBCursor cur = utils.getCollection("users").find(obj);
		
		cur.next();
		String user = (String) cur.curr().get("name");
		String email = (String) cur.curr().get("email");
		String password = (String) cur.curr().get("pass");
		String question = (String) cur.curr().get("question");
		String answer = (String) cur.curr().get("answer");
		String date = (String) cur.curr().get("date");
		
		return new User(user, email, password, question, answer, date);
	}



}
