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

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

/**
 * A VirtualTopics
 *
 */
public class VirtualTopics {
    
    public void sendAndConsumeVirtualTopic() throws JMSException {
        String brokerURI = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
        
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURI);
        Connection consumerConnection = connectionFactory.createConnection();
        consumerConnection.start();
        
        Session consumerSessionA = consumerConnection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue consumerAQueue = consumerSessionA.createQueue("Consumer.A.VirtualTopic.orders");
        MessageConsumer consumerA = consumerSessionA.createConsumer(consumerAQueue);
        
        Session consumerSessionB = consumerConnection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue consumerBQueue = consumerSessionB.createQueue("Consumer.B.VirtualTopic.orders");
        MessageConsumer consumerB = consumerSessionB.createConsumer(consumerAQueue);
        
        //setup the sender
        Connection senderConnection = connectionFactory.createConnection();
        senderConnection.start();
        Session senerSession = senderConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic ordersDestination = senerSession.createTopic("VirtualTopic.orders");
        MessageProducer producer = senerSession.createProducer(ordersDestination);
       
    }
}
