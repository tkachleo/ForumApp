package com.forum.client.util;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.forum.helper.Forum;
import com.forum.helper.Reply;
import com.forum.helper.SubForum;
import com.forum.helper.Thread;
import com.forum.managers.LogManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DBService {

	public static ArrayList<Forum> getAllForums(String s){
		Gson gson = new Gson();
		LogManager.print(s);
		Type collectionType = new TypeToken<ArrayList<Forum>>(){}.getType();
		return gson.fromJson(s, collectionType);
	}
	
	public static ArrayList<SubForum> getSubForums(String s, String forumName){
		ArrayList<Forum> forums = getAllForums(s);
		for(int i = 0 ; i < forums.size() ; i ++){
			if(forums.get(i).getName().equals(forumName))
				return forums.get(i).getSubs();
		}
		return null;
	}
	
	public static ArrayList<Thread> getThreads(String s, String fName, String sName){
		ArrayList<SubForum> subs = getSubForums(s, fName);
		if(subs != null){
			for(int i = 0 ; i < subs.size() ; i++)
				if(subs.get(i).getName().equals(sName))
					return subs.get(i).getThreads();
		}
		return null;
	}
	
	public static ArrayList<Reply> getReplies(String s, String fName, String sName, String tName){
		ArrayList<Thread> threads = getThreads(s, fName, sName);
		if(threads != null){
			for(int i = 0 ; i < threads.size() ; i++){
				if(threads.get(i).getCaption().equals(tName)){
					return threads.get(i).getReplies();
				}
			}
		}
		return null;
	}
	
}
