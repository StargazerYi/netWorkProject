package ui;

import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;

public class myLabel extends JLabel {
	myLabel(String message) {
		setText(message);
		setHorizontalAlignment(JTextField.CENTER);
	}
}