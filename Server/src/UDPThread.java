import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.*;

import net.sf.json.JSONObject;
public class UDPThread implements Runnable{
	private DatagramSocket UDPserver = null;
	private byte[] receiveData = new byte[1024];
	private byte[] sendData = new byte[1024];
	private String content = null;
	private String ip = null;
	private IPaddress ipaddress = null;
	private int port;
	private JSONObject test = null;
	public UDPThread(DatagramSocket UDPserver)
    {
        this.UDPserver = UDPserver;
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try{
				DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
				UDPserver.receive(receivePacket);
				content = new String(receivePacket.getData());
				content = content.trim();
				System.out.println("来自客户端消息："+content);         
                Map<String, Object> result = JsonAnalyzer.parse(content);
                String type = (String)result.get("Type");
                System.out.println(type);
                switch(type) {
             	case "5": {
             		String userid = (String)result.get("UserId");
             		String talkid = (String)result.get("TalkId");
             		if(Resource.UserOnLine.get(userid) != null && Resource.UserOnLine.get(talkid) != null) {
             			IPaddress useraddress = Resource.UserUDPIP.get(userid);
             			IPaddress talkaddress = Resource.UserUDPIP.get(talkid);
             			test = JsonAnalyzer.type13(talkid,userid,Resource.getUserName(userid),useraddress.getIP(),useraddress.getPort());
						UDPsend(test.toString(),talkaddress.ip,talkaddress.port);
             			test = JsonAnalyzer.type14(userid,talkid,Resource.getUserName(talkid),talkaddress.getIP(),talkaddress.getPort());
             			UDPsend(test.toString(),useraddress.ip,useraddress.port);
             		}
             		break;
             	}
             	case "6": {
             		String userid = (String)result.get("UserId");
             		String username = (String)result.get("UserName");
             		if(Resource.UserOnLine.get(userid) != null) {
             			ip = receivePacket.getAddress().toString().substring(1);
             			port = receivePacket.getPort();
             			System.out.println(ip);
             			System.out.println(port);
             			ipaddress = new IPaddress(ip,port);
             			Resource.UserUDPIP.put(userid, ipaddress);
             		}
             		break;
             	}
                }
			}catch(Exception e) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
			
		}
	}
	private void UDPsend(String message,String ip, int port) throws IOException {
		sendData = message.getBytes();
		InetAddress address = InetAddress.getByName(ip);
		DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,address,port);
		UDPserver.send(sendPacket );
	}
	
}
