package com.dyp.dashboard.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {
	
	private static Logger logger = LoggerFactory.getLogger(IOUtils.class);
	
	public static String convertStreamToString(InputStream is) {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    try {
	      while ((line = reader.readLine()) != null) {
	        sb.append(line + "\n");
	      }
	    } catch (IOException e) {
	      logger.error("convert stream to string error!",e);
	    } finally {
	      try {
	        is.close();
	      } catch (IOException e) {
	        logger.error("convert stream to string close error!",e);
	      }
	    }
	    return sb.toString();
	  }
}
