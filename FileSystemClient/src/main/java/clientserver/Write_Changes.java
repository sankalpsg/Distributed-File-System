package clientserver;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clientserver.Request_Write;
import clientserver.Support_Functions;

/**
 * Servlet implementation class WriteRemoteFile
 */
public class Write_Changes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Support_Functions hf; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Write_Changes() {
        super();
        // TODO Auto-generated constructor stub
    }
	public void init(ServletConfig config) throws ServletException {
		hf = new Support_Functions();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String token = (String)request.getSession().getAttribute("token");
		String key1 = (String)request.getSession().getAttribute("key1");
		String usernameEnc = (String)request.getSession().getAttribute("usernamenc");
		String filename = request.getParameter("fn");
		
		Request_Write writeRequest = new Request_Write();
		//writeRequest.setDirectory(directory);
		//After sending changes open the same page again with success msg
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("writefileopen.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
