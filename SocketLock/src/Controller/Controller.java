package Controller;

import java.util.Map;

 
import socketServer.RunException;

public abstract class Controller {
	
	protected String  threadName;
	
	abstract public  Map<String, String>  DoSomeThing(Map<String, String> hasMap) throws RunException ;
	
	public Controller setThreadName(String  threadName){
		this.threadName = threadName;
		return this;
	}

}
