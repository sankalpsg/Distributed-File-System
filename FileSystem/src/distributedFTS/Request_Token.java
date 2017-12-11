package distributedFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Request_Token {
	String token;
	String encryptedUsername;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getEncryptedUsername() {
		return encryptedUsername;
	}
	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}
	public Request_Token getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Request_Token tokenRequest = gson.fromJson(param,Request_Token.class);
		return tokenRequest;
		
	}

}
