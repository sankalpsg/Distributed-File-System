package clientserver;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clientserver.Request_Write;
import clientserver.Response_Write;
import clientserver.Support_Functions;
import clientserver.EncryptDecrypt;


public class Write_Changes extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Support_Functions sf; 
       
    public Write_Changes() 
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
		String usernameEnc = (String)request.getSession().getAttribute("usernamenc");
		String filename = (String)request.getSession().getAttribute("filename");
		String directory = (String)request.getSession().getAttribute("directory");
		String filecontent = (String)request.getParameter("writebox");
		String serverurl = (String)request.getSession().getAttribute("serverurl");
		
		Request_Write Request_Write = new Request_Write();
		Request_Write.setDirectory(EncryptDecrypt.encrypt(directory, key1));
		Request_Write.setFilename(EncryptDecrypt.encrypt(filename, key1));
		Request_Write.setEncryptedUsername(usernameEnc);
		Request_Write.setFilecontent(EncryptDecrypt.encrypt(filecontent,key1));
		Request_Write.setToken(token);
		String jsonWriteRequest = Request_Write.getJsonString();
		String writeResponsereply = sf.sendWriteRequest(jsonWriteRequest,serverurl);
		Response_Write writeResponse = new Response_Write();
		writeResponse = writeResponse.getClassFromJsonString(writeResponsereply);
		
		//Success case
		if(null!=writeResponse.getAuthstatus() && writeResponse.getAuthstatus().equals("Y")) 
		{
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("message", "File Changes saved");
			response.getWriter().append("Served at: ").append(request.getContextPath());
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}
		
		//Token Validation Failed
		else if(null!=writeResponse.getAuthstatus() && writeResponse.getAuthstatus().equals("N")) 
		{
			request.getSession().setAttribute("status", "0");
			request.getSession().setAttribute("message", "Token Validation Failed, please re-login and try again");
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}
		
		else 
		{
			request.getSession().setAttribute("status", "1");
			request.getSession().setAttribute("message", "ERROR");
			response.getWriter().append("Served at: ").append(request.getContextPath());
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}
		
		 //Release Lock for file
        ReleaseLock releaseLock = new ReleaseLock();
        releaseLock.setEmail(EncryptDecrypt.encrypt("test@gmail.com", key1));
        releaseLock.setFilename(EncryptDecrypt.encrypt(filename, key1));
        releaseLock.setToken(token);
        releaseLock.setUsername(usernameEnc);
        String lockRequestStr = releaseLock.getJsonString();
        sf.sendUnLockRequest(lockRequestStr);
        
        
        String cacheFilename = CachingMemory.cacheStore.get(filename);
        if(cacheFilename != null) 
        {
        	CachingMemory.cacheStore.put(filename, filecontent);
        }

	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
