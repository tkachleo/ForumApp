package alex.com.MainUi;

import java.awt.Color;
import java.awt.Font;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.forum.client.util.Emailer;
import com.forum.client.util.MySession;
import com.forum.client.util.SuperSimpleHttpUtils;
import com.forum.helper.User;
import com.forum.managers.LogManager;
import com.google.gson.Gson;

public class RgistartionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JLabel intoLabel, nameL, emailL, createPassL, confirmPassL, qustionL,
			anssL, phoneL;
	static JTextField nameText, emailText, qustionText, answerText, phoneText;
	JButton submitButton, clearButton;
	JPasswordField passText, confirmPassText;
	private MyActionListener myActionListener;
	private final String URL = "http://tkach.herokuapp.com/request?MSG_NUM=";

	public RgistartionPanel(MyActionListener myActionListener) {
		this.myActionListener = myActionListener;
		this.setLayout(null);
		intoLabel = new JLabel("Registration Form in Windows Form:");
		intoLabel.setForeground(Color.blue);
		intoLabel.setFont(new Font("Serif", Font.BOLD, 20));
		nameL = new JLabel("Name:");
		emailL = new JLabel("Email-ID:");
		createPassL = new JLabel("Create Passowrd:");
		confirmPassL = new JLabel("Confirm Password:");
		qustionL = new JLabel("Qustion:");
		anssL = new JLabel("nswer:");
		phoneL = new JLabel("Phone No:");
		nameText = new JTextField();
		emailText = new JTextField();
		passText = new JPasswordField();
		confirmPassText = new JPasswordField();
		qustionText = new JTextField();
		answerText = new JTextField();
		phoneText = new JTextField();
		submitButton = new JButton("Submit");
		clearButton = new JButton("Clear");
	}

	public void initFrame() {
		submitButton.setName("SubmitReg");
		clearButton.setName("Clear");
		submitButton.addActionListener(myActionListener);
		clearButton.addActionListener(myActionListener);
		intoLabel.setBounds(100, 30, 400, 30);
		nameL.setBounds(80, 70, 200, 30);
		emailL.setBounds(80, 110, 200, 30);
		createPassL.setBounds(80, 150, 200, 30);
		confirmPassL.setBounds(80, 190, 200, 30);
		qustionL.setBounds(80, 230, 200, 30);
		anssL.setBounds(80, 270, 200, 30);
		phoneL.setBounds(80, 310, 200, 30);
		nameText.setBounds(300, 70, 200, 30);
		emailText.setBounds(300, 110, 200, 30);
		passText.setBounds(300, 150, 200, 30);
		confirmPassText.setBounds(300, 190, 200, 30);
		qustionText.setBounds(300, 230, 200, 30);
		answerText.setBounds(300, 270, 200, 30);
		phoneText.setBounds(300, 310, 200, 30);
		submitButton.setBounds(50, 350, 100, 30);
		clearButton.setBounds(170, 350, 100, 30);
		this.add(intoLabel);
		this.add(nameL);
		this.add(nameText);
		this.add(emailL);
		this.add(emailText);
		this.add(createPassL);
		this.add(passText);
		this.add(confirmPassL);
		this.add(confirmPassText);
		this.add(qustionL);
		this.add(qustionText);
		this.add(anssL);
		this.add(answerText);
		this.add(phoneL);
		this.add(phoneText);
		this.add(submitButton);
		this.add(clearButton);
	}

	public void clear() {
		nameText.setText("");
		emailText.setText("");
		passText.setText("");
		confirmPassText.setText("");
		qustionText.setText("");
		answerText.setText("");
		phoneText.setText("");

	}

	public void submit() {
		ArrayList<MySession> sessions = myActionListener.getMainFrame()
				.getSessionList();
		for (int i = 0; i < sessions.size(); i++) {

			if (sessions
					.get(i)
					.getSession()
					.equals(myActionListener.getMainFrame().getCurrentSession())) {
				if (SuperSimpleHttpUtils.getRequest(
						URL + SuperSimpleHttpUtils.IS_USER_EXISTS + "&user="
								+ sessions.get(i).getUsername()).equals("true")) {
					JPanel tempPanel = new JPanel();
					JLabel userTacken = new JLabel("Username is already taken!");
					tempPanel.add(userTacken);
					myActionListener.getMainFrame().ChangeSplitPanel(tempPanel);

				} else {
					JPanel tempPanel = new JPanel();
					JLabel userTacken = new JLabel("Registered User: "
							+ nameText.getText());
					tempPanel.add(userTacken);
					myActionListener.getMainFrame().ChangeSplitPanel(tempPanel);
					Emailer emailer = new Emailer();
					emailer.sendEmail(
							nameText.getText(),
							"Verification Mail",
							"Hello and Welcome to our forum - "
									+ nameText.getText()
									+ ".  Please click this link to verify yourself : tkach.herokuapp.com/verification?username="
									+ nameText.getText());
					LogManager.print("Activated send mail function!");
					// String question = "";
					// String answer = "";
					// try {
					// question = URLEncoder.encode(sQuestion.getValue(),
					// "UTF-8");
					// answer = URLEncoder.encode(sAnswer.getValue(), "UTF-8");
					// } catch (UnsupportedEncodingException e) {
					// e.printStackTrace();
					// }

					SuperSimpleHttpUtils.getRequest(URL
							+ SuperSimpleHttpUtils.ADD_NEW_USER + "&user="
							+ nameText.getText() + "&email="
							+ emailText.getText() + "&pass="
							+ new String(passText.getPassword()) + "&question="
							+ qustionText.getText() + "&answer="
							+ answerText.getText());
					Gson gson = new Gson();
					String url = SuperSimpleHttpUtils.getRequest(URL
							+ SuperSimpleHttpUtils.GET_USER + "&name="
							+ nameText.getText());
					LogManager.print("xxxxxxxxxxxxxxxxxxxx" + url);
					User user = gson.fromJson(url, User.class);
					System.out.println(user.getName());
				}
			}
		}

	}

}
