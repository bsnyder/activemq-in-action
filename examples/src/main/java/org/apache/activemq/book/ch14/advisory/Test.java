package org.apache.activemq.book.ch14.advisory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.command.ActiveMQDestination;

public class Test {
    protected static String brokerURL = "tcp://localhost:61616";
    protected static transient ConnectionFactory factory;
    protected transient Connection connection;
    protected transient Session session;
    
    private String jobs[] = new String[]{"suspend", "delete"};
    
    public Test() throws Exception {
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
	public static void main(String[] args) throws Exception {
		Test advisory = new Test();
		Session session = advisory.getSession();
    	for (String job : advisory.jobs) {
    		
    		ActiveMQDestination destination = (ActiveMQDestination)session.createQueue("JOBS." + job);
    		
    		Destination consumerTopic = AdvisorySupport.getConsumerAdvisoryTopic(destination);
    		System.out.println("Subscribing to advisory " + consumerTopic);
    		MessageConsumer consumerAdvisory = session.createConsumer(consumerTopic);
    		consumerAdvisory.setMessageListener(new ConsumerAdvisoryListener());
    		
    		Destination noConsumerTopic = AdvisorySupport.getNoQueueConsumersAdvisoryTopic(destination);
    		System.out.println("Subscribing to advisory " + noConsumerTopic);
    		MessageConsumer noConsumerAdvisory = session.createConsumer(noConsumerTopic);
    		noConsumerAdvisory.setMessageListener(new NoConsumerAdvisoryListener());
    		
    	}
	}

	public Session getSession() {
		return session;
	}
}
