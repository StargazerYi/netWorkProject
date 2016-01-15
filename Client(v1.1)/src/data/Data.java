package data;

import java.util.*;
import net.sf.json.*;

import ui.*;

public class Data {
	public static boolean isLogin = false;

	public static String userID;
	public static String userName;
	public static String serverIP = "172.18.41.32";

	public static Stack<JSONObject> tcpSend = new Stack<JSONObject>();
	public static Stack<JSONObject> udpSend = new Stack<JSONObject>();
	public static Stack<String> udpGuide = new Stack<String>();

	public static MainWindow pointer;
	public static RegisterWindow rPointer = null;
	public static LoginWindow lPointer = null;

	public static Map<String, String> onlineID = new HashMap<String, String>();
	public static Map<String, String> onlineName = new HashMap<String, String>();
	public static Map<String, String> onlineIP = new HashMap<String, String>();
	public static Map<String, String> onlinePort = new HashMap<String, String>();

	public static Map<String, ChatWindow> chatMap = new HashMap<String, ChatWindow>();
	public static Map<String, GroupWindow> groupMap = new HashMap<String, GroupWindow>();

	public static void clean() {
		isLogin = false;
		userID = null;
		userName = null;

		onlineID.clear();
		onlineName.clear();
		onlineIP.clear();
		onlinePort.clear();

		chatMap.clear();
		groupMap.clear();
	}
}