package socketServer;

public class EKeyLock {
	private String thread_name;
	private long unlocktime;
	private boolean islock = false;
	private long locktime ;
	private int lockCount = 0;
	private int waitlockcount = 0;

	public EKeyLock(){	System.out.print("Create lock"); }
	
	public Boolean lock(String thread_name,long unlocktime,long waittime)  {
	 
		long starttime = System.currentTimeMillis();
		this.waitlockcount++;
		while ( true ) {
			 
			if( this.doLock(thread_name, unlocktime) ) { this.waitlockcount--; return true;}
			if(waittime>0){
				long nowtime = System.currentTimeMillis();
				if(nowtime-starttime>=waittime) { this.waitlockcount--; return false ;}				
			}else if (waittime == 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.waitlockcount--;
					return false;
				}
			}else {
				this.waitlockcount--;
				return false;
			}
		}		
	}
	
	public int getWaitlockcount() {
		return waitlockcount;
	}

	private Boolean doLock(String thread_name,long unlocktime) {
		synchronized (this) {
			if(!this.islock) {
				return this.lockDo(thread_name,unlocktime);
			}else{
				long nowtime = System.currentTimeMillis();
				if(nowtime-this.locktime>=this.unlocktime) { 
					return this.lockDo(thread_name,unlocktime);
				}
				return false;
			}
		}
	}
	
	public Boolean unlock(String thread_name) {
		synchronized (this) {
			if( this.islock && this.thread_name.equals(thread_name)) {
				this.thread_name = null;
				this.locktime = 0;
				this.islock = false;
				return true;
			}else {
				return false;
			}
		}		 
	}
	
	public Boolean unlockWithOutThread() {
		synchronized (this) {
			if( this.islock) {
				this.thread_name = null;
				this.locktime = 0;
				this.islock = false;
				return true;
			}else {
				return false;
			}
		}		
	}
	
	 
	
	private boolean lockDo(String thread_name,long unlocktime) {
		this.thread_name = thread_name;
		this.unlocktime = unlocktime;
		this.islock = true;
		this.locktime = System.currentTimeMillis();
		this.lockCount++;
		return true;
	}

	public String getThread_name() {
		return thread_name;
	}

	public long getUnlocktime() {
		return unlocktime;
	}

	public boolean isIslock() {
		return islock;
	}

	public long getLocktime() {
		return locktime;
	}

	public int getLockCount() {
		return lockCount;
	}
	
 
	
	
}
