package ui;

import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import net.sf.json.*;
import data.Data;
import jsonAnalyzer.JsonAnalyzer;

public class GroupWindow extends JFrame {
	private myLabel message1 = new myLabel("Members:");
	private myLabel message2 = new myLabel("Message:");
	private myLabel message3 = new myLabel("Statement:");

	JTextArea members = new JTextArea();
	JTextArea message = new JTextArea();
	myTextField said = new myTextField();

	private JButton send = new JButton("Send");
	private JButton close = new JButton("Quit");

	private JPanel box1 = new JPanel();
	private JPanel box2 = new JPanel();

	private boolean online;
	private String groupID;
	private Map<String, String> list = new HashMap<String, String>();

	public GroupWindow(String s) {
		groupID = s;
		online = true;

		JScrollPane scroll1 = new JScrollPane(members);
		scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JScrollPane scroll2 = new JScrollPane(message);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		box1.add(message1);
		box1.add(members);
		box1.add(message2);
		box1.add(scroll2);
		box1.add(message3);
		box1.add(said);

		box2.add(send);
		box2.add(close);

		box1.setLayout(new GridLayout(3, 2, 10, 10));

		add(box1, BorderLayout.CENTER);
		add(box2, BorderLayout.SOUTH);

		setTitle(s);
		setSize(500,266);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (online) {
				    JSONObject groupChat = JsonAnalyzer.type4(Data.userID, groupID, said.getText());
			        Data.tcpSend.push(groupChat);
			        said.setText("");
			    } else {
			    	JOptionPane.showMessageDialog(null, "Connection closed", "message", JOptionPane.ERROR_MESSAGE);
			    	said.setText("");
			    }
			}
		});

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (online) {
				    JSONObject quit = JsonAnalyzer.type7(Data.userID, groupID);
				    Data.tcpSend.push(quit);
				    if (Data.groupMap.containsKey(groupID)) {
				    	Data.groupMap.remove(groupID);
				    }
				    online = false;

				    dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Connection closed", "message", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public void said(String s) {
		message.setText(message.getText() + "\n" + s);
		message.setCaretPosition(message.getText().length());
	}

	private void showMember() {
		String temp = "";
		for (Map.Entry<String, String> entry : list.entrySet()) {
			temp = temp + " " + entry.getValue();
		}
		members.setText(temp);
	}

	public void build(String idList, String nameList) {
		ArrayList<String> id = new ArrayList<String>();
		ArrayList<String> name = new ArrayList<String>();

		int i, index;
		i = 2;
		index = 0;
		while (true) {
			index = idList.indexOf(',', i);
			if (index < 0) break;

			String temp = idList.substring(i, index-1);
			id.add(temp);
			i = index+2;
		}
		id.add(idList.substring(i, idList.length()-2));

		i = 2;
		index = 0;
		while (true) {
			index = nameList.indexOf(',', i);
			if (index < 0) break;

			String temp = nameList.substring(i, index-1);
			name.add(temp);
			i = index+2;
		}
		name.add(nameList.substring(i, nameList.length()-2));

		for (i = 0; i < id.size(); i++) {
			list.put(id.get(i), name.get(i));
		}

		showMember();
	}

	public void delete(String key) {
		list.remove(key);
		showMember();
	}
}