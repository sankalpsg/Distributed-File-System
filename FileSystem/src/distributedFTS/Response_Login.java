package distributedFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response_Login 
{	
	String user_type;
	String key1;
	String fullname;
	String token;
	String status;
	
	// Getter Setter Methods
	/**
	 * @return the user_type
	 */
	public String getUser_type() {
		return user_type;
	}


	/**
	 * @param user_type the user_type to set
	 */
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}


	/**
	 * @return the key1
	 */
	public String getKey1() {
		return key1;
	}


	/**
	 * @param key1 the key1 to set
	 */
	public void setKey1(String key1) {
		this.key1 = key1;
	}


	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}


	/**
	 * @param fullname the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}


	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	// Converting to Json String format	
	
	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String response_login = gson.toJson(this);
		return response_login;
	}
}
