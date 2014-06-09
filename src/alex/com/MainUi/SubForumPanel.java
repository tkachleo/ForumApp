package alex.com.MainUi;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.forum.client.util.DBService;
import com.forum.client.util.SuperSimpleHttpUtils;
import com.forum.helper.Forum;
import com.forum.helper.SubForum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SubForumPanel extends JPanel{


	private static final long serialVersionUID = 1L;
	private MyActionListener myActionListener;
	private ForumPanel forumPanel;
	private final String URL = "http://tkach.herokuapp.com/request?MSG_NUM=";
	private ArrayList<JButton> SubForumButton = new ArrayList<>();

	public SubForumPanel(MyActionListener myActionListener) {
		this.myActionListener = myActionListener;
		
	}

	public void init(JButton button, ForumPanel forumPanel) {
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.SubForumButton.clear();
		this.forumPanel =forumPanel;	
		String s = SuperSimpleHttpUtils.getRequest(URL+SuperSimpleHttpUtils.GET_ALL_FORUMS_RAW);
		ArrayList<com.forum.helper.SubForum> subs = DBService.getSubForums(s, button.getText());
		for (SubForum subForum : subs) {
			this.SubForumButton.add(new JButton(subForum.getName()));
		}
		JLabel welcome = new JLabel("Welcome To " + button.getText());
		this.add(welcome);
		for (JButton subButton : SubForumButton) {
			subButton.addActionListener(myActionListener);
			subButton.setName(button.getText()+"."+subButton.getText());
			this.add(subButton);
		}
		this.myActionListener.getMainFrame().changeRightSplitPanel(this);
		
		changeLeftPanel();
		
	}

	private void changeLeftPanel() {
		
		
	}

	public ArrayList<JButton> getSubForumButton() {
		return SubForumButton;
	}

}
