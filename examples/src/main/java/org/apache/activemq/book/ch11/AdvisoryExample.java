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
import org.apache.activemq.command.*;
import javax.jms.Message;
import javax.jms.*;
/**
 * A AdvisoryExample
 * 
 */
public class AdvisoryExample {
    public void listenToConnectionAdvisories() throws JMSException {
        // listen for Connections
        String brokerURI = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURI);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic connectionAdvisory = org.apache.activemq.advisory.AdvisorySupport.CONNECTION_ADVISORY_TOPIC;
        MessageConsumer consumer = session.createConsumer(connectionAdvisory);
        ActiveMQMessage message = (ActiveMQMessage) consumer.receive();
        DataStructure data = (DataStructure) message.getDataStructure();
        if (data.getDataStructureType() == ConnectionInfo.DATA_STRUCTURE_TYPE) {
            ConnectionInfo connectionInfo = (ConnectionInfo) data;
            System.out.println("Connection started: " + connectionInfo);
        } else if (data.getDataStructureType() == RemoveInfo.DATA_STRUCTURE_TYPE) {
            RemoveInfo removeInfo = (RemoveInfo) data;
            System.out.println("Connection stopped: " + removeInfo.getObjectId());
        } else {
            System.err.println("Unknown message " + data);
        }
    }

    public void listenToConsumerAdvisories() throws JMSException {
        // listen for Consumers
        String brokerURI = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURI);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // Lets first create a Consumer to listen too
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // Lets first create a Consumer to listen too
        Queue queue = session.createQueue("test.Queue");
        MessageConsumer testConsumer = session.createConsumer(queue);
        // so lets listen for the Consumer starting/stoping
        Topic advisoryTopic = org.apache.activemq.advisory.AdvisorySupport.getConsumerAdvisoryTopic(queue);
        MessageConsumer consumer = session.createConsumer(advisoryTopic);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message m) {
                ActiveMQMessage message = (ActiveMQMessage) m;
                try {
                    System.out.println("Consumer Count = " + m.getStringProperty("consumerCount"));
                    DataStructure data = (DataStructure) message.getDataStructure();
                    if (data.getDataStructureType() == ConsumerInfo.DATA_STRUCTURE_TYPE) {
                        ConsumerInfo consumerInfo = (ConsumerInfo) data;
                        System.out.println("Consumer started: " + consumerInfo);
                    } else if (data.getDataStructureType() == RemoveInfo.DATA_STRUCTURE_TYPE) {
                        RemoveInfo removeInfo = (RemoveInfo) data;
                        System.out.println("Consumer stopped: " + removeInfo.getObjectId());
                    } else {
                        System.err.println("Unknown message " + data);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        testConsumer.close();
    }
}
