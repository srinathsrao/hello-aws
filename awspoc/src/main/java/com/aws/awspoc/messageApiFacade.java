package com.aws.awspoc;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class messageApiFacade {
	
	
    @PostMapping("/message")
    public void postSolaceMessage(@RequestBody String solaceMessage) throws Exception 
    {
    	
        System.out.println( (String) solaceMessage);
        messagePoster objPoster= new messagePoster();
        objPoster.sendTextMessage(solaceMessage);
        
    }

}
