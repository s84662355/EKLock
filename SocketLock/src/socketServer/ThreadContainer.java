package socketServer;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.RequestingUserName;

public class ThreadContainer {
	
	static private ThreadContainer container = null;
	static private int thread_num = 0;
	static private Map<String, ThreadSocket> thread_hash = null;
	 
	private ThreadContainer() {
		ThreadContainer.thread_hash = new HashMap<String, ThreadSocket>();
	}
	
	static public ThreadContainer getContainer() {
		if(ThreadContainer.container == null ) ThreadContainer.container = new ThreadContainer();
		return ThreadContainer.container;
	}
	
	public ThreadSocket createThread(Socket socket ,String thread_name) {
		return new ThreadSocket(socket , thread_name );
	}
	
	public void increase(String thrad_name ,ThreadSocket threadSocket ){
		ThreadContainer.thread_hash.put(thrad_name, threadSocket  );
		ThreadContainer.thread_num++;
	}
	
	public void  desc(String thrad_name ) {
		ThreadContainer.thread_hash.remove(thrad_name);
		ThreadContainer.thread_num--;
	}
	
	public int getThreadNum() {
		return  ThreadContainer.thread_num;
	}
	
	public Map<String, ThreadSocket>  getThreads() {
		return  ThreadContainer.thread_hash;
	}
	
 

}
