package alex.com.MainUi;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.forum.client.util.SuperSimpleHttpUtils;
import com.forum.helper.Reply;
import com.forum.helper.Thread;

public class InsideThreadPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MyActionListener myActionListener;
	private static JTextArea coment =new JTextArea();
	 
	private final String URL = "http://tkach.herokuapp.com/request?MSG_NUM=";
	private String[] names;
	private Thread t;
	private String forumName;
	private String subName;
	private String threadName;
	
	public InsideThreadPanel(MyActionListener myActionListener) {
		this.myActionListener = myActionListener;
	}

	public void init(JButton button, ThreadPanel threadPanel) {
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.names= button.getName().split("\\.");
		this.forumName =names[0];
		this.subName =names[1];
		this.threadName=names[2];
		for (com.forum.helper.Thread t : threadPanel.getThreadsArray()) {
			if(names[2].equals(t.getText())){
				JLabel welcome  = new JLabel("welcome to "+names[2]+" Thread");
				this.add(welcome);
				initializeCenter(names , t);
			}
		}
		this.myActionListener.getMainFrame().changeRightSplitPanel(this);
		changeLeftPanel();
		
	}

	private void changeLeftPanel() {
		JPanel left =myActionListener.getMainFrame().getLeftSplitPanel();
		if (left.getComponentCount() == 3) {
			left.remove(2);
			
		}
//		JButton backtoSub = new JButton("back to sub");
//		backtoSub.addActionListener(myActionListener);
//		backtoSub.setName("backTosub");
//		left.add(backtoSub);
		
	}

	private void initializeCenter(String[] names, Thread t) {
		this.t=t;
		JLabel ThreadName = new JLabel(names[2]);
		ThreadName.setForeground(Color.blue);
		ThreadName.setFont(new Font("Serif", Font.BOLD, 25));
		this.add(ThreadName);
		JLabel about = new JLabel(t.getCaption());
		about.setForeground(Color.blue);
		about.setFont(new Font("Serif", Font.BOLD, 18));
		this.add(about);
		JPanel threadCommentPanel = new JPanel();
		threadCommentPanel.setLayout(new BoxLayout(threadCommentPanel, BoxLayout.Y_AXIS));
		for (Reply reply : t.getReplies()) {
			JLabel replyname = new JLabel("Reply From: "+reply.getUser());
			replyname.setForeground(Color.black);
			replyname.setFont(new Font("Serif", Font.BOLD, 17));
			threadCommentPanel.add(replyname);
			JLabel replytext = new JLabel("Reply Text: "+reply.getText());
			replytext.setForeground(Color.black);
			replytext.setFont(new Font("Serif", Font.BOLD, 15));
			threadCommentPanel.add(replytext);
			
		}
		JScrollPane thereadScrollPanel = new JScrollPane(threadCommentPanel);
		JPanel centerPanel = new JPanel();
		GridLayout experimentLayout = new GridLayout(0,1);
		centerPanel.setLayout(experimentLayout);
		this.add(thereadScrollPanel);
		centerPanel.add(new JLabel("add a comment:"));
		centerPanel.add(coment);
		this.add(centerPanel);
		JButton sendComment = new JButton("send");
		sendComment.setName("send");
		sendComment.addActionListener(myActionListener);
		this.add(sendComment);
		
	}

	public void addComment() {
		String logedUser = this.myActionListener.getLogin().getLogedUser();


		String fName=forumName;
		String sName=subName;
		String tName=t.getCaption();
		String user=logedUser;	
		String text=coment.getText();
		try {
			fName = URLEncoder.encode(fName, "UTF-8");
			sName = URLEncoder.encode(sName, "UTF-8");
			tName = URLEncoder.encode(tName, "UTF-8");
			user = URLEncoder.encode(user, "UTF-8");
			text = URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SuperSimpleHttpUtils.getRequest(URL+SuperSimpleHttpUtils.ADD_NEW_REPLY+"&user="
				+user+"&forum="+fName+"&sub="+sName+"&thread="+tName+"&text="+text);
		
		
		this.removeAll();
		JLabel welcome = new JLabel("welcome to " + names[2] + " Thread");
		this.add(welcome);
		getT().addReply(logedUser, coment.getText());
		coment.setText("");
		initializeCenter(getNames(), getT());
		this.myActionListener.getMainFrame().changeRightSplitPanel(this);

	}
	
	

	public String[] getNames() {
		return names;
	}

	public static JTextArea getComent() {
		return coment;
	}

	public Thread getT() {
		return t;
	}

}
