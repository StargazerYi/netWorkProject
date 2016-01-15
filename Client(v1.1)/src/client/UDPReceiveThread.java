import java.io.*;
import java.net.*;
import java.util.Map;
import javax.swing.*;

import net.sf.json.*;
import data.Data;
import jsonAnalyzer.JsonAnalyzer;
import ui.*;

public class UDPReceiveThread implements Runnable {
	private DatagramSocket clientSocket;

	private static byte[] receiveData = new byte[1024];

	public UDPReceiveThread(DatagramSocket s) throws IOException {
		clientSocket = s;
	}

	@Override
	public void run() {
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			while (true) {
				clientSocket.receive(receivePacket);
				String content = new String(receivePacket.getData()).trim();
				System.out.println("From Server: " + content);

				Map<String, String> map = JsonAnalyzer.parse(content);
				String type = map.get("Type");

				if (type.equals("13")) {
					String name = map.get("TalkName");
					String id = map.get("TalkId");
					String ip = map.get("TalkIp");
					String port = map.get("TalkPort");

					Data.onlineIP.put(id, ip);
					Data.onlinePort.put(ip, port);

					if (!Data.chatMap.containsKey(id)) {
						ChatWindow chatWin = new ChatWindow(name);
						Data.chatMap.put(id, chatWin);
					}
				} else if (type.equals("14")) {
					String name = map.get("TalkName");
					String id = map.get("TalkId");
					String ip = map.get("TalkIp");
					String port = map.get("TalkPort");

					Data.onlineIP.put(id, ip);
					Data.onlinePort.put(ip, port);

					if (!Data.chatMap.containsKey(id)) {
						ChatWindow chatWin = new ChatWindow(name);
						Data.chatMap.put(id, chatWin);
					}
				} else if (type.equals("18")) {
					String name = map.get("UserName");
					String id = map.get("UserId");
					String message = map.get("Message");

					if (Data.chatMap.containsKey(id)) {
						ChatWindow chatWin = Data.chatMap.get(id);
						chatWin.speak(name + ": " + message);
					}
				} else if (type.equals("19")) {
					String id = map.get("UserId");
					if (Data.chatMap.containsKey(id)) {
						ChatWindow chatWin = Data.chatMap.get(id);
						JOptionPane.showMessageDialog(null, "The other user has end the chat", "message", JOptionPane.ERROR_MESSAGE);
						chatWin.dispose();
					
						Data.chatMap.remove(id);
					}
				}
			}
		} catch (IOException e) {
			// Nothing to do
		}
	}
}