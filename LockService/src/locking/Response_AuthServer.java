package locking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response_AuthServer {

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


	public Response_AuthServer getClassFromJson(String input)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Response_AuthServer response_AuthServer = gson.fromJson(input,Response_AuthServer.class);
		return response_AuthServer;
		
	}


}
