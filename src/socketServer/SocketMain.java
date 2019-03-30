package socketServer;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
import com.sun.net.httpserver.spi.*;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import WebServer.ApiHandler;
import WebServer.StaticHandler;
import util.LogUtil;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.dtools.ini.*;

import org.iq80.leveldb.*;
import static org.fusesource.leveldbjni.JniDBFactory.*;
import java.io.*;
 

public class SocketMain {
	public static void main(String[] args) throws IOException {
		
		
		 /*
		TreeSet<Integer> sortSet = new TreeSet<Integer>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1>o2) return 1;
				if(o1<o2) return -1;
				return 0;
			 
			}
		});
        sortSet.add(12111);
        sortSet.add(111122231);
        sortSet.add(11110000);
        sortSet.add(3333333);
        sortSet.add(6546575);
        sortSet.add(11);
        sortSet.add(11);
        System.out.print(sortSet.add(11));
        System.out.println(sortSet.toString());
       */
	 
 
		
		 IniFile iniFile= IniParser.getParser().setIni("base",args[0] ).get("base");
		 IniSection iniSection=iniFile.getSection("LockServer");
		 int LockServerPort = Integer.parseInt(iniSection.getItem("port").getValue()) ;
		 int LockServerThread_Num  = Integer.parseInt(iniSection.getItem("thread_num").getValue()) ;
		 
 
		 Config.LockServer_port = Integer.parseInt(iniSection.getItem("port").getValue()) ;
		 Config.LockServer_thread_num = Integer.parseInt(iniSection.getItem("thread_num").getValue()) ;
		 Config.LockWeb_port = Integer.parseInt( IniParser.getParser().get("base").getSection("LockWeb").getItem("port").getValue() ) ;
 		 Config.Auth_secretkey = IniParser.getParser().get("base").getSection("Auth").getItem("secretkey").getValue() ;
		
		 
		 
	     HttpServerProvider provider = HttpServerProvider.provider();  
	     HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(Config.LockWeb_port), 100);
	   
	     httpserver.createContext("/api",new ApiHandler());
	     httpserver.createContext("/static",new StaticHandler());
	     httpserver.setExecutor(null);  
	     httpserver.start();
	    
	     LogUtil.getLogUtil().start();
	     
	     
	     new Thread(new Runnable() {
			
			@Override
			public void run() {

 
				try {
					
				     DBComparator comparator = new DBComparator(){
				    	    public int compare(byte[] key1, byte[] key2) {
				    	    	String skey1 = new String(key1);
				    	    	String skey2 = new String(key2);
				    	    	Long lkey1 = Long.valueOf(new String(key1));
				    	    	Long lkey2 = Long.valueOf(new String(key2));
				    	    	
				    	    	if(lkey1>lkey2) return 1;
				    	    	if(lkey1<lkey2) return -1;
				    	    	return 0;
				    	       // return new String(key1).compareTo(new String(key2));
				    	    }
				    	    public String name() {
				    	        return "leveldb.mysssssss";
				    	    }
				    	    public byte[] findShortestSeparator(byte[] start, byte[] limit) {
				    	        return start;
				    	    }
				    	    public byte[] findShortSuccessor(byte[] key) {
				    	        return key;
				    	    }
				    	};
				     
				     Options options = new Options();
				     options.createIfMissing(true);
				     options.comparator(comparator);
				     DB db = factory.open(new File("example"), options);
				     
				     db.put("11111111".getBytes(), "g4h5h5".getBytes());
				     db.put("112211".getBytes(), "g4h5h5".getBytes());
				     db.put("112211666".getBytes(), "g4h5h5".getBytes());
				     db.put("111".getBytes(), "g4h5h5".getBytes());
				     db.put("34567111".getBytes(), "g4h5h5".getBytes());
				     
				     DBIterator iterator = db.iterator();
				     /*
			    	   for(iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
				    	     String key = asString(iterator.peekNext().getKey());
				    	     String value = asString(iterator.peekNext().getValue());
				    	     System.out.println(key+" = "+value);
				       }
				     */  
			    	   for(iterator.seek("33333".getBytes()); iterator.hasNext(); iterator.next()) {
				    	     String key = asString(iterator.peekNext().getKey());
				    	     String value = asString(iterator.peekNext().getValue());
				    	     System.out.println(key+" = "+value);
				       }
				     
			    	   /*
			    	   iterator.seekToLast();
			    	   if(iterator.hasNext()) {
				    	   String kkkString = asString(iterator.peekNext().getKey());	
				    	   System.out.print(kkkString);
			    		   
			    	   }
			    	   */
 
			    	   iterator.close();
			    	   db.close();
				    	   
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
			   	
			}
		}).start();
	     
	     
		 ServerSocket serverSocket = new ServerSocket(Config.LockServer_port);
		 ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Config.LockServer_thread_num );
		 System.out.print("服务器开启");
		 while(true){
			 Socket socket = serverSocket.accept();
			 socket.setKeepAlive(true);
			 ThreadSocket th = ThreadContainer.getContainer().createThread(socket,System.currentTimeMillis()+"")  ;
			 fixedThreadPool.execute(th);		 
		 }
	}
}
 
 
