package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response_Lock {
	String authstatus;
	String lockstatus;
	/**
	 * @return the authstatus
	 */
	public String getAuthstatus() {
		return authstatus;
	}
	/**
	 * @param authstatus the authstatus to set
	 */
	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}
	/**
	 * @return the lockstatus
	 */
	public String getLockstatus() {
		return lockstatus;
	}
	/**
	 * @param lockstatus the lockstatus to set
	 */
	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
	}

	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public Response_Lock getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Response_Lock response = gson.fromJson(replyInString, Response_Lock.class);
		return response;
	}
}
