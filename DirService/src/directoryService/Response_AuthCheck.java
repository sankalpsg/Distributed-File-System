package directoryService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Response_AuthCheck {

	String status;
	String key1;
	


	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getKey1() {
		return key1;
	}



	public void setKey1(String key1) {
		this.key1 = key1;
	}



	public Response_AuthCheck getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Response_AuthCheck response = gson.fromJson(replyInString, Response_AuthCheck.class);
		return response;
	}
}
