package storage;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import storage.Support_Functions;
import storage.Request_AuthCheck;
import storage.Response_AuthCheck;
import storage.UrlProperties;
import storage.Request_ReadFile;
import storage.Response_ReadFile;
import storage.Request_WriteFile;
import storage.Response_WriteFile;
import storage.EncryptDecrypt;

@Path("/reader")
public class Request_Handler 
{
	
	@POST
	@Consumes({"application/json"})
	@Path("/readFile")
	public String readFile(String input) 
	{
		UrlProperties.loadProperties();
		Request_ReadFile readRequest = new Request_ReadFile();
		readRequest = readRequest.getClassFromJsonString(input);
		String token = readRequest.getToken();
		String encUsername = readRequest.getEncryptedUsername();
		String directory = readRequest.getDirectory();
		String filename = readRequest.getFilename();
		
		//Send request to AS to check token
		Request_AuthCheck checkReq = new Request_AuthCheck();
		Response_AuthCheck checkResponse = new Response_AuthCheck();
		Support_Functions sf = new Support_Functions();
		Response_ReadFile readResponse = new Response_ReadFile();
		String path = "";
		checkReq.setToken(token);
		checkReq.setEncryptedUsername(encUsername);
		String authCheckRequest = checkReq.getJsonString();
		String authCheckResponse = sf.sendAuthCheckRequest(authCheckRequest);
		checkResponse = checkResponse.getClassFromJsonString(authCheckResponse);
		
		if(checkResponse.getStatus().equals("Y")) 
		{
			//First decrypt directory, filename
			filename = EncryptDecrypt.decrypt(filename, checkResponse.getKey1());
			directory = EncryptDecrypt.decrypt(directory, checkResponse.getKey1());
			//Encrypt file content with key1 and return content
			path = "C:\\Users\\Sankalp\\Desktop\\FileStorage\\"+directory+filename;
			String fcontent="";
			try 
			{
				fcontent = new String(Files.readAllBytes(Paths.get(path)));
				System.out.println(fcontent);
				fcontent = EncryptDecrypt.encrypt(fcontent, checkResponse.getKey1());
				//Send fileContent and auth status back to client
				readResponse.setAuthstatus(checkResponse.getStatus());
				readResponse.setFilecontent(fcontent);
			} 
			
			catch (NoSuchFileException e) 
			{
				fcontent = "";
				fcontent = EncryptDecrypt.encrypt(fcontent, checkResponse.getKey1());
				//Send filecontent and auth status back to client
				readResponse.setAuthstatus(checkResponse.getStatus());
				readResponse.setFilecontent(fcontent);
			}
			
			catch(IOException e) 
			{
				readResponse.setAuthstatus(checkResponse.getStatus());
				e.printStackTrace();
			}
		
		}
		
		else 
		{
			readResponse.setAuthstatus(checkResponse.getStatus());
		}
		
		String readResponseJson = readResponse.getJsonString();
		System.out.println(readResponseJson);
		return readResponseJson;
	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/writeFile")
	public String writeFile(String input) 
	{
		UrlProperties.loadProperties();
		Request_WriteFile writeRequest = new Request_WriteFile();
		Response_WriteFile writeResponse = new Response_WriteFile();
		writeRequest = writeRequest.getClassFromJsonString(input);
		String token = writeRequest.getToken();
		String encUsername = writeRequest.getEncryptedUsername();
		String directory = writeRequest.getDirectory();
		String filename = writeRequest.getFilename();
		String filecontent = writeRequest.getFilecontent();
		//Send request to AS to check token
		Request_AuthCheck checkReq = new Request_AuthCheck();
		Response_AuthCheck checkResponse = new Response_AuthCheck();
		Support_Functions sf = new Support_Functions();
		String path = "";
		checkReq.setToken(token);
		checkReq.setEncryptedUsername(encUsername);
		String authCheckRequest = checkReq.getJsonString();
		String authCheckResponse = sf.sendAuthCheckRequest(authCheckRequest);
		checkResponse = checkResponse.getClassFromJsonString(authCheckResponse);
		
		if(checkResponse.getStatus().equals("Y")) 
		{
			//First decrypt directory, filename
			filename = EncryptDecrypt.decrypt(filename, checkResponse.getKey1());
			directory = EncryptDecrypt.decrypt(directory, checkResponse.getKey1());
			filecontent = EncryptDecrypt.decrypt(filecontent, checkResponse.getKey1());
			//Encrypt file content with key1 and return content
			path = "C:\\Users\\Sankalp\\Desktop\\FileStorage\\"+directory+filename;
			try 
			{
				Files.write(Paths.get(path), filecontent.getBytes());
				writeResponse.setAuthstatus(checkResponse.getStatus());
			}
			
			catch(IOException e) 
			{
				writeResponse.setAuthstatus("N");
				e.printStackTrace();
			}
		}
		
		else 
		{
			writeResponse.setAuthstatus("N");
		}
		
		return writeResponse.getJsonString();
	}
	
	 // Created only to test if webserver is up and running
	 
	@POST
	@Consumes({"application/json"})
	@Path("/test")
	public String testServer(String input) 
	{
		return "up and running";
	}

}
