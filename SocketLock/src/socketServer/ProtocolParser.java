package socketServer;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Iterator;
import java.util.List;

public class ProtocolParser {
	private static final String String = null;
	static public Map<String, String> getRequest(String protocol) throws RunException{
	   String[] hashStrings = protocol.split("\\|");
	   int length = hashStrings.length;
	   Map<String, String>  hash_mapMap = new HashMap<String, String>();
	 
	   for (int i = 0; i < hashStrings.length; i++) {
		   String[] itemString  = hashStrings[i].split(":");
		    
		   if(itemString.length != 2) throw new RunException(ErrStatus.REQUEST_PROTOCOL_FAIL, "协议解析失败");
		   hash_mapMap.put(itemString[0], itemString[1]);
	   }
	   return hash_mapMap ;
	}
	
	
	static public String getResponse(Map<String, String>  map) {
		Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
		List<String> lists = new ArrayList<String>();
		while (entries.hasNext()) {
			Map.Entry<String, String> entry = entries.next();
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			lists.add( entry.getKey()+":"+entry.getValue() );
		}
		int len = lists.size();
		String respStrings = "";
		for (int i = 0; i < len; i++) {
			respStrings+=lists.get(i);
			if(i!=len-1)respStrings+="|";
		}
		return respStrings;
	}
}
