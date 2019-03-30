package socketServer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dtools.ini.BasicIniFile;
import org.dtools.ini.IniFile;
import org.dtools.ini.IniFileReader;
import org.dtools.ini.IniSection;

public class IniParser {
	static private IniParser  iniParser = null;
	Map<String, IniFile> hashMap ;
	
	private IniParser() {
		this.hashMap = new HashMap<String, IniFile>();
	}
	
	static public IniParser getParser() {
		if(IniParser.iniParser == null) IniParser.iniParser = new IniParser();
		return IniParser.iniParser;
	}
	
	public IniParser setIni(String keyString , String path) throws IOException {
		IniFile iniFile=new BasicIniFile();
		File file=new File(path);
		IniFileReader rad=new IniFileReader(iniFile, file);
		rad.read();
		this.hashMap.put(keyString, iniFile);
		return this;
	}
	
	public IniFile get(String keyString) {
		return this.hashMap.get(keyString);
	}
}


/*
 * 
 * 		 IniFile iniFile=new BasicIniFile();
		 File file=new File(args[0]);

		 IniFileReader rad=new IniFileReader(iniFile, file);
		 rad.read();
		 
		 IniSection iniSection=iniFile.getSection("LockServer");
		  
		 int LockServerPort = Integer.parseInt(iniSection.getItem("port").getValue()) ;
		 
		 int LockServerThread_Num  = Integer.parseInt(iniSection.getItem("thread_num").getValue()) ;
*/
 