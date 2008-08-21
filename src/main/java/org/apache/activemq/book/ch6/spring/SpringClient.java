package org.apache.activemq.book.ch6.spring;

import org.apache.xbean.spring.context.FileSystemXmlApplicationContext;


public class SpringClient {

	public static void main(String[] args) {
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/org/apache/activemq/book/ch6/spring-client.xml");
		SpringPublisher publisher = (SpringPublisher)context.getBean("stockPublisher");
		publisher.start();
	}

}
