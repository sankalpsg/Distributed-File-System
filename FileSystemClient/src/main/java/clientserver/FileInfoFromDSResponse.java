package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileInfoFromDSResponse 
{

	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public String getAuthstatus() {
		return authstatus;
	}



	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}



	public String getServerurl() {
		return serverurl;
	}



	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}



	public String getDirectory() {
		return directory;
	}



	public void setDirectory(String directory) {
		this.directory = directory;
	}



	public String token;
	public String authstatus;
	public String serverurl;
	public String directory;

	

	public FileInfoFromDSResponse getClassFromJsonString(String replyInString) 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		FileInfoFromDSResponse resp = gson.fromJson(replyInString, FileInfoFromDSResponse.class);
		return resp;
	}
	 
}
