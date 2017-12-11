package distributedFTS;

/**
 * @author Sankalp
 *
 */

import java.io.*;
import java.sql.*;
import java.sql.Statement;
import java.util.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


// Secure Login Request is Processed
@Path("/fts")
public class Server_Login 
{
	@POST
	@Consumes({"application/json"})
	@Path("/signIn")
	public String login(String input){


		Request_Login lrequest = new Request_Login();
		lrequest = lrequest.getClassFromJson(input);

		String client_uname = lrequest.getUname();
		String client_passwd = lrequest.getPasswd();


		Response_Login lresponse = new Response_Login();
		
		// Authenticating the login user 
		try

		{

			Connection connect = ConnectionDao.sqlconnect(); 
			Statement stmt=connect.createStatement();  
			ResultSet rs=stmt.executeQuery("select fname,usertype,token,pswd from users where encrypt_username = '" + client_uname +"';");

			if(rs.next())
			{

				// If username gets Authenicated, token and key 1 is sent to client as response
				if(rs.getString(4).equals(Cryption.decrypt(client_passwd, rs.getString(4))))
				{
					lresponse.setStatus("Y");
					lresponse.setFullname(rs.getString(1));
					lresponse.setUser_type(rs.getString(2));
					lresponse.setToken(rs.getString(3));
					
					 String key1 = Cryption.getInitialKey();
					 String key2 = Cryption.getInitialKey();
					 String time = String.valueOf(System.currentTimeMillis());
					 String myToken = lresponse.getFullname() + ";;" + key1 + ";;" + time;
					
					 lresponse.setToken(Cryption.encrypt(myToken,key2));
					 lresponse.setKey1(key1);
					 String query = "update users set key1 = ?, key2 = ?, token = ? where encrypt_username = '" + client_uname +"';  ";
				        PreparedStatement preparedStmt = connect.prepareStatement(query);
				        preparedStmt.setString (1, key1);
				        preparedStmt.setString (2, key2);
				        preparedStmt.setString (3, lresponse.getToken());
				        preparedStmt.execute();
					return lresponse.getJsonString();
				}


			}

			// Authentication gets rejected and the Status is sent as N
			lresponse.setStatus("N");
			connect.close();  
		}

		catch(Exception e)
		{

			System.out.println("Exception in main"+e);

		}  

		return lresponse.getJsonString();
	}

	
	// Authentication of the token
	
	@POST
	@Consumes({"application/json"})
	@Path("/tokenCheck")
	public String tokenCheck(String input){
		
		Request_Token tk_request = new Request_Token();
		tk_request = tk_request.getClassFromJson(input);
		
		String client_uname = tk_request.getUserName_Encrypted();
		String client_token = tk_request.getToken();
		
		System.out.println("username is "+client_uname);
		System.out.println("token is "+client_token);

		Response_Token tresponse = new Response_Token();
		Connection connect = ConnectionDao.sqlconnect();
		try 
		{
			Statement stmt=connect.createStatement();  
			ResultSet rs=stmt.executeQuery("select token,key2,key1 from users where token = '" + client_token +"' and encrypt_username = '" + client_uname +"';");
		
		if(rs.next())
		{
			client_token = Cryption.decrypt(client_token, rs.getString(2));
			StringTokenizer st = new StringTokenizer(client_token,";;");
			String ds_ttl = new String();
			while (st.hasMoreTokens()) 
		    {  
		    	 ds_ttl = st.nextToken();  
		    }
		     
		    long a = Long.parseLong(ds_ttl);
		    System.out.println(""+a);
		    long b = System.currentTimeMillis();
		    
		    if(b - a <= 300000)
		    {
		    	tresponse.setStatus("Y");
		 		tresponse.setKey1(rs.getString(3));
		 		return tresponse.getJsonString();
		    }
			
				
		}			
		
		tresponse.setStatus("N");
		return tresponse.getJsonString();	
		
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tresponse.setStatus("N");
		return tresponse.getJsonString();


	}	

}








