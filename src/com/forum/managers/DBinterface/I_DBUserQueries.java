package com.forum.managers.DBinterface;

public interface I_DBUserQueries {
	public boolean isFriendExists(String user, String Friend);
	public boolean isAdmin(String user);
	public boolean isMgr(String user, String forum);
	public boolean isMod(String user, String forum, String sub);
	public boolean isAuthor(String forum, String sub, String thread, String user, String text);
	public boolean isBanned(String user);
	public boolean isUserExists(String user);
	public boolean isVerified(String user);
	public boolean isUserRegisteredToForum(String user, String forum);

}
