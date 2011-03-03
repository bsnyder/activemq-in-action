/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.book.ch11;


import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

/**
 * A CamelExample
 *
 */
public class CamelExample {
	private BrokerService broker;
	
	public void startBroker() throws Exception{
		 System.setProperty("activemq.base", "target");
         // new File("target/data").mkdirs();
         broker = BrokerFactory.createBroker("xbean:src/main/resources/" + "org/apache/activemq/book/ch11/broker.xml");
		
	}
	
	public void startConsumer() throws Exception{
		 ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover://(tcp://localhost:61616)");
	       
	        Connection connection = cf.createConnection();
	        connection.start();
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Queue queue = session.createQueue("Test.Queue");
	        MessageConsumer consumer = session.createConsumer(queue);
	        
	        consumer.setMessageListener(new MessageListener() {

	            public void onMessage(Message message) {
	              
	                try {
	                    
	                   System.err.println("Got = " + message);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                
	            }
	            
	        });
	}
	
	public void startStatsConsumer() throws Exception{
		 ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover://(tcp://localhost:61616)");
	       
	        Connection connection = cf.createConnection();
	        connection.start();
	        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        Topic topic = session.createTopic("Statistics.Topic");
	        MessageConsumer consumer = session.createConsumer(topic);
	        
	        consumer.setMessageListener(new MessageListener() {

	            public void onMessage(Message message) {
	              
	                try {
	                    
	                   System.err.println("STATS Got = " + message);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                
	            }
	            
	        });
	}
	
	public void startProducer() throws Exception {
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover://(tcp://localhost:61616)");
	       
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("Test.Topic");
        MessageProducer producer = session.createProducer(topic);
        for (int i = 0; i < 0; i++){
        	TextMessage msg = session.createTextMessage("test:"+i);
        	producer.send(msg);
        }
	}
	
	public static void main(String[] args) throws Exception {
		CamelExample se = new CamelExample();
		se.startBroker();
		se.startConsumer();
		se.startStatsConsumer();
		se.startProducer();
		Thread.sleep(1000);
	}
 
}
