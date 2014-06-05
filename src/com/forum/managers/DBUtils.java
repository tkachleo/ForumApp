package com.forum.managers;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.forum.helper.User;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import com.forum.managers.DBinterface.*;

public class DBUtils implements I_DBUtils{
	private static DBUtils instance = null;
	private static DB db;
	private static MongoURI mongoURI;
	private DBUserHandling userHandling;

	private DBUtils(){
		mongoURI = new MongoURI("mongodb://heroku_app22673344:alv44a8t3i3asu5fpllejk31ug@ds033419.mongolab.com:33419/heroku_app22673344");
		LogManager.print("MONGO URI:----"+mongoURI+"------");
		try {
			db = mongoURI.connectDB();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
		db.authenticate(mongoURI.getUsername(), mongoURI.getPassword()); 
	}
	
	public static DBUtils getInstance(){
		if (instance == null  ){
			instance = new DBUtils();
			

		}
		return instance;
	}
	
	public DBCollection getCollection(String collectionName) {
		return db.getCollection(collectionName);
	}
	
	public void cleanDB(){
		DBCursor cur = getCollection("forums").find();
		while(cur.hasNext()){
			getCollection("forums").remove(cur.next());
		}
		
		DBCursor cur1 = getCollection("complaints").find();
		while(cur1.hasNext()){
			getCollection("complaints").remove(cur1.next());
		}
		
		DBCursor cur2 = getCollection("users").find();
		while(cur2.hasNext()){
			getCollection("users").remove(cur2.next());
		}
		userHandling.addNewUser(new User("admin", "looe87@gmail.com", "admin", "", ""), true);
	}

	public void setUserHandling(DBUserHandling userHandaling) {
		this.userHandling =userHandaling;
		
	}
	
	public BasicDBList ArrayToDBList(ArrayList<String> list){
		BasicDBList res = new BasicDBList();
		BasicDBObject obj;
		for(int i = 0 ; i < list.size() ; i++){
			obj = new BasicDBObject().append("name", list.get(i));
			res.add(obj);
		}
		return res;
	}
	
	public ArrayList<String> DBListToArray(BasicDBList list){
		ArrayList<String> res = new ArrayList<String>();
		BasicDBObject obj;
		String s;
		for(int i = 0 ; i < list.size() ; i++){
			obj = (BasicDBObject)list.get(i);
			s = (String)obj.get("name");
			res.add(s);
		}
		return res;
	}
}
