package locking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Response_Lock {
	
	String token;
	String encryptedUsername;
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
	
	public String getEncryptedUsername() {
		return encryptedUsername;
	}

	public void setEncrypted_Username(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}
	
	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String lockresponse = gson.toJson(this);
		return lockresponse;
	}
	
}
