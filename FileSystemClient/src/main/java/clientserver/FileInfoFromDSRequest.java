package clientserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileInfoFromDSRequest 
{

	String token;
	String filename;
	String uname_Encrypted;
	String Operatn;
	
	public String getUname_Encrypted() 
	{
		return uname_Encrypted;
	}
	
	public void setUname_Encrypted(String uname_Encrypted) 
	{
		this.uname_Encrypted = uname_Encrypted;
	}
	public String getOperatn() 
	{
		return Operatn;
	}

	public void setOperatn(String operatn) 
	{
		Operatn = operatn;
	}

	/**
	 * @return the token
	 */
	public String getToken() 
	{
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) 
	{
		this.token = token;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() 
	{
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) 
	{
		this.filename = filename;
	}
	
	
	
	//Convert class to Json String
	public String getJsonString() 
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}

}
