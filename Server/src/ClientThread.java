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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.*;

import net.sf.json.JSONObject;

public class ClientThread implements Runnable {
	public static String content = null;
    private Socket clientSocket = null;
    private BufferedReader br = null;
    private Writer out = null;
    private dbManage dbManager = dbManage.getInstance();
    private JSONObject test = null;
    public ClientThread(Socket s)
    {
         try
         {
             this.clientSocket = s;
             br = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
             out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"UTF-8"));
         }
         catch(IOException e)
         {
             e.printStackTrace();
         }
    }
     @Override
    public void run() {
        try
        {
            while(clientSocket.isClosed() == false && (content = readFromClient())!= null)
            {
                 System.out.println("来自客户端消息："+content);
                 
                 Map<String, Object> result = JsonAnalyzer.parse(content);
                 String type = (String)result.get("Type");
                 
                 switch(type) {
                 	case "0": {
                 		System.out.println("type："+type);
                 		String userid = (String)result.get("UserId");
                 		String username = Resource.getUserName(userid);
                 		Resource.UserOnLine.remove(userid);
                 		Resource.UserUDPIP.remove(userid);
                 		//clientSocket.close();
                 		test = JsonAnalyzer.type16(userid,username);
                 		Resource.threadPool.execute(new GroupThread(Resource.UserOnLine,test.toString()));
                 		break;
                 	}
                 	case "1": {
                 		System.out.println("type："+type);
                 		String userid = (String)result.get("UserId");
                 		String username = (String)result.get("UserName");
                 		String password = (String)result.get("Password");
                 		Resource.UserList.put(userid, username);
                 		boolean status = dbManager.UserRegister(userid,username,password);
                 		test = JsonAnalyzer.type8(userid,username,status);
                 		writeToClient(test.toString());
                 		break;
                 	}
                 	case "2": {
                 		System.out.println("type："+type);
                 		String userid = (String)result.get("UserId");
                 		String username = Resource.getUserName(userid);
                 		String password = (String)result.get("Password");
                 		boolean status = dbManager.UserLogin(userid, password);
                 		if(status == true) {
                 			test = JsonAnalyzer.type15(userid,username);
                 			Resource.threadPool.execute(new GroupThread(Resource.UserOnLine,test.toString()));
                     		Resource.UserOnLine.put(userid, clientSocket);
                 		}
                 		int num = Resource.UserOnLine.size();
                 		int i = 0;
                 		String[] ids = new String[num];
                 		String[] names = new String[num];
                 		Iterator<Entry<String, Socket>> iter = Resource.UserOnLine.entrySet().iterator();
                 		while(iter.hasNext()) {
                 			Entry<String, Socket> entry = iter.next();
                 			ids[i] = entry.getKey();
                 			names[i] = Resource.getUserName(ids[i]);
                 			i++;
                   	  	}
                 		test = JsonAnalyzer.type9(userid,username,status,ids,names);
                 		writeToClient(test.toString());
                 		break;
                 	}
                 	case "3": {
                 		System.out.println("type："+type);
                 		String userid = (String)result.get("UserId");
                 		String username = Resource.getUserName(userid);
                 		String Grouplist = (String)result.get("GroupList");
                 		System.out.println(Grouplist);
                 		String[] groupids = getGroupNumber(Grouplist);
                 		String[] groupnames = new String[groupids.length];
                 		String groupId = UUID.randomUUID().toString();
                 		HashMap<String,Socket> group = new HashMap<String,Socket>();
                 		int i = 0;
                 		for(String id : groupids) {
                 			group.put(id, Resource.UserOnLine.get(id));
                 			groupnames[i] = Resource.getUserName(id);
                 			i++;
                 		}
                 		Resource.groups.put(groupId, group);
                 		test = JsonAnalyzer.type10(groupId);
                 		writeToClient(test.toString());
                 		test = JsonAnalyzer.type11(userid,username,groupId,groupids,groupnames);
                 		Resource.threadPool.execute(new GroupThread(group,test.toString()));
                 		break;
                 	}
                 	case "4": {
                 		System.out.println("type："+type);
                 		String userid = (String)result.get("UserId");
                 		String username = Resource.getUserName(userid);
                 		String Groupid = (String)result.get("GroupId");
                 		String message = (String)result.get("Message");
                 		HashMap<String,Socket> group = Resource.groups.get(Groupid);
                 		test = JsonAnalyzer.type12(userid,username,Groupid,message);
                 		Resource.threadPool.execute(new GroupThread(group,test.toString()));
                 		break;
                 	}
                 	case "5": {
                 		System.out.println("type："+type);
                 		break;
                 	}
                 	case "6": {
                 		System.out.println("type："+type);
                 		break;
                 	}
                 	case "7": {
                 		System.out.println("type："+type);
                 		String userid = (String)result.get("UserId");
                 		String Groupid = (String)result.get("GroupId");
                 		HashMap<String,Socket> group = Resource.groups.get(Groupid);
                 		test = JsonAnalyzer.type17(userid,Groupid);
                 		Resource.threadPool.execute(new GroupThread(group,test.toString()));
                 		break;
                 	}
                 }
            }
        }catch(Exception e)
        {
             try
             {
                  clientSocket.close();
             }catch(IOException ex)
             {
                  System.out.println("close");
             }
        }
     }   
     private String readFromClient()
     {
          try
          {
               return br.readLine();
          }
          catch(IOException e)
          {
        	  System.out.println("close");
          }
        return null;
     }
     private void writeToClient(String response) {
    	 System.out.println("消息："+response);
    	 try{
    		 out.write(response+"\r\n");
  		   	 out.flush();
    	 }catch(Exception e) {
    		 
    	 }
     }
     private String[] getGroupNumber(String Grouplist) {
    	 List<String> list = new ArrayList<String>();
    	 int head = 0, last = 0;
    	 while(last+1 < Grouplist.length()-1) {
    		 head = Grouplist.indexOf('"',last+1);
    		 last = Grouplist.indexOf('"', head+1);
    		 list.add(Grouplist.substring(head+1, last));
    	 }
    	 String[] str = new String[list.size()];
    	 for(int i = 0; i < list.size(); i++) {
    		 str[i] = list.get(i);
    	 }
    	 return str;
     }
}