package distributedFTS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDao {
	
	public static Connection sqlconnect() {
		Connection con= null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
	
		 con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/fileserver","root","Prashant123!");
	} 
	
	catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return con;
	}

}
