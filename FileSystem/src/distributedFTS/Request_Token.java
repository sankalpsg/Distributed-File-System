package distributedFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Request_Token 
{
	String userName_Encrypted;
	String token;
	

	// Getter Setter Methods
	
	
	/**
	 * @return the userName_Encrypted
	 */
	public String getUserName_Encrypted() {
		return userName_Encrypted;
	}

	/**
	 * @param userName_Encrypted the userName_Encrypted to set
	 */
	public void setUserName_Encrypted(String userName_Encrypted) {
		this.userName_Encrypted = userName_Encrypted;
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


	// Converting from Json String to Class 

	public Request_Token getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Request_Token request_token = gson.fromJson(param,Request_Token.class);
		return request_token;
		
	}

}
