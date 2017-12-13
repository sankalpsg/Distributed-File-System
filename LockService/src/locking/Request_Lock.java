package locking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Request_Lock {

	String uname;
	String filename;
	String token;
	String email;
	
	
	
	/**
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}
	/**
	 * @input uname the uname to set
	 */
	public void setUname(String uname) {
		this.uname = uname;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @input filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @input token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @input email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Request_Lock getClassFromJson(String input)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Request_Lock lrequest = gson.fromJson(input,Request_Lock.class);
		return lrequest;
		
	}


}
