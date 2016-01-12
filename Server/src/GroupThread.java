import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;

import net.sf.json.JSONObject;

public class GroupThread implements Runnable {
     private HashMap<String,Socket> groupList= null;
     private Writer out=null;
     private String message = null;
     JSONObject test = null;
     public GroupThread (HashMap<String,Socket> groupList,String message)
     {
               this.groupList = groupList;
               this.message = message;
     }
     @Override
     public synchronized void run() {
          try
          {
        	  Iterator<Entry<String, Socket>> iter = groupList.entrySet().iterator();
        	  while(iter.hasNext()) {
        		 //System.out.println("ÈºÏûÏ¢£º"+message);
        		 Map.Entry<String, Socket> entry = iter.next();
        		 out = new BufferedWriter(new OutputStreamWriter(entry.getValue().getOutputStream(),"UTF-8"));
        		 out.write(message+"\r\n");
        		 out.flush();
        	  	 }
          }
          catch(IOException e)
          {
               e.printStackTrace();
          }
     }
}