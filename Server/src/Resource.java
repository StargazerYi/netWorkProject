import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.*;

import org.sqlite.*;

import java.sql.*;
public class Resource {
	public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
			20,		//corePoolSize
			40, 		//maximumPoolSize
			10,		//keepAliveTime
			TimeUnit.HOURS, 	//unit
			new ArrayBlockingQueue<Runnable>(20),	//workQueue
			new ThreadPoolExecutor.DiscardOldestPolicy()//
	);
	public static Set<Socket> socketSet = new HashSet<Socket>();
	public static HashMap<String,HashMap<String,Socket>> groups = new HashMap<String,HashMap<String,Socket>>();
	public static HashMap<String, String> UserList = new HashMap<String,String>();
	public static HashMap<String,Socket> UserOnLine = new HashMap<String,Socket>();
	public static HashMap<String,IPaddress> UserUDPIP = new HashMap<String,IPaddress>();
	public static int groupNum = 0;
	public static int socketNum = 0;
	public static String getUserName(String userid) {
		return (String)UserList.get(userid);
	}
}
