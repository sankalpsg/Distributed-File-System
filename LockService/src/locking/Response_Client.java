package locking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response_Client {

	String writeStatus;
	String authStatus;
	String releaseStatus; 
	
	

	public String getWriteStatus() {
		return writeStatus;
	}



	public void setWriteStatus(String writeStatus) {
		this.writeStatus = writeStatus;
	}



	public String getAuthStatus() {
		return authStatus;
	}



	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}



	public String getReleaseStatus() {
		return releaseStatus;
	}



	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}



	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String responsetoclient = gson.toJson(this);
		return responsetoclient;
	}
}
