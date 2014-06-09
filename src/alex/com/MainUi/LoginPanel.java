package alex.com.MainUi;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.forum.client.util.SuperSimpleHttpUtils;
import com.forum.client.util.MySession;
import com.forum.client.util.SuperSimpleHttpUtils;

public class LoginPanel  extends JPanel{


	private static final long serialVersionUID = -1607680442027973736L;
	private JPanel rightPanel;
	JLabel login, name,createPass;
	private static JTextField logInText;
	JButton logInButton;
	private static JPasswordField passText;
	ArrayList<JButton> forums = new ArrayList<>();
	private MyActionListener myActionListener;
	private final String URL = "http://tkach.herokuapp.com/request?MSG_NUM=";
	private ForumPanel fPanel;
	
	public LoginPanel(JPanel rightPanel, MyActionListener myActionListener) {
		this.rightPanel=rightPanel;
		this.myActionListener=myActionListener;
	}

	public void initFrame() {
		this.setLayout(null);
		login = new JLabel("Log in:");
		login.setForeground(Color.blue);
		login.setFont(new Font("Serif", Font.BOLD, 20));
		name = new JLabel("Name:");
		createPass = new JLabel("Create Passowrd:");
		logInText = new JTextField();
		passText = new JPasswordField();
		logInButton = new JButton("Log in");
		logInButton.setName("logIn");
	    logInButton.addActionListener(myActionListener);
		login.setBounds(100, 30, 400, 30);
		name.setBounds(80, 70, 200, 30);
		createPass.setBounds(80, 100, 200, 30);
		logInText.setBounds(300, 70, 200, 30);
		passText.setBounds(300, 100, 200, 30);
		logInButton.setBounds(80, 150, 100, 30);
		this.add(login);
		this.add(name);
		this.add(logInText);
		this.add(createPass);
		this.add(passText);
		this.add(logInButton);
	}


	public void logUser() {
		String exist = SuperSimpleHttpUtils.getRequest(URL
				+ SuperSimpleHttpUtils.IS_USER_EXISTS + "&user="
				+ logInText.getText());
		if(exist.equals("true")){
		this.fPanel = new ForumPanel(this, myActionListener);
		fPanel.initForms();
		this.myActionListener.getMainFrame().changeRightSplitPanel(fPanel);
		}
		else{
			JPanel userNotExistPanel = new JPanel();
			JLabel userNotExsitLabel = new JLabel("User not Exist");
			userNotExsitLabel.setForeground(Color.blue);
			userNotExsitLabel.setFont(new Font("Serif", Font.BOLD, 25));
			userNotExistPanel.add(userNotExsitLabel );
			this.myActionListener.getMainFrame().changeRightSplitPanel(userNotExistPanel);
			
		}
	}



	public static JTextField getLogInText() {
		return logInText;
	}
	
	public MyActionListener getMyActionListener(){
		return this.myActionListener;
	}

	public ForumPanel getfPanel() {
		return fPanel;
	}
	
	





}
