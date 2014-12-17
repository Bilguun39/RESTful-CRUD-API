package backendAssignment02A.bilguun.sipree.service.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import backendAssignment02A.bilguun.sipree.service.utils.Constants;

public class LoggingApi {
	
	private Logger logger = null;
	
	public static LoggingApi instance;
	
	private LoggingApi(){
		
	}
	
	public static LoggingApi getInstance(){
		if(instance == null){
			instance = new LoggingApi();
			instance.init();
		}
		
		return instance;
	}
	
	private void init(){
		if(logger == null)
			logger = Logger.getLogger(Constants.LOGGER_NAME);
			FileHandler fh = null;
			
			String userHome = System.getProperty("user.home");
			
			try {
				fh = new FileHandler(userHome + "/" + Constants.LOG_FILE_NAME);  
		        logger.addHandler(fh);
		        
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fh.setFormatter(formatter);
				
			} catch (SecurityException | IOException e) {
				e.printStackTrace();
			} finally{
			}
			
	}
	
	public Logger getLogger(){
		return logger;
	}
}
