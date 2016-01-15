package ui;

import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

import net.sf.json.*;
import data.Data;
import jsonAnalyzer.JsonAnalyzer;

public class ChatWindow extends JFrame {
	private myLabel message1 = new myLabel("Message:");
	private myLabel message2 = new myLabel("Statement:");

	JTextArea message = new JTextArea();
	myTextField said = new myTextField();

	private JButton send = new JButton("Send");
	private JButton close = new JButton("Close");

	private JPanel box1 = new JPanel();
	private JPanel box2 = new JPanel();

	private String name;
	private boolean online;

	public ChatWindow(String s) {
		name = s;
		online = true;

		JScrollPane scroll = new JScrollPane(message);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		box1.add(message1);
		box1.add(scroll);
		box1.add(message2);
		box1.add(said);

		box2.add(send);
		box2.add(close);

		box1.setLayout(new GridLayout(2, 2, 10, 10));

		add(box1, BorderLayout.CENTER);
		add(box2, BorderLayout.SOUTH);

		setTitle(s);
		setSize(450, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		JSONObject chat = JsonAnalyzer.type18(Data.userID, Data.userName, "Ready");
		Data.udpSend.push(chat);
		Data.udpGuide.push(name);
		speak(Data.userName + ": Ready");

		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (online) {
					String text = said.getText();
					if (text.length() < 1000) {
					    JSONObject chat = JsonAnalyzer.type18(Data.userID, Data.userName, text);
					    Data.udpSend.push(chat);
					    Data.udpGuide.push(name);
					    speak(Data.userName + ": " + said.getText());
					    said.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "Message is too long", "message", JOptionPane.ERROR_MESSAGE);
						said.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Connection closed", "message", JOptionPane.ERROR_MESSAGE);
					said.setText("");
				}
			}
		});

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (online) {
					JSONObject quit = JsonAnalyzer.type19(Data.userID, Data.userName);
			        Data.udpSend.push(quit);
			        Data.udpGuide.push(name);
			        if (Data.chatMap.containsKey(Data.onlineName.get(name))) {
			        	Data.chatMap.remove(Data.onlineName.get(name));
			        }
			        online = false;

			        dispose();
			    } else {
			    	JOptionPane.showMessageDialog(null, "Connection closed", "message", JOptionPane.ERROR_MESSAGE);
			    }
			}
		});
	}

	public void speak(String s) {
		message.setText(message.getText() + "\n" + s);
		message.setCaretPosition(message.getText().length());
	}
}