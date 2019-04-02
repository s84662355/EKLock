package util;

import static org.fusesource.leveldbjni.JniDBFactory.bytes;
import static org.fusesource.leveldbjni.JniDBFactory.factory;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

public class LevelDB {
	
	private static DB db = null;
	
	
	public static DB getDB() {
		if(LevelDB.db == null) {
			  Options options = new Options();
			  options.createIfMissing(true);
			  try {
				  LevelDB.db = factory.open(new File("example"), options);
			  } catch (IOException e) {
					e.printStackTrace();
					return null;
			  }	   
		}
		return LevelDB.db;
	}
	

}
