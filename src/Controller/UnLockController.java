package Controller;

import java.util.HashMap;
import java.util.Map;

import socketServer.LockContainer;
 
 
import socketServer.ResponseStatus;
import socketServer.RunException;

public class UnLockController extends Controller  {
	
	
	@Override
	public  Map<String, String>  DoSomeThing(Map<String, String> hasMap) throws RunException {
		
		   String LockName = hasMap.get("LockName");
		   if(LockName == null) {
			   throw new RunException(ResponseStatus.ATTRS_FAIL, "缺少参数LockName");
		   }
		
		   Boolean  isLockBoolean = LockContainer.unlock(this.threadName,LockName);
		   Map<String, String> hashMap = new HashMap<String, String>();
		   if(isLockBoolean) {
			   hashMap.put("status", String.valueOf(ResponseStatus.SUCCESS));
			   hashMap.put("data","解锁成功");
		   }else {
			   hashMap.put("status", String.valueOf(ResponseStatus.UNLOCK_FAIL));
			   hashMap.put("data","解锁失败");
		   }
		   return hashMap; 
	}
	
 

}
