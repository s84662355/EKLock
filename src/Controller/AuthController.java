package Controller;

import java.util.HashMap;
import java.util.Map;

import org.dtools.ini.IniFile;

import socketServer.IniParser;
import socketServer.ResponseStatus;
import socketServer.RunException;

public class AuthController extends Controller{

	@Override
	public Map<String, String> DoSomeThing(Map<String, String> hasMap) throws RunException {
		String secretkey =  hasMap.get("secretkey");
		if(secretkey == null) throw new RunException(ResponseStatus.ATTRS_FAIL, "缺少参数secretkey");
		
	    String authsecretkey = IniParser.getParser().get("base").getSection("Auth").getItem("secretkey").getValue();
	    
		Map<String, String> hashMap = new HashMap<String, String>();
		if(!secretkey.equals(authsecretkey)) {
			
			   hashMap.put("status", String.valueOf(ResponseStatus.AUTH_FAIL));
			   hashMap.put("data","秘钥错误");
			   
		}else {
			
			   hashMap.put("status", String.valueOf(ResponseStatus.SUCCESS));
			   hashMap.put("data",this.threadName);
		}
 
		return hashMap;
	}
	
	

}
