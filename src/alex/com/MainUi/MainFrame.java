package alex.com.MainUi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.forum.client.util.MySession;
import com.forum.client.util.Sessions;

public class MainFrame  extends JFrame{

	
	private static final long serialVersionUID = 5102551503120265405L;
	private ArrayList<JButton> buttonsLIst = new ArrayList<JButton>();
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JSplitPane splitCenterPanel;
	private MyActionListener buttonActionListner;
	private Sessions sessions;
	private String session;

	public MainFrame(){
		super("Forum");
	}
	
	public void CreateAWindow(){		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			initFrame();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		this.setSize(1100, 1000);
		this.setVisible(true);
		
	}
	
	private  void initFrame() throws IOException {
		JPanel upperPanel = new JPanel();
		upperPanel.setBackground(new Color(0x4DA7A7));
		BufferedImage myPicture = ImageIO.read(new File("title1.png"));
		JLabel pinapleLogo = new JLabel(new ImageIcon(myPicture));
		upperPanel.add(pinapleLogo);
		this.add(upperPanel,BorderLayout.NORTH);
		buildCenterPanel();
		
	}

	private void buildCenterPanel() {
		BuildRightPanel();
		BuildLeftPanel();

		splitCenterPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftPanel, rightPanel);
		this.add(splitCenterPanel, BorderLayout.CENTER);

	}
	


	private  void BuildRightPanel() {
	    rightPanel = new JPanel();
		Font font = new Font("SansSerif", Font.BOLD, 30);
		JLabel welcome = new JLabel("Weclcome to Pineapple");
		welcome.setFont(font);
		rightPanel.add(welcome);
	}

	public  void BuildLeftPanel() {
		leftPanel = new JPanel();
		leftPanel.setName("Menu");
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JButton  registerButton = new JButton("Register");
		registerButton.setName("Register");
		JButton  Login = new JButton("Log in");
		Login.setName("Login");
		JButton  LogOut = new JButton("Log Out");
		LogOut.setName("LogOut");
		JButton  home = new JButton("Home");
		home.setName("Home");
		buttonsLIst.add(registerButton);
		buttonsLIst.add(Login);
		buttonsLIst.add(LogOut);
		buttonsLIst.add(home);
		Font font = new Font("SansSerif", Font.BOLD, 15);
		JLabel menu = new JLabel("Menu");
		menu.setFont(font);
		leftPanel.add(menu,BorderLayout.CENTER);
		this.buttonActionListner = new MyActionListener(this.rightPanel ,this.splitCenterPanel ,this ,this.leftPanel);
		for (JButton button : buttonsLIst) {
			button.setAlignmentX(Component.LEFT_ALIGNMENT);
			button.addActionListener(buttonActionListner);	
			leftPanel.add(button);
		}
		
		
	}



	public String getCurrentSession(){
		return this.session;
	}
	
	public void setCurrentSession(String session){
		this.session = session;
	}
	
	public ArrayList<MySession> getSessionList(){
		return Sessions.getInstance().getSessions();
	}
	
	public void addSession(String session){
		this.sessions.getSessions().add(new MySession(session));
	}

	public void changeRightSplitPanel(JPanel jPanel) {
		this.splitCenterPanel.setRightComponent(jPanel);
		
	}
	
	public void changeRightSplitPanelScroll(JScrollPane jPanel) {
		this.splitCenterPanel.setRightComponent(jPanel);
		
	}
	
	public void ChangeLeftSplitPanel(JPanel jPanel) {
		this.splitCenterPanel.setLeftComponent(jPanel);
		
	}
	
	public JPanel getLeftSplitPanel(){
		return (JPanel) this.splitCenterPanel.getLeftComponent();
		
	}

	public JSplitPane getSplitCenterPanel() {
		return splitCenterPanel;
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}
	
	



}
