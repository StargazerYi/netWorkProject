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

public class RegisterWindow extends JFrame {
	private myLabel message1 = new myLabel("ID:");
	private myLabel message2 = new myLabel("Name");
	private myLabel message3 = new myLabel("Password:");

	myTextField id = new myTextField();
	myTextField name = new myTextField();
	myTextField password = new myTextField();

	private JButton register = new JButton("Register");

	private JPanel box1 = new JPanel();
	private JPanel box2 = new JPanel();

	public RegisterWindow() {

		box1.add(message1);
		box1.add(id);
		box1.add(message2);
		box1.add(name);
		box1.add(message3);
		box1.add(password);

		box2.add(register);

		box1.setLayout(new GridLayout(3, 2, 10, 10));

		add(box1, BorderLayout.CENTER);
		add(box2, BorderLayout.SOUTH);

		setTitle("Register_Window");
		setSize(450, 180);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSONObject text = JsonAnalyzer.type1(id.getText(), name.getText(), password.getText());
				Data.tcpSend.push(text);
			}
		});
	}
}