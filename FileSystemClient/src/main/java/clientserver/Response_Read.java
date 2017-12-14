package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response_Read 
{
	String filecontent;

	public String getFilecontent() 
	{
		return filecontent;
	}

	public void setFilecontent(String filecontent) 
	{
		this.filecontent = filecontent;
	}
	
	public Response_Read getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Response_Read response = gson.fromJson(replyInString, Response_Read.class);
		return response;
	}
}
