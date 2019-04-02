package socketServer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class AuthToken {
	private Map<String, Long> tokenMap = null;
	private static AuthToken instance = null;
	private Thread thread = null;
	
	private AuthToken() {
		this.tokenMap = new HashMap<String, Long>();
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					 AuthToken.this.tokenMap.forEach(new BiConsumer<String, Long>() {
						@Override
						public void accept(String t, Long u) {
							if(System.currentTimeMillis() - u > 20000) {
								AuthToken.getInstance().delete(t);
							}
						}
					 });	
					 try {
						Thread.sleep(5000);
					 } catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					 }
				}
			}
		});
		this.thread.start();
	}
	
	public static AuthToken getInstance() {
		if(AuthToken.instance == null) AuthToken.instance = new AuthToken();
		return AuthToken.instance;
	}
	
	public String createToken() {
		String tokenString  = System.currentTimeMillis()+"";
		synchronized (this) {
			this.tokenMap.put(tokenString,System.currentTimeMillis());
		}
		return tokenString;
	}
	
	public synchronized void set(String token) {
		this.tokenMap.put(token, System.currentTimeMillis());
	}
	
	public synchronized void delete(String token) {
		this.tokenMap.remove(token);
	}
	
	public Long get(String token) {
		this.tokenMap.get(token);
		return  null;
	}

}
