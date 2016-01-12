import java.util.*;
import java.sql.*;
public class dbManage {
	private Connection db;
	private Statement stmt = null;
	private String sql = null;
	private ResultSet rs = null;
	private static dbManage dbManager = null;
	public dbManage() {
		try{
			Class.forName("org.sqlite.JDBC");
			db = DriverManager.getConnection("jdbc:sqlite:test.db");
			db.setAutoCommit(false);
			stmt = db.createStatement();
			System.out.println("Opened database successfully");
		}catch(Exception e) {
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
	}
	public static dbManage getInstance() {  
	    if (dbManager == null) {    
	    	dbManager = new dbManage();  
	    }    
	    return dbManager;  
	}  

	public boolean IsTableUserExit() {
		try{
		    /*sql = "DROP TABLE USER;";
		    stmt.executeUpdate(sql);
		    db.commit();*/
		    
			stmt = db.createStatement();
			rs = stmt.executeQuery("SELECT count(*) as c FROM sqlite_master WHERE type='table' AND name='USER'");
			if(rs.getInt("c") == 1) {
				System.out.println("Table User is ready");
				return true;
			}else{
				return false;
			}
		}catch(Exception e) {
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		return false;
	}
	public boolean CreateTableUser() {
		try{
			stmt = db.createStatement();
			sql = "CREATE TABLE USER " +
                 "(ID TEXT PRIMARY KEY NOT NULL," +
                 " NAME TEXT NOT NULL, " + 
                 " PASSWORD TEXT NOT NULL)";
			stmt.executeUpdate(sql);
			System.out.println("Table User is created!");
			return true;
		}catch(Exception e) {
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    System.exit(0);
		}
		return false;
	}
	public ResultSet findUserAll() {
		try{
			stmt = db.createStatement();
			rs = stmt.executeQuery( "SELECT id, name FROM USER;" );
			return rs;
		}catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return null;
		}
	}
	public ResultSet findUser(String id) {
		try{
			stmt = db.createStatement();
			rs = stmt.executeQuery( "SELECT id FROM USER Where id = '"+id+"';" );
			System.out.println("Insert OK");
			return rs;
		}catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		    return null;
		}
	}
	public boolean UserLogin(String id, String password) {
		try{
			stmt = db.createStatement();
			rs = stmt.executeQuery("SELECT count(*) as c FROM USER WHERE id='"+id+"'AND password='"+password+"';");
			if(rs.getInt("c") == 1) {
				return true;
			}else{
				return false;
			}
		}catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return false;
		}
	}
	public boolean UserRegister(String id, String name,String password){
		try{
			sql = "INSERT INTO USER (ID,NAME,PASSWORD) " +
	                  "VALUES ('"+id+"', '"+name+"', '"+password+"' );"; 
		    stmt.executeUpdate(sql);
		    db.commit();;	
			return true;
		}catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return false;
		}
	}
}
