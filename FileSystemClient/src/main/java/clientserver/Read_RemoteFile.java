package clientserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clientserver.EncryptDecrypt;
import clientserver.CachingMemory;
import clientserver.FileInfoFromDSRequest;
import clientserver.FileInfoFromDSResponse;
import clientserver.Request_Read;
import clientserver.Response_Read;
import clientserver.Support_Functions;

//Servlet implementation class ReadRemoteFile

public class Read_RemoteFile extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Support_Functions sf; 
  
    public Read_RemoteFile() 
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
		String token = (String)request.getSession().getAttribute("token");
		String key1 = (String)request.getSession().getAttribute("key1");
		String uname_encrypt = (String)request.getSession().getAttribute("usernamenc");
		String filename = request.getParameter("fn");
		
		// Verify whether the file is in Cache or not
		
		Long ts_then = CachingMemory.cacheStoreTime.get(filename);
		Long ts_now = System.currentTimeMillis();
		Long ts_max = TimeUnit.MINUTES.toMillis(Long.parseLong(UrlProperty.cache_Time));
		
		if(ts_then != null && ts_now-ts_then<ts_max) 
		{//Serve from cache if entry time is within persistence limit
			String filecontent = CachingMemory.cacheStore.get(filename);
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("filecontent", filecontent);
			request.getSession().setAttribute("filename", filename);
			request.getRequestDispatcher("readfile.jsp").forward(request, response);
		}
		
		else
		{
			if(ts_then != null && ts_now-ts_then>ts_max)  //Remove from cache if older than max persistence time allowed
			{
				CachingMemory.cacheStore.remove(filename);
				CachingMemory.cacheStoreTime.remove(filename);
			}
		
		
			//Get file info from Directory Service
			FileInfoFromDSRequest infoRequest = new FileInfoFromDSRequest();
			infoRequest.setFilename(EncryptDecrypt.encrypt(filename, key1));
			infoRequest.setToken(token);
			infoRequest.setUname_Encrypted(uname_encrypt);
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
				readRequest.setEncryptedUsername(uname_encrypt);
				readRequest.setDirectory(fileInfoResponse.getDirectory());//This is also encrypted with key1
				String jsonReadRequest = readRequest.getJsonString();
				String readResponsereply = sf.sendReadRequest(jsonReadRequest,fileInfoResponse.getServer_IP());
				Response_Read readResponse = new Response_Read();
				readResponse = readResponse.getClassFromJsonString(readResponsereply);
				String filecontent = EncryptDecrypt.decrypt(readResponse.getFilecontent(),key1);
			
				if(CachingMemory.cacheStore.size()<20) 
				{
					System.out.println("Size of cache: "+ CachingMemory.cacheStore.size());
					CachingMemory.cacheStore.put(filename, filecontent);
					CachingMemory.cacheStoreTime.put(filename, System.currentTimeMillis());
				}
			
				request.getSession().setAttribute("status", "1");
				request.getSession().setAttribute("filecontent", filecontent);
				request.getSession().setAttribute("filename", filename);
				request.getRequestDispatcher("readfile.jsp").forward(request, response);
			}
		
			else 
			{
				System.out.println("Validation Failed");
				request.getSession().setAttribute("status", "0");
				if(fileInfoResponse.getAuthStatus()==null)
					fileInfoResponse.setAuthStatus("");
				request.getSession().setAttribute("message", fileInfoResponse.getAuthStatus());
				request.getRequestDispatcher("readfile.jsp").forward(request, response);
			}
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
