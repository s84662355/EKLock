package util;

import static org.fusesource.leveldbjni.JniDBFactory.asString;
import static org.fusesource.leveldbjni.JniDBFactory.bytes;
import static org.fusesource.leveldbjni.JniDBFactory.factory;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;

public class LogUtil {
	
	private  Thread thread = null;
	private  ConcurrentLinkedQueue<String> logQueue = null;
	private  static LogUtil instance = null;
	private  Random r ;
	private  SqliteDb sqliteDb = null;
	
	static public LogUtil getLogUtil() {
		if(LogUtil.instance == null) LogUtil.instance = new LogUtil();
		return LogUtil.instance;
	}
	
	private LogUtil() {
		this.logQueue = new ConcurrentLinkedQueue<String>();
		this.r=new Random();
		this.init();
	    this.thread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					String logString = LogUtil.this.logQueue.poll();
					if(logString == null)
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 	
					}else{
						Statement stmt = LogUtil.this.sqliteDb.getStatement();
						try {
							stmt.executeUpdate(logString);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					//	LevelDB.getDB().put((System.currentTimeMillis()+""+r.nextInt(100)).getBytes(), logString.getBytes());	
						System.out.print(logString);
					}
				}	
			}
		});
	}
	
	
	public boolean add(String data) {
		return this.logQueue.add(data);
	}
	
	public  boolean add(String operate , String thread , String lockname , int unlocktime , int waittime , String arg , int status , String  responsedata , long createtime )  
	{
		String sqlString;
		try {
			sqlString = this.insertSql( operate ,  thread ,  lockname , unlocktime ,  waittime ,  arg , status ,  responsedata ,  createtime );
			return this.logQueue.add(sqlString);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	public  boolean add (String operate , String thread , String lockname  , String arg , int status , String  responsedata , long createtime ) 
	{
		String sqlString;
		try {
			sqlString = this.insertSql( operate ,  thread ,  lockname ,   arg , status ,  responsedata ,  createtime );
			return this.logQueue.add(sqlString);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	
	public  boolean add(String operate , String thread ,  String arg , int status , String  responsedata , long createtime )
	{
		String sqlString;
		try {
			sqlString = this.insertSql( operate ,  thread ,  arg , status ,  responsedata ,  createtime );
			return this.logQueue.add(sqlString);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	
	public void  start() {
		if(!this.thread.isAlive()) {
			this.thread.start();
		}
	}
	
	private void init() {
	    this.sqliteDb = SqliteDb.getSqliteDb("LogDB");
	    try {
			Statement stmt = this.sqliteDb.getStatement();
		    String sql = "CREATE TABLE  if not exists logdata " +
	                  "(id INT PRIMARY KEY     NOT NULL," +
	                  "operate           TEXT  DEFAULT \"\", " + 
	                  "thread            TEXT  DEFAULT \"\", " + 
	                  "lockname          TEXT  DEFAULT \"\", " +
	                  "unlocktime        INTEGER DEFAULT 0 "+
	                  "waittime          INTEGER DEFAULT 0 "+
	                  "arg               TEXT  DEFAULT \"\" "+
	                  "status            INTEGER DEFAULT 0 "+
	                  "responsedata      TEXT  DEFAULT \"\" "+
	                  "createtime        INTEGER DEFAULT 0)"; 
		    
		    stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String insertSql(String operate , String thread , String lockname , int unlocktime , int waittime , String arg , int status , String  responsedata , long createtime ) throws SQLException {
		return  "INSERT INTO  logdata ( "
				+ "operate,"
				+ "thread,"
				+ "lockname,"
				+ "unlocktime,"
				+ "waittime,"
				+ "arg,"
				+ "status,"
				+ "responsedata,"
				+ "createtime ) " +
                "VALUES ( "
                + "'"+operate+"', "
                + "'"+thread+"', "
                + "'"+lockname+"', "
                + unlocktime +", "
                + waittime +", "
                + "'"+arg+"', "
                + status +", "
                + "'"+responsedata+"', "
                + createtime +");"; 
		 
	}	
	
	public String insertSql(String operate , String thread , String lockname  , String arg , int status , String  responsedata , long createtime ) throws SQLException {
		return  "INSERT INTO  logdata ( "
				+ "operate,"
				+ "thread,"
				+ "lockname,"
				+ "arg,"
				+ "status,"
				+ "responsedata,"
				+ "createtime ) " +
                "VALUES ( "
                + "'"+operate+"', "
                + "'"+thread+"', "
                + "'"+lockname+"', "
                + "'"+arg+"', "
                + status +", "
                + "'"+responsedata+"', "
                + createtime +");";
	}
	
	
	public String insertSql(String operate , String thread ,  String arg , int status , String  responsedata , long createtime ) throws SQLException {
		return  "INSERT INTO  logdata ( "
				+ "operate,"
				+ "thread,"
				+ "arg,"
				+ "status,"
				+ "responsedata,"
				+ "createtime ) " +
                "VALUES ( "
                + "'"+operate+"', "
                + "'"+thread+"', "
                + "'"+arg+"', "
                + status +", "
                + "'"+responsedata+"', "
                + createtime +");"; 	
	 
	}
 
}
