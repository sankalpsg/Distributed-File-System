/**
 * 
 */
package distributedFTS;

/**
 * @author Sankalp
 *
 */

import java.io.*;
import java.nio.*;
import java.sql.*;
import java.sql.Statement;
import java.util.*;
import javax.ws.*;
import org.apache.catalina.connector.Response;


@Path("/fts")
public class Server_Login 
{
	@POST
	@Consumes({"application/json"})
	@Path("/signIn")
	public String create(String param1)
	{
	    System.out.println("param1 = " + param1);
	    Request_Login lr = new Request_Login();
	    lr = lr.getClassFromJson(param1);
	    
	    String client_username = lr.getUsername();
	    String client_password = lr.getPassword();
	    
	    Response_Login lresponse = new Response_Login();
	    
	    try
	    {	    	
			  	Class.forName("com.mysql.jdbc.Driver");  
			   	Connection con=DriverManager.getConnection(  
			  	"jdbc:mysql://localhost:3306/fileserver","root","Prashant123!");  
			  
			   	Statement stmt=con.createStatement();  
			   	ResultSet rs=stmt.executeQuery("select fname,usertype,token from users where username = '" + client_username + "' and pswd = '"+ client_password+"';");
			    	
			   	if(rs.next())
			   	{
			    		lresponse.setAuthstatus("Y");
			    		lresponse.setName(rs.getString(1));
			    		lresponse.setUsertype(rs.getString(2));
			    		lresponse.setToken(rs.getString(3));
			    		return lresponse.getJsonString();
			   	}
			     
			   	lresponse.setAuthstatus("N");
		    	con.close();  
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Exception in main"+e);
	    }  
	    
	    return lresponse.getJsonString();
	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/fileRead")
	public void file_read(String param1) throws IOException 
	{
		System.out.println("Execution of File read start here!");
		Request_Login lr = new Request_Login();
    	lr = lr.getClassFromJson(param1);
		String filename = lr.getFilename();
		System.out.println("filename entered is"+filename);
		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(filename))) 
		{
			stream.forEach(System.out::println);

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/fileWrite")
	public void file_write() 
	{
		
		
		
	}
	
  
}
	
