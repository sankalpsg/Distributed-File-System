package clientserver;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Request_Cache {

	String filename;

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
	
	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public Request_Cache getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Request_Cache classObj = gson.fromJson(replyInString, Request_Cache.class);
		return classObj;
	}
	
}