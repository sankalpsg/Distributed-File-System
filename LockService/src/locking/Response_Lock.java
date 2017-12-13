package locking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response_Lock {
	
	String token;
	String encrypted_Username;
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
	 * @return the encrypted_Username
	 */
	public String getEncrypted_Username() {
		return encrypted_Username;
	}
	/**
	 * @param encrypted_Username the encrypted_Username to set
	 */
	public void setEncrypted_Username(String encrypted_Username) {
		this.encrypted_Username = encrypted_Username;
	}
	
	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String lockresponse = gson.toJson(this);
		return lockresponse;
	}
	
}
