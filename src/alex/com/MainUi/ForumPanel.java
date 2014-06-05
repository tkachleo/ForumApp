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
	
	
	
	public ForumPanel(LoginPanel loginPanel) {
			this.loginPanel =loginPanel;
			this.leftPanel=loginPanel.getMyActionListener().getMainFrame().getLeftSplitPanel();
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
		this.leftPanel.add(new JButton("newww"));
		
	}

}
