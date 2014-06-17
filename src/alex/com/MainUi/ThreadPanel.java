package alex.com.MainUi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

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
	private String forumName;
	private String subNmae;
	private JTextField addNewThreadText;
	
	
	
	public ThreadPanel(MyActionListener myActionListener2) {
		this.myActionListener = myActionListener2;
		this.newLeftPanel = new JPanel();
	}
	
	public void initAfterAdd() {
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		String s = SuperSimpleHttpUtils.getRequest(URL+SuperSimpleHttpUtils.GET_ALL_FORUMS_RAW);
		ArrayList<com.forum.helper.SubForum> subs = DBService.getSubForums(s, this.forumName);


		JLabel welcome = new JLabel("Welcome To " + this.subNmae);
		this.add(welcome);
		for (JButton threadButton : threadArrayButton) {
			threadButton.addActionListener(myActionListener);
			threadButton.setName(this.forumName+"."+this.subNmae+"."+threadButton.getText());
			this.add(threadButton);
		}
		this.myActionListener.getMainFrame().changeRightSplitPanel(this);
		
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
		this.forumName = names[0];
		this.subNmae = names[1];
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
		JPanel left =myActionListener.getMainFrame().getLeftSplitPanel();
		if (left.getComponentCount() == 2) {
			newLeftPanel
					.setLayout(new BoxLayout(newLeftPanel, BoxLayout.Y_AXIS));
			Component[] components = left.getComponents();
			for (Component component : components) {
				newLeftPanel.add(component);
			}
			JButton newThread = new JButton("add new Thread");
			newThread.setName("newThread");
			newThread.addActionListener(myActionListener);
			newLeftPanel.add(newThread);

			myActionListener.getMainFrame().ChangeLeftSplitPanel(newLeftPanel);
		}
	}

	public ArrayList<JButton> getthreadArrayButto() {
		return threadArrayButton;
	}

	public ArrayList<Thread> getThreadsArray() {
		return threadsArray;
	}
	
	public void addnewThread(){
		JPanel addnewThreadPanel = new JPanel();
		addnewThreadPanel.setLayout(null);
		JLabel addnewThread = new JLabel("add new Thread:");
		addnewThread.setForeground(Color.blue);
		addnewThread.setFont(new Font("Serif", Font.BOLD, 20));
		addnewThread = new JLabel("Name:");
		this.addNewThreadText = new JTextField();
		JButton logInButton = new JButton("add");
		logInButton.setName("addnewThread");
	    logInButton.addActionListener(myActionListener);
	    addnewThread.setBounds(100, 30, 400, 30);
	    addnewThread.setBounds(80, 70, 200, 30);
		addNewThreadText.setBounds(300, 70, 200, 30);
		logInButton.setBounds(80, 150, 100, 30);
		addnewThreadPanel.add(addnewThread);
		addnewThreadPanel.add(addnewThread);
		addnewThreadPanel.add(addNewThreadText);
		addnewThreadPanel.add(logInButton);
		myActionListener.getMainFrame().changeRightSplitPanel(addnewThreadPanel);
		
	}
	public void addnewThreadPressed() {
		try {
			String text = "";
			text = URLEncoder.encode(addNewThreadText.getText(), "UTF-8");
			String user = myActionListener.getLogin().getLogedUser();
			user = URLEncoder.encode(user, "UTF-8");
			this.forumName =URLEncoder.encode(this.forumName, "UTF-8");
			this.subNmae =URLEncoder.encode(this.subNmae, "UTF-8");
			String caption = URLEncoder.encode("what", "UTF-8");
			String s = SuperSimpleHttpUtils.getRequest(URL+SuperSimpleHttpUtils.ADD_NEW_THREAD + "&user="+user + "&forum="+this.forumName+
					"&sub="+this.subNmae+"&forum="+this.forumName+"&caption="+caption +"&text="+text);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton newthread= new JButton(addNewThreadText.getText());
		this.threadArrayButton.add(newthread);
		this.initAfterAdd();
		
	}

	public JPanel getNewLeftPanel() {
		return newLeftPanel;
	}
		
	



}
