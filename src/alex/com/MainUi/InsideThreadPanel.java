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
	
	public InsideThreadPanel(MyActionListener myActionListener) {
		this.myActionListener = myActionListener;
	}

	public void init(JButton button, ThreadPanel threadPanel) {
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.names= button.getName().split("\\.");
		for (com.forum.helper.Thread t : threadPanel.getThreadsArray()) {
			if(names[2].equals(t.getText())){
				JLabel welcome  = new JLabel("welcome to "+names[2]+" Thread");
				this.add(welcome);
				initializeCenter(names , t);
			}
		}
		
		this.myActionListener.getMainFrame().changeRightSplitPanel(this);
		
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
		for (Reply reply : t.getReplies()) {
			JLabel replyname = new JLabel("Reply From: "+reply.getUser());
			replyname.setForeground(Color.black);
			replyname.setFont(new Font("Serif", Font.BOLD, 17));
			this.add(replyname);
			JLabel replytext = new JLabel("Reply Text: "+reply.getText());
			replytext.setForeground(Color.black);
			replytext.setFont(new Font("Serif", Font.BOLD, 15));
			this.add(replytext);
			
		}
	
		JPanel centerPanel = new JPanel();
		GridLayout experimentLayout = new GridLayout(0,1);
		centerPanel.setLayout(experimentLayout);
		centerPanel.add(new JLabel("add a comment:"));
		centerPanel.add(coment);
		this.add(centerPanel);
		JButton sendComment = new JButton("send");
		sendComment.setName("send");
		sendComment.addActionListener(myActionListener);
		this.add(sendComment);
		
	}

	public void addComment() {
		String text = "";
//		try {
//			text = URLEncoder.encode(coment.getText(), "UTF-8");
//			String s = SuperSimpleHttpUtils.getRequest(URL+SuperSimpleHttpUtils.ADD_NEW_THREAD + "&user="+"other" + "&forum="+getNames()[0]+
//					"&sub="+getNames()[1]+"&forum="+getNames()[0]+"&caption="+"what" +"&text="+text);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.removeAll();
		JLabel welcome  = new JLabel("welcome to "+names[2]+" Thread");
		this.add(welcome);
		getT().addReply("me", coment.getText());
		coment.setText("");
		initializeCenter(getNames(),getT());
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
