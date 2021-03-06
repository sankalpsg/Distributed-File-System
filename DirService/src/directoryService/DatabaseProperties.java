package directoryService;

import java.io.IOException;
import java.util.Properties;

public  class DatabaseProperties 
{
	public static String serverUrl;
	public static String authcheckUrl;
	public static String dbusername;
	public static String dbpassword;
	public static String dbip;
	public static String dbport;
	public static String dbname;
	public static void loadProperties() 
	{
		Properties config = new Properties();
		try 
		{
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			serverUrl = config.getProperty("serverUrl");
			authcheckUrl = config.getProperty("authcheckUrl");
			dbusername = config.getProperty("dbusername");
			dbpassword = config.getProperty("dbpassword");
			dbip = config.getProperty("dbip");
			dbport = config.getProperty("dbport");
			dbname = config.getProperty("dbname");
			
		} 
		
		catch (IOException e) 
		{
			System.out.println("Failed to load property file"+e);
		}
	}
}
