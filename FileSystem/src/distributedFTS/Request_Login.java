/**
 * 
 */
package distributedFTS;

import com.google.gson.Gson;

public class Request_Login {
	String username;
	String password;
	String filename;

	
	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Request_Login getClassFromJson(String param)
	{
		Gson gson = new Gson();
		Request_Login loginRequest = gson.fromJson(param,Request_Login.class);
		return loginRequest;
	}
	
 }


