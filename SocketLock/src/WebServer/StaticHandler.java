package WebServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class StaticHandler implements HttpHandler{

	@Override
	public void handle(HttpExchange he) throws IOException {
        String requestMethod = he.getRequestMethod();
        if (requestMethod.equalsIgnoreCase("GET")) {
        	Headers responseHeaders = he.getResponseHeaders();
        	OutputStream responseBody = he.getResponseBody();
        	File file = new File(System.getProperty("user.dir")+he.getRequestURI().getPath());
        	if(!file.exists()) {
               he.sendResponseHeaders(404, 0);      
        	}else if(file.isFile()) {
       	     	he.sendResponseHeaders(200, 0);
       	        InputStreamReader reader=new InputStreamReader(new FileInputStream(file));
       	        BufferedReader bfreader=new BufferedReader(reader);
       	     
       	        String line;
       	        while((line=bfreader.readLine())!=null) {//包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null
       	         responseBody.write( line.getBytes());
       	        }
       	      
        	}else if(file.isDirectory()) {       	  
       	     	he.sendResponseHeaders(200, 0);
                responseBody.write("不能访问目录".getBytes());      
        	}
            responseBody.close();
        }
		
	} 

}
