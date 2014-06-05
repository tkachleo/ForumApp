package alex.com.MainUi;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;
import java.net.ProtocolException;
import com.forum.managers.LogManager;

public class HttpSender {
	
	public static final int ADD_NEW_FORUM = 1;
	public static final int ADD_NEW_SUB_FORUM = 2;
	public static final int ADD_NEW_THREAD= 3;
	public static final int ADD_NEW_REPLY = 4;
	public static final int ADD_NEW_USER = 5;
	public static final int GET_FORUM_LIST = 6;
	public static final int GET_SUB_FORUM_LIST = 7;
	public static final int GET_THREAD_LIST = 8;
	public static final int GET_REPLY_LIST = 9;
	public static final int UPDATE_FORUM = 10;
	public static final int DELETE_FORUM = 11;
	public static final int DELETE_SUB_FORUM = 12;
	public static final int DELETE_THREAD = 13;
	public static final int DELETE_REPLY = 14;
	public static final int IS_USER_EXISTS = 15;
	public static final int IS_FORUM_EXISTS = 16;
	public static final int IS_SUB_FORUM_EXISTS = 17;
	public static final int IS_ADMIN = 18;
	public static final int IS_MGR = 19;
	public static final int IS_MOD = 20;
	public static final int REGISTER_TO_FORUM = 21;
	public static final int IS_REGISTERED_TO_FORUM = 22;
	public static final int MAKE_MGR = 23;
	public static final int REMOVE_MGR = 24;
	public static final int MAKE_MOD = 25;
	public static final int REMOVE_MOD = 26;
	public static final int IS_AUTHOR = 27;
	public static final int IS_VERIFIED = 28;
	public static final int GET_ALL_FORUMS_RAW = 29;
	public static final int GET_USER_STATUS = 30;
	public static final int MAKE_ADMIN = 31;
	public static final int ADD_COMPLAINT = 32;
	public static final int GET_FORUM_MEMBERS = 33;
	public static final int GET_MOD_REPORT = 34;
	public static final int GET_SUB_POST_COUNT = 35;
	public static final int GET_ALL_USER_MSGS = 36;
	public static final int GET_FORUM_NUMBER = 37;
	public static final int GET_COMPLAINTS = 38;
	public static final int CHANGE_PASSWORD = 39;
	public static final int UPDATE_TIME = 40;
	public static final int GET_USER = 41;
	
	public static String getRequest(String url){
		LogManager.print("sending request to servlet!");
		URL obj;
		HttpURLConnection con;
		//int responseCode;
		StringBuffer response;

		try{
			obj = new URL(url);
		}catch(MalformedURLException e){
			e.printStackTrace();
			return null;
		}
		try{
			con = (HttpURLConnection) obj.openConnection();
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
 
		try{
			con.setRequestMethod("GET");
		}catch(ProtocolException e){
			e.printStackTrace();
			return null;	
		}
 
		//User-Agent is for girls.
		//con.setRequestProperty("User-Agent", USER_AGENT);
		//try{
			//responseCode = con.getResponseCode();
		//}catch(IOException e){
		//	e.printStackTrace();
		//	return null;	
		//}

	//	System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);
		try{
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		 response= new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
	}catch(IOException e){
					e.printStackTrace();
			return null;
	}
		////print result
		//System.out.println(response.toString());
		return response.toString();
	}
}
