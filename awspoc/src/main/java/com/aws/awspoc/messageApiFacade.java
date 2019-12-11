package com.aws.awspoc;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan
@EnableAutoConfiguration
public class messageApiFacade {
	
	
	@PostMapping("/message")
    public String  postSolaceMessage(@RequestBody String solaceMessage)  
    {
    	
        System.out.println( (String) solaceMessage);
        frameworkLogger.debug_logToFile(solaceMessage);
        messagePoster objPoster= new messagePoster();
        try 
        {
        	
        	System.out.println("Entering inside the block");
			objPoster.sendTextMessage(solaceMessage);
			
		} 
        
        
        catch (Exception e)
        
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return "Thank you !";
    }

}
