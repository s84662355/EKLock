package Controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.alibaba.fastjson.*;

import socketServer.ErrStatus;
import socketServer.ResponseStatus;
import socketServer.RunException;

public class ControllerFactory {
	
	
	static public Controller getController(String className) throws RunException    {	
		
		try {
			Class controllerClass;
			controllerClass = Class.forName("Controller."+className+"Controller");
			Constructor controllerConstructor = controllerClass.getConstructor();
		    return (Controller)controllerConstructor.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RunException(ResponseStatus.CONTROLLER_NOTFIND,"controller is not found",e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RunException(ResponseStatus.CONTROLLER_CONSTRUCTOR_NOTFIND,"controller Constructor is not found",e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RunException(ResponseStatus.CONTROLLER_CONSTRUCTOR_PROBLEM ,"controller Constructor  problem  ",e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RunException(ResponseStatus.CONTROLLER_CONSTRUCTOR_ACCESS,"controller Constructor Access",e.getMessage());			
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RunException(ResponseStatus.CONTROLLER_CONSTRUCTOR_PROBLEM ,"controller Constructor  problem  ",e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RunException(ResponseStatus.CONTROLLER_CONSTRUCTOR_PROBLEM ,"controller Constructor  problem  ",e.getMessage());
		}
	}

}
