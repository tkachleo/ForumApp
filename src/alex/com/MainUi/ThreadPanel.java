package alex.com.MainUi;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.forum.client.util.DBService;
import com.forum.client.util.SuperSimpleHttpUtils;
import com.forum.helper.SubForum;
import com.forum.helper.Thread;

public class ThreadPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private MyActionListener myActionListener;
	private ForumPanel forumPanel;
	private final String URL = "http://tkach.herokuapp.com/request?MSG_NUM=";
	private ArrayList<JButton> threadArrayButton = new ArrayList<>();
	private ArrayList<Thread> threadsArray;
	private JPanel newLeftPanel;
	
	
	
	public ThreadPanel(MyActionListener myActionListener2) {
		this.myActionListener = myActionListener2;
		this.newLeftPanel = new JPanel();
	}

	public void init(JButton button, ForumPanel forumPanel) {
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.threadArrayButton.clear();
		this.forumPanel =forumPanel;	
		System.out.println(button.getName());
		String[] names= button.getName().split("\\.");
		String s = SuperSimpleHttpUtils.getRequest(URL+SuperSimpleHttpUtils.GET_ALL_FORUMS_RAW);
		ArrayList<com.forum.helper.SubForum> subs = DBService.getSubForums(s, names[0]);
		for (SubForum subForum : subs) {
			if(subForum.getName().equals(names[1])){
				this.threadsArray=subForum.getThreads();
				for (com.forum.helper.Thread t : subForum.getThreads()) {
					threadArrayButton.add(new JButton(t.getText()));
					
				}
			}
		}

		JLabel welcome = new JLabel("Welcome To " + button.getText());
		this.add(welcome);
		for (JButton threadButton : threadArrayButton) {
			threadButton.addActionListener(myActionListener);
			threadButton.setName(names[0]+"."+names[1]+"."+threadButton.getText());
			this.add(threadButton);
		}
		this.myActionListener.getMainFrame().changeRightSplitPanel(this);
		
		changeLeftPanel();
		
	}

	private void changeLeftPanel() {
		newLeftPanel.setLayout(new BoxLayout(newLeftPanel, BoxLayout.Y_AXIS));
		JButton LogOut = new JButton("Log Out");
		LogOut.setName("LogOut");
		LogOut.addActionListener(myActionListener);
		newLeftPanel.add(LogOut);
		JButton MainForumsPage = new JButton("Main Page");
		MainForumsPage.setName("mainForums");
		MainForumsPage.addActionListener(myActionListener);
		newLeftPanel.add(MainForumsPage);
		JButton newThread = new JButton("add new Thread");
		MainForumsPage.setName("newThread");
		MainForumsPage.addActionListener(myActionListener);
		newLeftPanel.add(newThread);
		
//		myActionListener.getMainFrame().ChangeLeftSplitPanel(newLeftPanel);
	}

	public ArrayList<JButton> getthreadArrayButto() {
		return threadArrayButton;
	}

	public ArrayList<Thread> getThreadsArray() {
		return threadsArray;
	}



}
