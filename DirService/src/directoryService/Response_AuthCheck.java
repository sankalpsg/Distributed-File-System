package directoryService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Response_AuthCheck {

	String authstatus;
	String key1;
	/**
	 * @return the authStatus
	 */
	public String getAuthstatus() {
		return authstatus;
	}

	/**
	 * @param authStatus the authStatus to set
	 */
	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}

	
	

	/**
	 * @return the key1
	 */
	public String getKey1() {
		return key1;
	}

	/**
	 * @param key1 the key1 to set
	 */
	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public Response_AuthCheck getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Response_AuthCheck response = gson.fromJson(replyInString, Response_AuthCheck.class);
		return response;
	}
}
