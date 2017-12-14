package clientserver;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clientserver.EncryptDecrypt;
import clientserver.FileInfoFromDSRequest;
import clientserver.FileInfoFromDSResponse;
import clientserver.Request_Lock;
import clientserver.Response_Lock;
import clientserver.Request_Read;
import clientserver.Response_Read;
import clientserver.Support_Functions;

public class Write_RemoteFile extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Support_Functions sf; 
 
    public Write_RemoteFile() 
    {
        super();
    }
    
	public void init(ServletConfig config) throws ServletException 
	{
		sf = new Support_Functions();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String token = (String)request.getSession().getAttribute("token");
		String key1 = (String)request.getSession().getAttribute("key1");
		String usernameEnc = (String)request.getSession().getAttribute("usernamenc");
		String filename = request.getParameter("fn");
		
		//Get file info from Directory Service
		FileInfoFromDSRequest infoRequest = new FileInfoFromDSRequest();
		infoRequest.setFilename(EncryptDecrypt.encrypt(filename, key1));
		infoRequest.setToken(token);
		infoRequest.setUname_Encrypted(usernameEnc);
		infoRequest.setOperatn("w");
		String infoRequestJson = infoRequest.getJsonString();
		String replyInfoRequest = sf.getDirectoryInfo(infoRequestJson);
		FileInfoFromDSResponse fileInfoResponse = new FileInfoFromDSResponse();
		fileInfoResponse=fileInfoResponse.getClassFromJsonString(replyInfoRequest);
		
		if(fileInfoResponse.getAuthStatus().equals("Y"))
		{
			fileInfoResponse.setServer_IP(EncryptDecrypt.decrypt(fileInfoResponse.getServer_IP(),key1));
			Request_Read readRequest = new Request_Read();
			readRequest.setFilename(EncryptDecrypt.encrypt(filename, key1));
			readRequest.setToken(token);
			readRequest.setEncryptedUsername(usernameEnc);
			readRequest.setDirectory(fileInfoResponse.getDirectory());//This is also encrypted with key1
			String jsonReadRequest = readRequest.getJsonString();
			String readResponsereply = sf.sendReadRequest(jsonReadRequest,fileInfoResponse.getServer_IP());
			Response_Read readResponse = new Response_Read();
			readResponse = readResponse.getClassFromJsonString(readResponsereply);
			String filecontent = EncryptDecrypt.decrypt(readResponse.getFilecontent(),key1);
			
			//Send Request to lock file
			Request_Lock lockRequest = new Request_Lock();
			lockRequest.setEmail(EncryptDecrypt.encrypt("test@gmail.com", key1));
			lockRequest.setFilename(EncryptDecrypt.encrypt(filename, key1));
			lockRequest.setToken(token);
			lockRequest.setUsername(usernameEnc);
			String lockRequestStr = lockRequest.getJsonString();
			String lockResponseStr = sf.sendLockRequest(lockRequestStr);
			Response_Lock lockResponse = new Response_Lock(); 
			lockResponse = lockResponse.getClassFromJsonString(lockResponseStr);
			
			//Success case 
			if(lockResponse.getLockstatus()!=null && lockResponse.getLockstatus().equalsIgnoreCase("Y"))	
			{
				request.getSession().setAttribute("status", "1");
				request.getSession().setAttribute("filecontent", filecontent);
				request.getSession().setAttribute("filename", filename);
				request.getSession().setAttribute("directory", EncryptDecrypt.decrypt(fileInfoResponse.getDirectory(),key1));
				request.getSession().setAttribute("serverurl", fileInfoResponse.getServer_IP());
				System.out.println(filecontent);
				request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			}
			
			//Failure case with unable to get lock on file
			else if(lockResponse.getLockstatus()!=null && lockResponse.getLockstatus().equalsIgnoreCase("N")) 
			{
				System.out.println("Validation Failed");
				request.getSession().setAttribute("status", "0");
				if(fileInfoResponse.getAuthStatus()==null)
				    fileInfoResponse.setAuthStatus("");
				request.getSession().setAttribute("message", "Unable to get lock on file, file is already locked");
				request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			} 
			
			//Failure case when token validation or any other problem
			else
			{
				System.out.println("Validation Failed");
				request.getSession().setAttribute("status", "0");
				if(fileInfoResponse.getAuthStatus()==null)
					fileInfoResponse.setAuthStatus("");
				request.getSession().setAttribute("message", "Token Validation Failed");
				request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
			}			
		}
		
		else 
		{
			System.out.println("Validation Failed");
			request.getSession().setAttribute("status", "0");
			if(fileInfoResponse.getAuthStatus()==null)
				fileInfoResponse.setAuthStatus("");
			request.getSession().setAttribute("message", fileInfoResponse.getAuthStatus());
			request.getRequestDispatcher("writefileopen.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
