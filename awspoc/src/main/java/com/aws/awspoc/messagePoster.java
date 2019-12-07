package com.aws.awspoc;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.solacesystems.jms.SupportedProperty;

public class messagePoster {
	
	final String TOPIC_NAME = "demoT/level1";
	 final String CONNECTION_FACTORY_JNDI_NAME = "/jms/cf/default";
   String host = "tcp://mr1qvxdm3zqyo3.messaging.solace.cloud:20800";
   String vpnName = "msgvpn-20ta6idome7l";
   
   String username = "demoUser";
   String password = "test";
   
   
   

public  void sendTextMessage (String textMessage) throws Exception
{
	System.out.printf("Topic Message Producer is connecting to Solace messaging at %s...%n", host);
   Hashtable<String, Object> env = new Hashtable<String, Object>();
   env.put(InitialContext.INITIAL_CONTEXT_FACTORY, "com.solacesystems.jndi.SolJNDIInitialContextFactory");
   env.put(InitialContext.PROVIDER_URL, host);
   env.put(Context.SECURITY_PRINCIPAL, username + '@' + vpnName); // Formatted as user@message-vpn
   env.put(Context.SECURITY_CREDENTIALS, password);
   
   
   //SSL Scheme of connection.
   env.put(SupportedProperty.SOLACE_JMS_AUTHENTICATION_SCHEME, SupportedProperty.AUTHENTICATION_SCHEME_BASIC );
  
   /* env.put(SupportedProperty.SOLACE_JMS_SSL_TRUST_STORE, "C:\\srinath\\SpringSTS\\solaceCerts\\cert\\my-cacerts");
   env.put(SupportedProperty.SOLACE_JMS_SSL_TRUST_STORE_FORMAT, "jks");
   env.put(SupportedProperty.SOLACE_JMS_SSL_TRUST_STORE_PASSWORD, ("changeit"));
   env.put(SupportedProperty.SOLACE_JMS_SSL_KEY_STORE, "C:\\srinath\\SpringSTS\\solaceCerts\\cert\\app-test-user.pfx");
   env.put(SupportedProperty.SOLACE_JMS_SSL_KEY_STORE_FORMAT, "pkcs12");  
   env.put(SupportedProperty.SOLACE_JMS_SSL_KEY_STORE_PASSWORD, "changeit"); */

   
   
   
   
   //Create a new COntext 
   
   InitialContext initialContext = new InitialContext(env);
 
   ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup(CONNECTION_FACTORY_JNDI_NAME);

   Connection connection = connectionFactory.createConnection();
   Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

   System.out.printf("***********Connected to the Solace Message VPN '%s' with client username '%s'.%n", vpnName,
           username);

   // Lookup the queue.
   Topic topic = (Topic) initialContext.lookup(TOPIC_NAME);

   // Create the message producer for the created queue
   MessageProducer messageProducer = session.createProducer(topic);

   // Create a text message.
   TextMessage message = session.createTextMessage(textMessage);

   System.out.printf("Sending message '%s' to queue '%s'...%n", message.getText(), topic.toString());

   // Send the message
   // NOTE: JMS Message Priority is not supported by the Solace Message Bus
   messageProducer.send(topic, message, DeliveryMode.PERSISTENT, Message.DEFAULT_PRIORITY,
           Message.DEFAULT_TIME_TO_LIVE);

   System.out.println("Sent successfully. Exiting...");

   // Close everything in the order reversed from the opening order
   // NOTE: as the interfaces below extend AutoCloseable,
   // with them it's possible to use the "try-with-resources" Java statement
   // see details at https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
   messageProducer.close();
   session.close();
   connection.close();
   // The initial context needs to be close; it does not extend AutoCloseable
   initialContext.close();
}
}
