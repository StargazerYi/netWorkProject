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

public class LoginWindow extends JFrame {
	private myLabel message1 = new myLabel("ID:");
	private myLabel message2 = new myLabel("Password:");

	myTextField user = new myTextField();
	myTextField password = new myTextField();

	private JButton login = new JButton("Login");

	private JPanel box1 = new JPanel();
	private JPanel box2 = new JPanel();

	public LoginWindow() {

		box1.add(message1);
		box1.add(user);
		box1.add(message2);
		box1.add(password);
		box2.add(login);

		box1.setLayout(new GridLayout(2, 2, 10, 10));

		add(box1, BorderLayout.CENTER);
		add(box2, BorderLayout.SOUTH);

		setTitle("Login_Window");
		setSize(450, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Data.isLogin) {
				    JSONObject text = JsonAnalyzer.type2(user.getText(), password.getText());
				    Data.tcpSend.push(text);
				} else {
					JOptionPane.showMessageDialog(null, "Already Login", "message", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}