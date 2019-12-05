package com.aws.awspoc;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class messageApiFacade {
	
	
    @PostMapping("/message")
    public void postSolaceMessage(String solaceMessage) 
    {
    	
        System.out.println("Solace Message");
        
    }

}
