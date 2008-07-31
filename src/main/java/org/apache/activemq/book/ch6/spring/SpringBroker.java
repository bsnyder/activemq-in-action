package org.apache.activemq.book.ch6.spring;

import org.springframework.context.support.FileSystemXmlApplicationContext;


public class SpringBroker {

	//Spring 1.0
	static String broker = "src/main/resources/org/apache/activemq/book/ch7/spring-1.0.xml";
	//Spring + XBean
	//static String broker = "src/main/resources/org/apache/activemq/book/ch5/activemq-simple.xml";
	//Spring 2.0
	//static String broker = "src/main/resources/org/apache/activemq/book/ch7/spring-2.0.xml";
	
	public static void main(String[] args) {
		System.setProperty("activemq.base", System.getProperty("user.dir"));
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(broker);
	}

}
