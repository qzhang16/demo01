package com.asg.jms;

import javax.jms.JMSConsumer;

// import java.util.Enumeration;
// import java.util.concurrent.CountDownLatch;

// import javax.jms.Connection;
// import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
// import javax.jms.JMSException;
// import javax.jms.Message;
// import javax.jms.MessageListener;
// import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
// import javax.jms.QueueBrowser;
// import javax.jms.Session;
// import javax.jms.TextMessage;
// import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");

		InitialContext context = new InitialContext();
		Queue reqQ = (Queue) context.lookup("queue/requestQueue");
		// Queue replyQ = (Queue) context.lookup("queue/replyQueue");
		Queue expiryQueue = (Queue) context.lookup("queue/expiryQueue");

		try( ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616","admin","admin");
		JMSContext jmsContext = cf.createContext()){
			JMSProducer reqProducer = jmsContext.createProducer();
			// producer.setPriority(1);
			// reqProducer.setJMSReplyTo(replyQ);
			reqProducer.setTimeToLive(1000);
			TextMessage msg = jmsContext.createTextMessage("message X110");
			msg.setBooleanProperty("logged", true);
			msg.setStringProperty("userToken", "abc123..");
			// TemporaryQueue replyQ = jmsContext.createTemporaryQueue();
			
			// msg.setJMSReplyTo(replyQ);
			
			// reqProducer.send(reqQ, "message 11");
			reqProducer.send(reqQ, msg);
			// System.out.println(msg.getJMSMessageID() + " : " + msg.getBody(String.class));
		// producer.setPriority(2);
		// producer.send(queue, "message 12");
		// producer.setPriority(3);
		// producer.send(queue, "message 13");
		Thread.sleep(2000);
		JMSConsumer reqC = jmsContext.createConsumer(reqQ);
		Message msg01 = reqC.receive(100);
		// // String msg = reqC.receiveBody(String.class);
		if (msg01 != null) {
			System.out.println(msg01.getJMSMessageID() + " : " + msg01.getBody(String.class));
			System.out.println(msg01.getBody(String.class));
			System.out.println(msg01.getBooleanProperty("logged"));
			System.out.println(msg01.getStringProperty("userToken"));
		} else {
			Message msg10 = jmsContext.createConsumer(expiryQueue).receive(100);
			System.out.println(msg10.getBody(String.class));
			System.out.println(msg10.getBooleanProperty("logged"));
			System.out.println(msg10.getStringProperty("userToken"));
		}
		// Map<String, TextMessage> messages = new HashMap<>();
		// messages.put(msg01.getJMSMessageID(), msg);

		// JMSProducer replyProducer = jmsContext.createProducer();
		// TextMessage msg02 = jmsContext.createTextMessage("Echo: " + msg01.getBody(String.class));
		// msg02.setJMSCorrelationID(msg01.getJMSMessageID());
		// // replyProducer.send(replyQ, "Echo: " + msg);
		// replyProducer.send((Queue) msg01.getJMSReplyTo(), msg02);
		// // replyProducer.send(replyQ, msg02);

		// // JMSConsumer replyC = jmsContext.createConsumer(replyQ);
		// JMSConsumer replyC = jmsContext.createConsumer((Queue) msg01.getJMSReplyTo());
		
		// // // // msg = replyC.receiveBody(String.class);
		// // // System.out.println(replyC.receiveBody(String.class));
		// Message msg03 = replyC.receive(100);
		// System.out.println("Echo: " + msg03.getJMSCorrelationID() + " : " + msg03.getBody(String.class));
		// System.out.println("Original: " + messages.get(msg03.getJMSCorrelationID()).getBody(String.class));



		// for (int i = 0; i < 3; i++) {
		// 	// System.out.println(consumer.receiveBody(String.class));
		// 	System.out.println(consumer.receive(100).getJMSPriority());
		// }

	
		
		// String messageReceived =
		// jmsContext.createConsumer(queue).receiveBody(String.class);
		// System.out.println(messageReceived);
		}
		// final ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
		// Connection connection = cf.createConnection("admin", "admin");
		// Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// final Queue destination = (Queue) context.lookup("queue/queue01");
		// // final Topic destination = (Topic) context.lookup("topic/topic01");

		// MessageProducer proceducer = session.createProducer(destination);
		// TextMessage msg = session.createTextMessage("I am the creator of my destination");
		// proceducer.send(msg);
		// proceducer.send(msg);

		// proceducer.close();

		// final CountDownLatch latch = new CountDownLatch(1);
		// new Thread(new Runnable() {

		// 	@Override
		// 	public void run() throws RuntimeException {
		// 		try {
		// 			Connection connection = cf.createConnection("admin", "admin");
		// 			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 			MessageConsumer consumer = session.createConsumer(destination);
		// 			consumer.setMessageListener(new Subscriber(latch));
		// 			connection.start();

		// 			// while (true) {
		// 			// 	TextMessage msg01 = (TextMessage) consumer.receive(50000);
		// 			// 	if (msg01 != null) {
		// 			// 		if (msg01.getText().equalsIgnoreCase("END"))
		// 			// 			break;
		// 			// 		System.out.println(msg01.getText());
		// 			// 	}
		// 			// }
		// 			latch.await();
		// 			consumer.close();

		// 		} catch (Exception e) {
		// 			throw new RuntimeException();
		// 		}
		// 	}
		// }).start();

		// MessageProducer proceducer = session.createProducer(destination);
		// TextMessage msg = session.createTextMessage("I am the creator of my		destination");
		// proceducer.send(msg);

		// proceducer.close();

		// QueueBrowser qb = session.createBrowser(destination);
		// Enumeration enn = qb.getEnumeration();
		// TextMessage tm = null;
		// // connection.start();
		// // connection.stop();
		// while (enn.hasMoreElements()) {
		// 	tm = (TextMessage) enn.nextElement();
		// 	System.out.println(tm.getText());
		// }

		// session.close();
		// connection.close();

	}
}

// class Subscriber implements MessageListener {
// 	private final CountDownLatch countDownLatch;
// 	public Subscriber(CountDownLatch latch) {
//         countDownLatch = latch;
//     }
// 	@Override
// 	public void onMessage(Message message) {
// 		try {
// 			if (message instanceof TextMessage) {
// 				String text = ((TextMessage) message).getText();
// 				if ("END".equalsIgnoreCase(text)) {
// 					System.out.println("Received END message!");
// 					countDownLatch.countDown();
// 				} else {
// 					System.out.println("Received message:" + text);
// 				}
// 			}
// 		} catch (JMSException e) {
// 			System.out.println("Got a JMS Exception!");
// 		}
// 	}
// }
