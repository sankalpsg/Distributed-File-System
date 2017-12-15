package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response_Write 
{
	String authstatus;
	String filestatus;
	

	public String getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}

	public String getFilestatus() {
		return filestatus;
	}

	public void setFilestatus(String filestatus) {
		this.filestatus = filestatus;
	}

	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public Response_Write getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Response_Write response = gson.fromJson(replyInString, Response_Write.class);
		return response;
	}
}
