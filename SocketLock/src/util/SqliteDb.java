package util;

import java.sql.*;

public class SqliteDb {
	static private SqliteDb inSqliteDb = null;
	private Connection c = null;
	private Statement stmt = null;
	
	
	private SqliteDb(String dbpath) {
	    try {
	        Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:test.db");
	        this.stmt = c.createStatement();
	    } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	    }
	}
	
	public static SqliteDb getSqliteDb(String dbpath) {
		if(SqliteDb.inSqliteDb == null) SqliteDb.inSqliteDb = new SqliteDb(dbpath);
		return SqliteDb.inSqliteDb;
	}
	
	public Connection getConnection() {
		return this.c;
	}
	
	public Statement getStatement() {
		return this.stmt;
	}

 
	
	

}
