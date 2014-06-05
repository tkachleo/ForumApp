package com.forum.managers.DBinterface;

import java.util.ArrayList;

import com.forum.managers.DBUserHandling;
import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;

public interface I_DBUtils {
	DBCollection getCollection(String collectionName);
	public void setUserHandling(DBUserHandling userHandaling);
	BasicDBList ArrayToDBList(ArrayList<String> list);
	public ArrayList<String> DBListToArray(BasicDBList list);

}
