package socketServer;

import java.awt.List;
import java.util.HashMap;
import java.util.Map;

public class LockContainer {
	static private LockContainer iContainer = null;
	private Map<String, EKeyLock> lockMap;
	private LockContainer() {
		this.lockMap =  new HashMap<String,  EKeyLock>();
	}
	static  public LockContainer getContainer() {
		if(LockContainer.iContainer == null) LockContainer.iContainer = new LockContainer();
		return LockContainer.iContainer;
	}
	static public boolean lock(String thread_name,String key , long unlocktime,long waittime)   {
		return LockContainer.getContainer().EKLock(thread_name, key, unlocktime, waittime);
	}
	static public boolean unlock(String thread_name,String key) {
		return LockContainer.getContainer().EKUnLock(thread_name, key);
	}
	public synchronized  EKeyLock set(String key) {
		EKeyLock lock = this.lockMap.get(key);
		if( lock  != null)return lock;
		lock = new EKeyLock();
		this.lockMap.put(key, lock);
		return this.lockMap.get(key);
	}
	public EKeyLock get(String key) {
			EKeyLock lock = this.lockMap.get(key);
			if( lock  != null) {
				return lock;
			} 
			return this.set(key);
	}
	public boolean  EKLock(String thread_name,String key , long unlocktime,long waittime )  {
		   EKeyLock eklock = this.get(key);
		   return  eklock.lock(thread_name, unlocktime,waittime );
	}
	
	public boolean  EKUnLock(String thread_name,String key ) {
		 EKeyLock eklock = this.get(key);
		 return eklock.unlock(thread_name);
	}
	
	public boolean  EKUnLockWithOutThread( String key ) {
		 EKeyLock eklock = this.get(key);
		 return eklock.unlockWithOutThread();
	}
	
	
	public Map<String, EKeyLock> getLocks(){
		return this.lockMap;
	}
	
 
}
