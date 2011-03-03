package org.apache.activemq.book.ch14.advisory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.CommandTypes;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.RemoveInfo;

public class Advisory {

    protected static String brokerURL = "tcp://localhost:61616";
    protected static transient ConnectionFactory factory;
    protected transient Connection connection;
    protected transient Session session;
    
    public Advisory() throws Exception {
    	factory = new ActiveMQConnectionFactory(brokerURL);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
	public static void main(String[] args) throws Exception {
		Advisory advisory = new Advisory();
		Session session = advisory.getSession();
    	for (String stock : args) {
    		
    		ActiveMQDestination destination = (ActiveMQDestination)session.createTopic("STOCKS." + stock);
    		
    		Destination consumerTopic = AdvisorySupport.getConsumerAdvisoryTopic(destination);
    		System.out.println("Subscribing to advisory " + consumerTopic);
    		MessageConsumer consumerAdvisory = session.createConsumer(consumerTopic);
    		consumerAdvisory.setMessageListener(new ConsumerAdvisoryListener());
    		
    		Destination noConsumerTopic = AdvisorySupport.getNoTopicConsumersAdvisoryTopic(destination);
    		System.out.println("Subscribing to advisory " + noConsumerTopic);
    		MessageConsumer noConsumerAdvisory = session.createConsumer(noConsumerTopic);
    		noConsumerAdvisory.setMessageListener(new NoConsumerAdvisoryListener());
    		
    	}
	}

	public Session getSession() {
		return session;
	}
	
}
