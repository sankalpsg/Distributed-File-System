package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Request_ReadFile 
{
	String encryptedUsername;
	String directory;
	String filename;
	String token;
	
	
	
	public String getEncryptedUsername() {
		return encryptedUsername;
	}

	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public Request_ReadFile getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Request_ReadFile request = gson.fromJson(replyInString, Request_ReadFile.class);
		return request;
	}
	
}
