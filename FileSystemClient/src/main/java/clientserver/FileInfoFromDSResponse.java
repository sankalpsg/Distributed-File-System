package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileInfoFromDSResponse 
{

	public String token;
	public String authStatus;
	public String server_IP;
	public String directory;

	public String getToken() 
	{
		return token;
	}

	public void setToken(String token) 
	{
		this.token = token;
	}

	public String getAuthStatus() 
	{
		return authStatus;
	}

	public void setAuthStatus(String authStatus) 
	{
		this.authStatus = authStatus;
	}

	public String getServer_IP() 
	{
		return server_IP;
	}

	public void setServer_IP(String server_IP) 
	{
		this.server_IP = server_IP;
	}

	public String getDirectory() 
	{
		return directory;
	}

	public void setDirectory(String directory) 
	{
		this.directory = directory;
	}

	public FileInfoFromDSResponse getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		FileInfoFromDSResponse resp = gson.fromJson(replyInString, FileInfoFromDSResponse.class);
		return resp;
	}
	 
}
