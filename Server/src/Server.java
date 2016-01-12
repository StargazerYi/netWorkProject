import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.sql.*;

public class Server {	
	private static boolean flag = false;
	public static void main(String[] args){
		startServ();
		System.out.println("Server listening......");
		int port = 5556;
		try {
			DatagramSocket UDPserver = new DatagramSocket(port);
			Resource.threadPool.execute(new UDPThread(UDPserver));
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		port = 5555;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Socket clientSocket = null;
		while(flag){
			//��ÿͻ�������
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Resource.socketSet.add(clientSocket);
			Resource.threadPool.execute(new ClientThread(clientSocket));
			Resource.socketNum++;	
		}
	}
	public static void startServ(){
	    /*Statement stmt = null;
	    String sql = null;
	    ResultSet rs = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      db = DriverManager.getConnection("jdbc:sqlite:test.db");
	      db.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	      
	      stmt = db.createStatement();
	      rs = stmt.executeQuery("SELECT count(*) as c FROM sqlite_master WHERE type='table' AND name='USER'");
	      if(rs.getInt("c") == 1) {
	    	  System.out.println("Table User is ready");
	      }else{      
	      sql = "CREATE TABLE USER " +
                  "(ID INT PRIMARY KEY     NOT NULL," +
                  " NAME           TEXT    NOT NULL, " + 
                  " PASSWORD       TEXT)";
	      stmt.executeUpdate(sql);
	      
	      System.out.println("Table USER is created");
	      
	      sql = "INSERT INTO USER (ID,NAME,PASSWORD) " +
                  "VALUES (1, 'Paul', '12345678' );"; 
	      stmt.executeUpdate(sql);
	      db.commit();
	      }
	            
	      rs = stmt.executeQuery( "SELECT * FROM USER;" );
	      while ( rs.next() ) {
	    	  int id = rs.getInt("id");
	    	  String  name = rs.getString("name");
	    	  String  password = rs.getString("password");
	    	  System.out.println( "ID = " + id );
	    	  System.out.println( "NAME = " + name );
	    	  System.out.println( "PASSWORD = " + password );
	    	  System.out.println();
	      }
	      rs.close();
	      
	      /*sql = "DROP TABLE User;";
	      stmt.executeUpdate(sql);
	      db.commit();
	          
	      stmt.close();
	      db.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }*/
		flag = true;
		dbManage dbManager = dbManage.getInstance();
		if(!dbManager.IsTableUserExit()) {
			dbManager.CreateTableUser();
		}
		//dbManager.UserRegister("1","wjq","123456");
		//dbManager.UserRegister("2","wjq","123456");
		ResultSet rs = dbManager.findUserAll();
		try {
			while (rs.next()) {
			     String id = rs.getString("id");
			     String  name = rs.getString("name");
			     Resource.UserList.put(id, name);
			     System.out.println( "ID = " + id );
			     System.out.println( "NAME = " + name );
			     System.out.println();
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*if(dbManager.UserLogin(1, "123456")) {
			System.out.println( "ID = " + 1+" login" );
		}*/
	}
	public static void stopServ(){
		flag = false;
	}
}
