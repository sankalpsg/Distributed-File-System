package clientserver;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clientserver.EncryptDecrypt;

import clientserver.Request_Login;
import clientserver.Response_Login;
import clientserver.UrlProperty;
import clientserver.Support_Functions;


// Servlet implementation class Login
 
public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Support_Functions sf;
       
    // @see HttpServlet#HttpServlet()
     
    public Login() 
    {
        super(); // Constructor
    }

	//@see Servlet#init(ServletConfig)
	
	public void init(ServletConfig config) throws ServletException 
	{
		sf = new Support_Functions();
		UrlProperty.import_Properties();
	}

	// @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Request_Login loginRequest= new Request_Login();
		//loginRequest.setUsername((String) request.getParameter("u"));
		loginRequest.setUsername(EncryptDecrypt.encrypt((String) request.getParameter("u"), (String) request.getParameter("p")));
		//String encryp = EncryptDecrypt.encrypt((String) request.getParameter("p"), (String) request.getParameter("p"));
		//String decryp = EncryptDecrypt.decrypt(encryp, (String) request.getParameter("p"));
		
		
		loginRequest.setPassword(EncryptDecrypt.encrypt((String) request.getParameter("p"), (String) request.getParameter("p")));
		String reply = sf.sendLoginRequest(loginRequest.getJsonString());
		Response_Login lResponse = new Response_Login();
		lResponse = lResponse.getClassFromJsonString(reply);
		
		if(lResponse.getAuthstatus().equals("Y")&&lResponse.getUsertype().equals("N")) 
		{
			request.getSession().setAttribute("fname", lResponse.getName());
			request.getSession().setAttribute("token", lResponse.getToken());
			request.getSession().setAttribute("key1", lResponse.getKey1());
			request.getSession().setAttribute("usernamenc", loginRequest.getUsername());
			request.getRequestDispatcher("welcome.jsp").forward(request, response);
		}
		
		else 
		{
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
	}

	
	 // @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
