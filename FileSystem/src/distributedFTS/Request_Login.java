package distributedFTS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Request_Login 
{

	String filename;
	String uname;
	String passwd;
	
    // Getter Setter Methods

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the uname
	 */
	public String getUname() {
		return uname;
	}

	/**
	 * @param uname the uname to set
	 */
	public void setUname(String uname) {
		this.uname = uname;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	// Convert from Json String to Class

	public Request_Login getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Request_Login lrequest = gson.fromJson(param,Request_Login.class);
		return lrequest;
		
	}
	
}
