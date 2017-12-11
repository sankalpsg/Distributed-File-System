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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Stream;

import javax.ws.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import java.security.Key;
import org.apache.catalina.connector.Response;

@Path("/fts")
public class Server_Login {
	@POST
	@Consumes({"application/json"})
	@Path("/signIn")
	public String create(String param1){

		System.out.println("param1 = " + param1);

		Request_Login lr = new Request_Login();
		lr = lr.getClassFromJson(param1);

		String client_username = lr.getUsername();
		String client_password = lr.getPassword();


		Response_Login lresponse = new Response_Login();

		try

		{

			Connection conn = ConnectionDao.sqlconnect(); 

			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select fname,usertype,token,pswd from users where encrypt_username = '" + client_username +"';");

			if(rs.next())
			{


				if(rs.getString(4).equals(Cryption.decrypt(client_password, rs.getString(4))))
				{
					lresponse.setAuthstatus("Y");
					lresponse.setName(rs.getString(1));
					lresponse.setUsertype(rs.getString(2));
					lresponse.setToken(rs.getString(3));
					
					 String key1 = Cryption.getNewKey();
					 String key2 = Cryption.getNewKey();
					 String time = String.valueOf(System.currentTimeMillis());
					 String myToken = lresponse.getName() + ";;" + key1 + ";;" + time;
					
					 lresponse.setToken(Cryption.encrypt(myToken,key2));
					 lresponse.setKey1(key1);
					 String query = "update users set key1 = ?, key2 = ?, token = ? where encrypt_username = '" + client_username +"';  ";
				        PreparedStatement preparedStmt = conn.prepareStatement(query);
				        preparedStmt.setString (1, key1);
				        preparedStmt.setString (2, key2);
				        preparedStmt.setString (3, lresponse.getToken());
				        preparedStmt.execute();
					return lresponse.getJsonString();
				}


			}


			lresponse.setAuthstatus("N");


			conn.close();  
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
	public void file_read(String param1) throws IOException {
		System.out.println("Execution of File read start here!");

		Request_Login lr = new Request_Login();
		lr = lr.getClassFromJson(param1);
		String filename = lr.getFilename();
		System.out.println("filename entered is"+filename);
		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {

			stream.forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@POST
	@Consumes({"application/json"})
	@Path("/fileWrite")
	public void file_write() {


	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/tokenCheck")
	public String tokenCheck(String param){
		
		Request_Token tr = new Request_Token();
		tr = tr.getClassFromJson(param);
		
		String client_username = tr.getEncryptedUsername();

		String client_token = tr.getToken();
		
		System.out.println("username is "+client_username);
		System.out.println("token is "+client_token);

		Response_Token tresponse = new Response_Token();
	
				
		
		Connection conn = ConnectionDao.sqlconnect();
		try {
		Statement stmt=conn.createStatement();  
			
			ResultSet rs=stmt.executeQuery("select token,key2,key1 from users where token = '" + client_token +"' and encrypt_username = '" + client_username +"';");
		
		
		if(rs.next())
		{
			client_token = Cryption.decrypt(client_token, rs.getString(2));
			StringTokenizer st = new StringTokenizer(client_token,";;");
			String ds_ttl = new String();
			String ttl = new String();
		     while (st.hasMoreTokens()) {  
		    	     ds_ttl = st.nextToken();  
		     }
		     long a = Long.parseLong(ds_ttl);
		     System.out.println(""+a);
		     long b = System.currentTimeMillis();
		    
		     if(b - a <= 300000)
		     {
		    	tresponse.setAuthstatus("Y");
		 		tresponse.setKey1(rs.getString(3));
		 		return tresponse.getJsonString();
		     }
			
				
		}			
		
		tresponse.setAuthstatus("N");
		
		return tresponse.getJsonString();	
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		tresponse.setAuthstatus("N");
		return tresponse.getJsonString();


	}	


}








