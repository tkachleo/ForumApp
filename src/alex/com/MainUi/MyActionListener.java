package alex.com.MainUi;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MyActionListener implements ActionListener {


	private JPanel mainPanel;
	private RgistartionPanel reg;
	private LoginPanel login;
	private MainFrame mainFrame;
	private JPanel mainLeftPanel;
	private SubForumPanel subForum;
	private ThreadPanel threadPanel;
	private InsideThreadPanel inThread;



	public MyActionListener(JPanel rightPanel, JSplitPane splitCenterPanel, MainFrame mainFrame, JPanel leftPanel ) {
		this.mainPanel = rightPanel;
		this.reg= new RgistartionPanel(this);
		this.login = new LoginPanel(rightPanel,this);
		this.mainFrame = mainFrame;
		this.mainLeftPanel = leftPanel;
		this.subForum  = new SubForumPanel(this);
		this.threadPanel = new ThreadPanel(this);
		this.inThread = new InsideThreadPanel(this); 
		
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		System.out.println(source.getName());
		switch (source.getName()) {
		case "Register": 
			MakeRegisterPanel("R");
			break;
		case "Login" :
			MakeLoginPanel("");
			break;
		case "LogOut" : LogOutPanel();
			break;
		case "SubmitReg": MakeRegisterPanel("S");
			break;
		case "Clear" : 
			MakeRegisterPanel("C");
			break;
		case "logIn" :
			MakeLoginPanel("l");
		break;
		case "Home" :
			this.mainFrame.changeRightSplitPanel(mainPanel);
			break;
		case "mainForums":
			this.mainFrame.changeRightSplitPanel(this.login.getfPanel());
			break;
		case "send":
			threadPressed("send",source);
			break;
		case "newThread":
			addnewThread();
			break;
		case "addnewThread":
			addpreesedinNewThread();
			break;
//		case "backTosub":
//			this.mainFrame.changeRightSplitPanel(this.subForum);
//			this.mainFrame.ChangeLeftSplitPanel(this.threadPanel.getNewLeftPanel());
//			break;
			
		default: 
			for (JButton  button  : ForumPanel.getButtonForums()) {
				if(button.equals(source)){
					mainForumPressed(button);
				}
			}
			for (JButton  button  : subForum.getSubForumButton()) {
				if(button.equals(source)){
					subForumPressed(button);
				}
			}
			for (JButton  button  : threadPanel.getthreadArrayButto()) {
				if(button.equals(source)){
					threadPressed("", button);
				}
			}
		break;
		}

	}



	private void addpreesedinNewThread() {
		this.threadPanel.addnewThreadPressed();
		
	}



	private void addnewThread() {
		this.threadPanel.addnewThread();
		
	}



	private void threadPressed(String string, JButton button) {
		if (string.equals("send")) {
			this.inThread.addComment();
			
		} else {
			this.inThread.init(button, threadPanel);
		}

	}


	private void subForumPressed(JButton button) {
		this.threadPanel.init(button, login.getfPanel());
		
	}



	private void mainForumPressed(JButton button) {
		this.subForum.init(button , login.getfPanel());
		
		
	}



	private void MakeRegisterPanel(String s) {
		if(s=="R"){
			reg.initFrame();
			this.mainFrame.changeRightSplitPanel(reg);
		}
		if(s=="S"){
			reg.submit();
		}
		if(s=="C"){
			reg.clear();
		}


	}
	private void MakeLoginPanel(String s) {
		if(s =="l"){
			login.logUser();
		}
		else{
			login.initFrame();
			this.mainFrame.changeRightSplitPanel(login);
		}
				
	}
	
	private void LogOutPanel() {
		JPanel logOutPanel = new JPanel();
		JLabel logoutLable = new JLabel("who are you ??you Are Loged Out!!");
		logoutLable.setForeground(Color.blue);
		logoutLable.setFont(new Font("Serif", Font.BOLD, 25));
		logOutPanel.add(logoutLable );
		this.mainFrame.changeRightSplitPanel(logOutPanel);
		this.mainFrame.ChangeLeftSplitPanel(mainLeftPanel);
		
		
	}



	public MainFrame getMainFrame() {
		return this.mainFrame;
	}



	public LoginPanel getLogin() {
		return login;
	}

}
