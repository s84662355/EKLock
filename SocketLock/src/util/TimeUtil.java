package util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {
	
	static public String getDate() {  
		long timeStamp = System.currentTimeMillis();  //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
		String sd = sdf.format(new Date(timeStamp));
		return sd;
	}

}
