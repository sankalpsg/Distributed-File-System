package clientserver;

import java.util.HashMap;

public class CachingMemory 
{

	public static HashMap<String, String> cacheStore = new HashMap<String, String>();// File Name and File Content
	public static HashMap<String, Long> cacheStoreTime = new HashMap<String, Long>();
}