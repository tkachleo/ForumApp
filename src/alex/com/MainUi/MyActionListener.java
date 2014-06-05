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



	public MyActionListener(JPanel rightPanel, JSplitPane splitCenterPanel, MainFrame mainFrame ) {
		this.mainPanel = rightPanel;
		this.reg= new RgistartionPanel(this);
		this.login = new LoginPanel(rightPanel,this);
		this.mainFrame = mainFrame;
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
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
			this.mainFrame.ChangeSplitPanel(mainPanel);
		break;
		}

	}


	

	private void MakeRegisterPanel(String s) {
		if(s=="R"){
			reg.initFrame();
			this.mainFrame.ChangeSplitPanel(reg);
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
			this.mainFrame.ChangeSplitPanel(login);
		}
				
	}
	
	private void LogOutPanel() {
		JPanel logOutPanel = new JPanel();
		JLabel logoutLable = new JLabel("who are you ??you Are Loged Out!!");
		logoutLable.setForeground(Color.blue);
		logoutLable.setFont(new Font("Serif", Font.BOLD, 25));
		logOutPanel.add(logoutLable );
		this.mainFrame.ChangeSplitPanel(logOutPanel);
	}



	public MainFrame getMainFrame() {
		return this.mainFrame;
	}

}