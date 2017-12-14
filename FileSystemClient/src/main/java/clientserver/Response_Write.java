package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response_Write 
{
	String authStatus;
	String fileStatus;
	
	public String getAuthStatus() 
	{
		return authStatus;
	}

	public void setAuthStatus(String authStatus) 
	{
		this.authStatus = authStatus;
	}

	public String getFileStatus() 
	{
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) 
	{
		this.fileStatus = fileStatus;
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
