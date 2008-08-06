package org.apache.activemq.book.ch6.spring;

import org.apache.activemq.book.ch5.Publisher;
import org.apache.xbean.spring.context.FileSystemXmlApplicationContext;


public class SpringBroker {

	public static void main(String[] args) throws Exception {
    	if (args.length == 0) {
    		System.err.println("Please define a configuration file!");
    		return;
    	}
    	String brokerUrl = args[0];
    	System.out.println("Starting broker with the following configuration: " + brokerUrl);
		System.setProperty("activemq.base", System.getProperty("user.dir"));
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(brokerUrl);
		
		Publisher publisher = new Publisher();
		for (int i = 0; i < 100; i++) {
			publisher.sendMessage(new String[]{"JAVA", "IONA"});
		}
		
	}

}
