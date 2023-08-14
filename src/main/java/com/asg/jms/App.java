package com.asg.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");

		InitialContext context = new InitialContext();
		// Queue queue = (Queue) context.lookup("queue/queue01");

		// try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
		// JMSContext jmsContext = cf.createContext()){
		// jmsContext.createProducer().send(queue,"Arise Awake and stop not till the
		// goal is reached");
		// String messageReceived =
		// jmsContext.createConsumer(queue).receiveBody(String.class);
		// System.out.println(messageReceived);
		// }
		ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = cf.createConnection("admin", "admin");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// Queue destination = (Queue) context.lookup("queue/queue01");
		Topic destination = (Topic) context.lookup("topic/topic01");

		// MessageProducer proceducer = session.createProducer(destination);
		// TextMessage msg = session.createTextMessage("I am the creator of my	destination");
		// proceducer.send(msg);

		// proceducer.close();
		MessageConsumer consumer = session.createConsumer(destination);
		connection.start();

		while (true) {
			TextMessage msg01 = (TextMessage) consumer.receive(50000);
			if (msg01 != null) {
				if (msg01.getText().equalsIgnoreCase("END"))
					break;
				System.out.println(msg01.getText());
			}
		}

		consumer.close();
		session.close();
		connection.close();

	}
}
