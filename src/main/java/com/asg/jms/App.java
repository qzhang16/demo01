package com.asg.jms;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println("Hello World!");
        
        InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/queue01");
		
		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()){
			jmsContext.createProducer().send(queue,"Arise Awake and stop not till the goal is reached");
			String messageReceived = jmsContext.createConsumer(queue).receiveBody(String.class);
			System.out.println(messageReceived);
		}
    }
}
