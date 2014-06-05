package com.forum.managers;

import java.util.ArrayList;

import com.forum.helper.Forum;
import com.forum.helper.ForumEntry;
import com.forum.helper.Reply;
import com.forum.helper.SubForum;
import com.forum.helper.Thread;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;
import com.forum.managers.DBinterface.*;

public class DBForumManager implements I_DBForumManager {
	private DBUtils utils;

	public DBForumManager(DBUtils utils) {	
			this.utils=utils;
	}
	

	public boolean isSubforumsExists(String forum){
		ArrayList<SubForum> sbf = getSubForums(forum);
		return (!(sbf.isEmpty()));
	}
	
	public boolean isThereSubforumExists(String forum, String sub){
		ArrayList<SubForum> sbf = getSubForums(forum);
		boolean ans = false;
		if (sbf!=null){
			for (int i = 0; i < sbf.size(); i++) {
				ans = sbf.get(i).getName().equals(sub);
				if (ans){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public void addNewForum(ForumEntry forum){
		BasicDBObject obj = new BasicDBObject();
		BasicDBList list1 = new BasicDBList();
		BasicDBList list2 = new BasicDBList();
		obj.append("name", forum.getName()).
								append("desc", forum.getDesc()).
								append("subs", list1).
								append("mgrs", list2).
								append("mgrs_num", "0").
								append("subs_num", "0");
		utils.getCollection("forums").insert(obj);
		DBManager.getInstance().registerUserToForum("admin", forum.getName());
		DBManager.getInstance().makeMgr("admin", forum.getName());
	}
	
	public void changePolicy(String forum, String mgrs_num, String subs_num){
		BasicDBObject query = new BasicDBObject().append("name", forum);
		BasicDBObject obj = new BasicDBObject().append("mgrs_num", mgrs_num).append("subs_num", subs_num);
		BasicDBObject update = new BasicDBObject().append("$set", obj);
		utils.getCollection("forums").update(query, update);
		
	}
	
 
	
	
	public void deleteForum(String name){
		BasicDBObject query = new BasicDBObject().append("name", name);
		utils.getCollection("forums").remove(query);
	}


	public boolean forumExists(String name){
		BasicDBObject obj = new BasicDBObject().append("name", name);
		DBCursor cur = utils.getCollection("forums").find(obj);
		if(cur.hasNext()){
			LogManager.print("forum exists");
			return true;
		}
		else{
			LogManager.print("forum does not exist");
			return false;
		}
	}
	
	public void removeMod(String user, String forum, String sub){
		ArrayList<SubForum> subs = getSubForums(forum);
		LogManager.print("trying to remove moderator");
		LogManager.print("removing " + user + " from " + forum + " -> " + sub);
		for(int i = 0 ; i < subs.size() ; i++){
			if(subs.get(i).getName().equals(sub)){
				LogManager.print("found " + sub);
				for(int j = 0 ; j < subs.get(j).getMods().size() ; j++){
					LogManager.print("mod number " + j);
					if(subs.get(i).getMods().get(j).equals(user)){
						LogManager.print("trying to remove " + subs.get(i).getMods().get(j));
						subs.get(i).getMods().remove(j);
						updateForum(forum, subs);
					}
				}
			}
		}
	}
	

	public ArrayList<SubForum> getSubForums(String forumName){
		ArrayList<Forum> forums = getAllForums();
		for(int i = 0 ; i < forums.size() ; i ++){
			if(forums.get(i).getName().equals(forumName))
				return forums.get(i).getSubs();
		}
		return null;
	}
	
	
	public boolean addNewSubForm(String forum, String sub){
		LogManager.print("Trying to add a new sub forum " + sub + " to the forum " + forum);
		
		ArrayList<Forum> forums = getAllForums();
		ArrayList<SubForum> subs = getSubForums(forum);
		LogManager.print("num of forums : " + forums.size() + "  num of subs : " + subs.size());
		for(int i = 0 ; i < forums.size() ; i++){
			if(forums.get(i).getName().equals(forum)){
				LogManager.print("inside outer if !!!");
				LogManager.print("max subs for " + forum + " is " + forums.get(i).getMaxSubs());
				if(subs.size() < Integer.parseInt(forums.get(i).getMaxSubs()) || forums.get(i).getMaxSubs().equals("0")){
					LogManager.print("inside inner if !!!");
					subs.add(new SubForum(sub, new ArrayList<String>(), new ArrayList<Thread>()));
					updateForum(forum, subs);
					return true;
				}
			}
		}
		return false;
	}
	
	public void updateForum(String forumName, ArrayList<SubForum> subForums){
		BasicDBList subs = new BasicDBList();
		BasicDBList threads = new BasicDBList();
		BasicDBList replies = new BasicDBList();
		for(int i = 0 ; i < subForums.size() ; i++){
			threads = new BasicDBList();
			for(int j = 0 ; j < subForums.get(i).getThreads().size() ; j++){
				replies = new BasicDBList();
				for(int k = 0 ; k < subForums.get(i).getThreads().get(j).getReplies().size() ; k++){
					replies.add(new BasicDBObject("name", subForums.get(i).getThreads().get(j).getReplies().get(k).getUser()).
							append("text", subForums.get(i).getThreads().get(j).getReplies().get(k).getText()));
				}
				threads.add(new BasicDBObject("name", subForums.get(i).getThreads().get(j).getUser()).
						append("caption", subForums.get(i).getThreads().get(j).getCaption()).
						append("text", subForums.get(i).getThreads().get(j).getText()).
						append("replies", replies));
			}
			subs.add(new BasicDBObject("name", subForums.get(i).getName()).
									append("threads", threads).
									append("mods", utils.ArrayToDBList(subForums.get(i).getMods())));

		}
		BasicDBObject query = new BasicDBObject().append("name", forumName);
		BasicDBObject newQuery = new BasicDBObject("$set", new BasicDBObject().append("subs", subs));
		utils.getCollection("forums").update(query, newQuery);

	}
	
	public String getAllForumsRAW(){
		DBCursor cur = utils.getCollection("forums").find();
		BasicDBList list = new BasicDBList();
		while(cur.hasNext()){
			list.add(cur.next());
		}
		return JSON.serialize(list);
	}
	
	public ArrayList<Forum> getAllForums(){
		ArrayList<Forum> forums = new ArrayList<Forum>();
		ArrayList<SubForum> subs;
		ArrayList<Thread> threads;
		ArrayList<Reply> replies;
		ArrayList<String> mgrs;
		ArrayList<String> mods;
		String forum, desc, sub, initName, initCap, initText, repName, repText, mgr_num, sub_num;
		DBCursor cur = utils.getCollection("forums").find();
		while(cur.hasNext()){
			subs = new ArrayList<SubForum>();
			threads = new ArrayList<Thread>();
			replies = new ArrayList<Reply>();
			mgrs = new ArrayList<String>();
			cur.next();
			forum = (String) cur.curr().get("name");
			desc = (String) cur.curr().get("desc");
			mgr_num = (String) cur.curr().get("mgrs_num");
			sub_num = (String) cur.curr().get("subs_num");
			BasicDBList dbMgrs = (BasicDBList) cur.curr().get("mgrs");
			for(int m = 0 ; m < dbMgrs.size() ; m++){
				BasicDBObject mgr = (BasicDBObject) dbMgrs.get(m);
				mgrs.add((String)mgr.get("name"));
			}
			BasicDBList list = (BasicDBList) cur.curr().get("subs");
			for(int i = 0 ; i < list.size() ; i++){
				mods = new ArrayList<String>();
				threads = new ArrayList<Thread>();
				BasicDBList dbMods = (BasicDBList) ((BasicDBObject) list.get(i)).get("mods");
				for(int n = 0 ; n < dbMods.size() ; n++){
					BasicDBObject mod = (BasicDBObject) dbMods.get(n);
					mods.add((String)mod.get("name"));
				}
				sub = (String) ((BasicDBObject) list.get(i)).get("name");
				BasicDBList list2 = (BasicDBList) ((BasicDBObject) list.get(i)).get("threads");
				for(int j = 0 ; j < list2.size() ; j++){
					replies = new ArrayList<Reply>();
					initName = (String) ((BasicDBObject) list2.get(j)).get("name");
					initCap = (String) ((BasicDBObject) list2.get(j)).get("caption");
					initText = (String) ((BasicDBObject) list2.get(j)).get("text");
					BasicDBList list3 = (BasicDBList) ((BasicDBObject) list2.get(j)).get("replies");
					for(int k = 0 ; k < list3.size() ; k++){
						repName = (String) ((BasicDBObject) list3.get(k)).get("name");
						repText = (String) ((BasicDBObject) list3.get(k)).get("text");
						replies.add(new Reply(repName, repText));
					}
					threads.add(new Thread(initName, initCap, initText, replies));
				}
				subs.add(new SubForum(sub, mods, threads));
			}
			forums.add(new Forum(forum, desc, mgrs, subs, mgr_num, sub_num));
		}
		return forums;
	}
	
	public ArrayList<Thread> getThreads(String fName, String sName){
		ArrayList<SubForum> subs = getSubForums(fName);
		if(subs != null){
			for(int i = 0 ; i < subs.size() ; i++)
				if(subs.get(i).getName().equals(sName))
					return subs.get(i).getThreads();
		}
		return null;
	}
	
	
}
