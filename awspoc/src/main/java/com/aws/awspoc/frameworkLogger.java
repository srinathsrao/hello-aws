package com.aws.awspoc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 

public class frameworkLogger {
	
	private static final Logger LOGGER = LogManager.getLogger("awsFileAppender");
	public static void  debug_logToFile(String logMessage)
	{
		 LOGGER.debug("Debug Message Logged !!!" + logMessage  );
	}

}
