/**
 * 
 */
package distributedFTS;

import com.google.gson.Gson;

public class Response_Login 
{
	String name;
	String token;
	String authstatus;
	String usertype;
	public String getUsertype() 
	{
		return usertype;
	}
	public void setUsertype(String usertype) 
	{
		this.usertype = usertype;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getToken() 
	{
		return token;
	}
	public void setToken(String token) 
	{
		this.token = token;
	}
	public String getAuthstatus() 
	{
		return authstatus;
	}
	public void setAuthstatus(String authstatus) 
	{
		this.authstatus = authstatus;
	}
	
	public String getJsonString()
	{
		Gson gson = new Gson();
		String ResponseLogin = gson.toJson(this);
		return ResponseLogin;
	}
}
