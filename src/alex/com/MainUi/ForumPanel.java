package alex.com.MainUi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window.Type;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.forum.client.util.SuperSimpleHttpUtils;
import com.forum.helper.Forum;
import com.forum.helper.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import alex.com.MainUi.LoginPanel;

public class ForumPanel extends JPanel{


	private static final long serialVersionUID = 3140197758828535521L;
		static ArrayList<JButton> buttonForums = new ArrayList<>();
		private LoginPanel loginPanel;
		private JPanel leftPanel;
		private JPanel newLeftPanel;
		private static ArrayList<Forum> forumArray;
		private final String URL = "http://tkach.herokuapp.com/request?MSG_NUM=";
		private MyActionListener myActionListener;
	
	
	
	public ForumPanel(LoginPanel loginPanel, MyActionListener myActionListener) {
			this.loginPanel =loginPanel;
			this.leftPanel=loginPanel.getMyActionListener().getMainFrame().getLeftSplitPanel();
			this.newLeftPanel = new JPanel();
			this.myActionListener =myActionListener;
		}



	public void initForms() {
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		String response = SuperSimpleHttpUtils.getRequest(URL
				+ SuperSimpleHttpUtils.GET_ALL_FORUMS_RAW);
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<ArrayList<Forum>>(){}.getType();
		forumArray = gson.fromJson(response, type);
		 for (Forum forum : forumArray) {
			 getButtonForums().add(new JButton(forum.getName()));
		}
		JLabel welcomeUser = new JLabel(LoginPanel.getLogInText().getText() + " Logged in!");
		this.add(welcomeUser);
		for (JButton forum : buttonForums) {
			forum.setName(forum.getText());
			forum.addActionListener(loginPanel.getMyActionListener());
			this.add(forum);
		}
		addLeftPanelButtons();
		
	}
	
	public static ArrayList<JButton> getButtonForums(){
		return buttonForums;
	}
	
	public ArrayList<Forum> getForumArray(){
		return forumArray;
	}


	private void addLeftPanelButtons() {
		newLeftPanel.setLayout(new BoxLayout(newLeftPanel, BoxLayout.Y_AXIS));
		JButton LogOut = new JButton("Log Out");
		LogOut.setName("LogOut");
		LogOut.addActionListener(loginPanel.getMyActionListener());
		newLeftPanel.add(LogOut);
		JButton MainForumsPage = new JButton("Main Page");
		MainForumsPage.setName("mainForums");
		MainForumsPage.addActionListener(myActionListener);
		newLeftPanel.add(MainForumsPage);
//		JButton addNewMainForum = new JButton("New Main Forum");
////		MainForumsPage.setName("NewMainForum");
////		MainForumsPage.addActionListener(myActionListener);
////		newLeftPanel.add(addNewMainForum);
		
		this.loginPanel.getMyActionListener().getMainFrame().ChangeLeftSplitPanel(newLeftPanel);
		
	}



	public JPanel getNewLeftPanel() {
		return newLeftPanel;
	}

}
