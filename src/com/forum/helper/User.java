package com.forum.helper;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class User {
	
	private String name;
	private String password;
	private String email;
	private String question;
	private String answer;
	private ArrayList<String> friends;
	private long passwordTogo;
	Calendar cal;
	Date today ;
	
	public User(String name, String email, String password, String question, String answer){
		this.name = name;
		this.password = password;
		this.email = email;
		this.question = question;
		this.answer = answer;
		this.friends = new ArrayList<String>();
		setPasswordCountDown();
		
		
	}
	
	public User(String name, String email, String password, String question, String answer, String date){
		this.name = name;
		this.password = password;
		this.email = email;
		this.question = question;
		this.answer = answer;
		this.friends = new ArrayList<String>();
		this.passwordTogo = Long.parseLong(date);
		setPasswordCountDown();
		
	}
	
	@SuppressWarnings("deprecation")
	private void setPasswordCountDown() {
		cal = Calendar.getInstance();
		Date currentDate = new Date();
		cal.set(Calendar.YEAR, currentDate.getYear()+1900);
		if(currentDate.getDate()+7>31){
			cal.set(Calendar.MONTH, currentDate.getMonth()+1);
			cal.set(Calendar.DAY_OF_MONTH, (currentDate.getDate()+7)%31);
		}
		else{
		cal.set(Calendar.MONTH, currentDate.getMonth());
		cal.set(Calendar.DAY_OF_MONTH, currentDate.getDate()+7);
		}
		cal.set(Calendar.HOUR_OF_DAY,currentDate.getHours());
		cal.set(Calendar.MINUTE, currentDate.getMinutes());
		cal.set(Calendar.SECOND, currentDate.getSeconds());
		cal.set(Calendar.MILLISECOND, 0);
		today = new Date();
		System.out.println("Today:  "
				+ SimpleDateFormat.getDateTimeInstance().format(today));
		System.out.println("Target: "
				+ SimpleDateFormat.getDateTimeInstance().format(cal.getTime()));
		System.out.println();
		passwordTogo = (cal.getTimeInMillis() - today.getTime()) / 1000;
		
	}
		
	public long getPasswordTogo(){
		today = new Date();
		passwordTogo = (cal.getTimeInMillis() - today.getTime()) / 1000;
		return passwordTogo;
	}

	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getQuestion(){
		return this.question;
	}
	
	public String getAnswer(){
		return this.answer;
	}
	
	public ArrayList<String> getFriends(){
		return this.friends;
	}
	
	public String getEmail(){
		return this.email;	
	}
	
	public boolean isPasswordOutOfDate(){
		return getPasswordTogo()<=0;
	}
	
	public void updatePassword(String newPassword ){
		this.password =newPassword;
		setPasswordCountDown();	
	}
	
	
	
}
