package directoryService;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import directoryService.EncryptDecrypt;
import directoryService.Support_Functions;

@Path("/dirServiceSearch")
public class Request_Handler 
{
	@POST
	@Consumes({"application/json"})
	@Path("/fetchFileInfo")
	public String getFileInfo(String input) 
	{
		DatabaseProperties.loadProperties();
		FileInfo_DSResponse getFileInfoFromDSResponse = new FileInfo_DSResponse();
		FileInfo_DSRequest getFileInfoFromDSRequest = new FileInfo_DSRequest();
		String getFileInfoFromDSResponseString=new String();
		Support_Functions sf = new Support_Functions();
		Request_AuthCheck checkReq = new Request_AuthCheck();
		Response_AuthCheck checkResponse = new Response_AuthCheck();
		getFileInfoFromDSRequest = getFileInfoFromDSRequest.getClassFromJsonString(input);
		HashMap<String, String> fileStats = new HashMap<String,String>();
		
		checkReq.setToken(getFileInfoFromDSRequest.getToken());
		checkReq.setEncryptedUsername(getFileInfoFromDSRequest.getEncryptedUsername());
		String authCheckRequest = checkReq.getJsonString();
		String authCheckResponse = sf.sendAuthCheckRequest(authCheckRequest);
		checkResponse = checkResponse.getClassFromJsonString(authCheckResponse);
		
		if(checkResponse.getAuthstatus().equals("Y")) 
		{			
				try 
				{
					if(getFileInfoFromDSRequest.getOperation().equals("r"))
					fileStats = sf.getFileLocationForRead(EncryptDecrypt.decrypt(getFileInfoFromDSRequest.getFilename(),checkResponse.getKey1()));
					else
					fileStats = sf.getFileLocationForWrite(EncryptDecrypt.decrypt(getFileInfoFromDSRequest.getFilename(),checkResponse.getKey1()));
					
					if(fileStats.isEmpty()) 
					{
						getFileInfoFromDSResponse.setAuthstatus("Directory Information Not Available for the file"); 
						getFileInfoFromDSResponseString = getFileInfoFromDSResponse.getJsonString();
						return getFileInfoFromDSResponseString;
					}
				
				} 
				
				catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
			
				getFileInfoFromDSResponse.setServerurl(EncryptDecrypt.encrypt(fileStats.get("serverurl"),checkResponse.getKey1()));
				getFileInfoFromDSResponse.setDirectory(EncryptDecrypt.encrypt(fileStats.get("directory"),checkResponse.getKey1()));
				getFileInfoFromDSResponse.setAuthstatus("Y");
			
		}
		
		else 
		{
			getFileInfoFromDSResponse.setAuthstatus("Validation of token Failed");
		}
		
		getFileInfoFromDSResponseString = getFileInfoFromDSResponse.getJsonString();
		System.out.println(getFileInfoFromDSResponseString);
		return getFileInfoFromDSResponseString;
	}
	
	// Full Info of the directory
	
	@POST
	@Consumes({"application/json"})
	@Path("/fullInfoDir")
	public String getDirInfo(String input) 
	{
		DatabaseProperties.loadProperties();
		FileInfo_DSRequest getFileInfoFromDSRequest = new FileInfo_DSRequest();
		Full_ResponsefromDS getCompleteInfoResponse = new Full_ResponsefromDS();
		String getFileInfoFromDSResponseString=new String();
		Support_Functions sf = new Support_Functions();
		Request_AuthCheck checkReq = new Request_AuthCheck();
		Response_AuthCheck checkResponse = new Response_AuthCheck();
		getFileInfoFromDSRequest = getFileInfoFromDSRequest.getClassFromJsonString(input);
		HashMap<String, String> fileStats = new HashMap<String,String>();
		checkReq.setToken(getFileInfoFromDSRequest.getToken());
		checkReq.setEncryptedUsername(getFileInfoFromDSRequest.getEncryptedUsername());
		String authCheckRequest = checkReq.getJsonString();
		String authCheckResponse = sf.sendAuthCheckRequest(authCheckRequest);
		checkResponse = checkResponse.getClassFromJsonString(authCheckResponse);
		if(checkResponse.getAuthstatus().equals("Y")) 
		{
			HashMap<String, String> fileList = sf.getCompleteFileList();
			getCompleteInfoResponse.setFilenameArray(fileList.get("filename"));
			getCompleteInfoResponse.setDirectoryArray(fileList.get("directory"));
			return getCompleteInfoResponse.getJsonString();
		}
		
		else 
		{
			return "{\"authstatus\":\"N\"}";
		}
	}

	@POST
	@Consumes({"application/json"})
	@Path("/test")
	public String testServer(String input) 
	{
		return "up and running";
	}
}

