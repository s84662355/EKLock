package WebServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.naming.ldap.ControlFactory;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Controller.AuthController;
import Controller.ControllerFactory;
import socketServer.AuthToken;
import socketServer.Config;
import socketServer.EKeyLock;
import socketServer.LockContainer;
import socketServer.ProtocolParser;
import socketServer.ResponseStatus;
import socketServer.RunException;
import util.LogUtil;
import util.TimeUtil;


public class ApiHandler implements HttpHandler{
	int test = 0;
	 
	public ApiHandler() {}
 

	@Override
	public void handle(HttpExchange he) throws IOException {
		
		System.out.print("dadada");
		String requestMethod = he.getRequestMethod();
		if(requestMethod.equalsIgnoreCase("GET")){}
		
		String responsestring = "";

        String queryString =  he.getRequestURI().getQuery();
        Map<String,String> queryStringInfo = formData2Dic(queryString);
        
        String controllerString = queryStringInfo.get("do");
        Map<String, String> responseMap = null;
    	 
    	
    	if(controllerString == null) {
    		
    	}
        try {
	        switch (controllerString) {
				case "Auth":
					String tokenString = AuthToken.getInstance().createToken();
				    responseMap = new  AuthController().setThreadName(tokenString).DoSomeThing(queryStringInfo);
				    if(!responseMap.get("status").equals("0")) { 
				    	AuthToken.getInstance().delete(tokenString);
				    }else {
				    	AuthToken.getInstance().set(tokenString);
				    }
					break;
				default:
				 
					String token = queryStringInfo.get("token");
					if(token == null) {
						responseMap = new HashMap<String, String>();
						responseMap.put("status", String.valueOf(ResponseStatus.TOKEN_FAIL));
						responseMap.put("data", "缺少token参数");
						break;
					}
					if (AuthToken.getInstance().get(token) == null) {
						responseMap = new HashMap<String, String>();
						responseMap.put("status", String.valueOf(ResponseStatus.TOKEN_FAIL));
						responseMap.put("data", "token不存在");
						break;				
					}
					AuthToken.getInstance().set(token);
					responseMap = ControllerFactory.getController(controllerString)
		                     .setThreadName(token)
		                     .DoSomeThing(queryStringInfo);
					break;
			}
		} catch (RunException e) {
			e.printStackTrace();
			responseMap.put("status",String.valueOf(e.getStatus()));
			responseMap.put("data",e.getMsg());
			System.out.print("1111111");
		}
		
		OutputStream responseBody = he.getResponseBody();
		Headers responseHeaders = he.getResponseHeaders();
		
		responseHeaders.set("aa", "dscjscj");
		he.sendResponseHeaders(200, 0);
		
		responsestring = ProtocolParser.getResponse(responseMap);
		
        String requestlog =  "request:"+TimeUtil.getDate()+"---"+queryString;
        String responselog = "response:"+TimeUtil.getDate()+"---"+responsestring;
        
        String logdataString = requestlog + "  @@@@  " + responselog;
        
        LogUtil.getLogUtil().add(logdataString);
        

		responseBody.write(responsestring.getBytes());
		responseBody.close();
		
	}  
	
    public static Map<String,String> formData2Dic(String formData ) {
        Map<String,String> result = new HashMap<>();
        if(formData== null || formData.trim().length() == 0) {
            return result;
        }
        final String[] items = formData.split("&");
        Arrays.stream(items).forEach(item ->{
            final String[] keyAndVal = item.split("=");
            if( keyAndVal.length == 2) {
                try{
                    final String key = URLDecoder.decode( keyAndVal[0],"utf8");
                    final String val = URLDecoder.decode( keyAndVal[1],"utf8");
                    result.put(key,val);
                }catch (UnsupportedEncodingException e) {
                	
                }
            }
        });
        return result;
    }
    
    public String getLockinfo() {
	       Map<String, EKeyLock> lockMap = LockContainer.getContainer().getLocks();
	       List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		   lockMap.forEach(new BiConsumer<String, EKeyLock>() {
				@Override
				public void accept(String t, EKeyLock u) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", t);
					map.put("islock", String.valueOf(u.isIslock()));
					map.put("lockCount",String.valueOf(u.getLockCount()));
					map.put("locktime",String.valueOf(u.getLocktime()));
					map.put("unlocktime", String.valueOf(u.getUnlocktime()));
					map.put("threadname",String.valueOf(u.getThread_name()));
					map.put("waitlockcount",String.valueOf(u.getWaitlockcount()));
					list.add(map);
				}
		   });
		   return JSON.toJSONString(list);
    }

}
