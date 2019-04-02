package socketServer;

public class RunException extends Exception{
	
	private String msg ;
	private int status ;
	private String errMsg;

	
	public RunException(int status,String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	public RunException(int status,String msg , String errMsg) {
		this.status = status;
		this.msg = msg;
		this.errMsg = errMsg;
	}	
	
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
