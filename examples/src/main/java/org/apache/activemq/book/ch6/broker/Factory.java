package org.apache.activemq.book.ch6.broker;

import java.net.URI;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class Factory {

	public static void main(String[] args) throws Exception {
		System.setProperty("activemq.base", System.getProperty("user.dir"));
		BrokerService broker = BrokerFactory.createBroker(new URI("xbean:src/main/resources/org/apache/activemq/book/ch5/activemq-simple.xml"));
		broker.start();
	}

}
