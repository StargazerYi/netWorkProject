import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.ArrayList;
import javax.swing.*;

import net.sf.json.*;
import data.Data;
import jsonAnalyzer.JsonAnalyzer;
import ui.*;

public class TCPReceiveThread implements Runnable {
	private Socket clientSocket;

	BufferedReader buffer = null;

	public TCPReceiveThread(Socket s) throws IOException {
		clientSocket = s;
		buffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
	}

	@Override
	public void run() {
		try {
			String content = null;

			while ((content = buffer.readLine()) != null) {
				System.out.println("From Server: " + content);
				Map<String, String> map = JsonAnalyzer.parse(content);

				String type = map.get("Type");

				if (type.equals("8")) {
					if (map.get("Status").equals("true")) {
						JOptionPane.showMessageDialog(null, "Success", "message", JOptionPane.INFORMATION_MESSAGE);
						Data.rPointer.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "The ID has been used", "message", JOptionPane.ERROR_MESSAGE);
					}

				} else if (type.equals("9")) {
					if (map.get("Status").equals("true")) {
						JOptionPane.showMessageDialog(null, "Success", "message", JOptionPane.INFORMATION_MESSAGE);
						Data.lPointer.dispose();

						Data.isLogin = true;

						Data.pointer.setState("UserName: " + map.get("UserName"));
						Data.userName = map.get("UserName");
                        Data.userID = map.get("UserId");

                        buildMap(map.get("UserIDList"), map.get("UserNameList"));
                        Data.pointer.update();

                        JSONObject hole = JsonAnalyzer.type6(Data.userID, Data.userName);
                        Data.udpSend.push(hole);
                        Data.udpGuide.push("Server");
					} else {
						JOptionPane.showMessageDialog(null, "Wrong Username or Password", "message", JOptionPane.ERROR_MESSAGE);
					}

				} else if (type.equals("10")) {
					//Nothing to do

				} else if (type.equals("11")) {
					String groupID = map.get("GroupId");
					GroupWindow groupWin = new GroupWindow(groupID);
					Data.groupMap.put(groupID, groupWin);
					String idList = map.get("GroupIDList");
					String nameList = map.get("GroupNameList");
					groupWin.build(idList, nameList);
					groupWin.said("Builder: " + map.get("UserName"));
					
				} else if (type.equals("12")) {
					String name = map.get("TalkName");
					String message = map.get("Message");
					String groupID = map.get("GroupId");

					if (Data.groupMap.containsKey(groupID)) {
						GroupWindow groupWin = Data.groupMap.get(groupID);
						groupWin.said(name + ": " + message);
					}
				} else if (type.equals("15")) {
					String name = map.get("UserName");
					String id = map.get("UserId");

					Data.onlineID.put(id, name);
					Data.onlineName.put(name, id);

					Data.pointer.update();

					System.out.println("List update: " + Data.onlineID.values());
				} else if (type.equals("16")) {
					String name = map.get("UserName");
					String id = map.get("UserId");

					Data.onlineID.remove(id);
					Data.onlineName.remove(name);

					Data.pointer.update();

					System.out.println("List update: " + Data.onlineID.values());
				} else if (type.equals("17")) {
					String id = map.get("UserId");
					String groupID = map.get("GroupId");
					if (Data.groupMap.containsKey(groupID)) {
						GroupWindow target = Data.groupMap.get(groupID);
						target.delete(id);
						target.said("(" + id + ") quit");
					}
				}
			}
		} catch (IOException e) {
			// Nothing to do
		}
	}

	private void buildMap(String idList, String nameList) {
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
			Data.onlineID.put(id.get(i), name.get(i));
			Data.onlineName.put(name.get(i), id.get(i));
		}
	}
}