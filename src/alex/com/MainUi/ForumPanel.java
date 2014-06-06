package alex.com.MainUi;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import alex.com.MainUi.LoginPanel;

public class ForumPanel extends JPanel{


	private static final long serialVersionUID = 3140197758828535521L;
		ArrayList<JButton> forums = new ArrayList<>();
		private LoginPanel loginPanel;
		private JPanel leftPanel;
		private JPanel newLeftPanel;
	
	
	
	public ForumPanel(LoginPanel loginPanel) {
			this.loginPanel =loginPanel;
			this.leftPanel=loginPanel.getMyActionListener().getMainFrame().getLeftSplitPanel();
			this.newLeftPanel = new JPanel();
		}



	public void initForms() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.forums.add(new JButton("Tapuz"));
		this.forums.add(new JButton("SOmeShit"));
		this.forums.add(new JButton("Tapuz"));
		JLabel welcomeUser = new JLabel(LoginPanel.getLogInText().getText() + " Logged in!");
		this.add(welcomeUser);
		for (JButton forum : forums) {
			this.add(forum);
		}
		addLeftPanelButtons();
		
	}



	private void addLeftPanelButtons() {
		newLeftPanel.setLayout(new BoxLayout(newLeftPanel, BoxLayout.Y_AXIS));
		JButton LogOut = new JButton("Log Out");
		LogOut.setName("LogOut");
		LogOut.addActionListener(loginPanel.getMyActionListener());
		newLeftPanel.add(LogOut);		
		newLeftPanel.add(new JButton("newww"));
		newLeftPanel.add(new JButton("newww"));
		this.loginPanel.getMyActionListener().getMainFrame().ChangeLeftSplitPanel(newLeftPanel);
		
	}

}
