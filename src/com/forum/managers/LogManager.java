package com.forum.managers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;


public class LogManager {

	private static final int LOGGER_SIZE = 2000;
	private static int currLogPosition = 0;
	
	static SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd  HH:mm:ss");

	
	private static String[] mLogArray;
	
	public static String[] getLogStack(){
		if (mLogArray == null){
			mLogArray = new String[LOGGER_SIZE];
		}
		return mLogArray;
	}
	
	public static int getLoggerSize(){
		return LOGGER_SIZE;
	}
	
	public static void add(String log){
		String time = ft.format(new Date());
		getLogStack()[currLogPosition%LOGGER_SIZE] = time +" "+ log;
		currLogPosition = (currLogPosition + 1) % LOGGER_SIZE;
	}
	
	
	public String[] getAllLogs(){
		return mLogArray;
	}
	
	public static void print(String log) {
		Logger.getLogger("jene").info(log);
		add(log);
	}

}

