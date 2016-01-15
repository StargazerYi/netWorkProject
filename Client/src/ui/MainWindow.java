package ui;

import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;

import net.sf.json.*;
import data.Data;
import jsonAnalyzer.JsonAnalyzer;

public class MainWindow extends JFrame {
	private myLabel message1 = new myLabel("System State");
	private myLabel message2 = new myLabel("Online user list");
	private myLabel message3 = new myLabel("Target(user / user,user ...)");
	private myLabel state = new myLabel("Haven't Login");

	JTextArea message = new JTextArea();
	myTextField target = new myTextField();

	private JButton login = new JButton("Login");
	private JButton register = new JButton("Register");
	private JButton chat = new JButton("Chat");
	private JButton group = new JButton("GroupChat");
	private JButton logout = new JButton("Logout");

	private JPanel box1 = new JPanel();
	private JPanel box2 = new JPanel();

	public MainWindow() {

		JScrollPane scroll = new JScrollPane(message);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        box1.add(message1);
		box1.add(state);
		box1.add(message2);
		box1.add(scroll);
		box1.add(message3);
		box1.add(target);

		box2.add(login);
		box2.add(register);
		box2.add(chat);
		box2.add(group);
		box2.add(logout);

		box1.setLayout(new GridLayout(3, 2, 10, 10));
		box2.setLayout(new GridLayout(1, 5, 20, 20));

		add(box1, BorderLayout.CENTER);
		add(box2, BorderLayout.SOUTH);

		setTitle("Main_Window");
		setSize(600, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Data.isLogin) {
					Data.lPointer = new LoginWindow();
			    }
			}
		});

		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Data.rPointer = new RegisterWindow();
			}
		});

		chat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Data.isLogin) {
					String talk = target.getText();
					if (Data.onlineID.containsValue(talk)) {
						JSONObject chatRequest = JsonAnalyzer.type5(Data.userID, Data.onlineName.get(talk));
						Data.udpSend.push(chatRequest);
						Data.udpGuide.push("Server");
						target.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "User doesn't exist or doesn't Online", "message", JOptionPane.ERROR_MESSAGE);
						target.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please login", "message", JOptionPane.ERROR_MESSAGE);
					target.setText("");
				}
			}
		});

		group.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Data.isLogin) {
					boolean correct = true;
					String temp = target.getText().trim();
					ArrayList<String> groupList = new ArrayList<String>();

					int i = 0, index = 0;
					while (true) {
						index = temp.indexOf(',', i);
						if (index < 0) {
							String buf = temp.substring(i, temp.length());
							if (Data.onlineName.containsKey(buf)) {
								groupList.add(Data.onlineName.get(buf));
							} else {
								correct = false;
							}
							break;
						}

						String buffer = temp.substring(i, index);
						if (Data.onlineName.containsKey(buffer)) {
						    groupList.add(Data.onlineName.get(buffer));
						    i = index + 1;
						} else {
							correct = false;
						}
					}
					if (correct) {
						JSONObject groupChat = JsonAnalyzer.type3(Data.userID, groupList);
						Data.tcpSend.push(groupChat);
					} else {
						JOptionPane.showMessageDialog(null, "User doesn't exist or doesn't Online", "message", JOptionPane.ERROR_MESSAGE);
					}
					target.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Please login", "message", JOptionPane.ERROR_MESSAGE);
					target.setText("");
				}
			}
		});

		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Data.isLogin) {
					JSONObject quit = JsonAnalyzer.type0(Data.userID);
					Data.tcpSend.push(quit);
					setState("Haven't Login");
					show("");
					Data.clean();
				}
			}
		});
	}

	public void setState(String s) {
		state.setText(s);
	}
	public void show(String s) {
		message.setText(s);
	}

	public void update() {
		String temp = "";
		for (Map.Entry<String, String> entry : Data.onlineID.entrySet()) {
			temp = temp + " " + entry.getValue();
		}
		message.setText(temp);
	}
}