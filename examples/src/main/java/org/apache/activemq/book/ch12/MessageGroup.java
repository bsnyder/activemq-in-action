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
package org.apache.activemq.book.ch12;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
/**
 * A MessageGroup
 * 
 */
public class MessageGroup {
    /**
     * Example to create a message group
     * 
     * @throws JMSException
     */
    public void createAGroup() throws JMSException {
        Connection connection = new ActiveMQConnectionFactory().createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("group.queue");
        MessageProducer producer = session.createProducer(queue);
        Message message = session.createTextMessage("<foo>test</foo>");
        message.setStringProperty("JMSXGroupID", "TEST_GROUP_A");
        producer.send(message);
    }

    public void closeAGroup() throws JMSException {
        Connection connection = new ActiveMQConnectionFactory().createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("group.queue");
        MessageProducer producer = session.createProducer(queue);
        Message message = session.createTextMessage("<foo>close</foo>");
        message.setStringProperty("JMSXGroupID", "TEST_GROUP_A");
        message.setIntProperty("JMSXGroupSeq", -1);
        producer.send(message);
    }

    public void checkNewGroupMessage() throws JMSException {
        Connection connection = new ActiveMQConnectionFactory().createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("group.queue");
        MessageConsumer consumer = session.createConsumer(queue);
        Message message = consumer.receive();
        String groupId = message.getStringProperty("JMSXGroupId");
        if (message.getBooleanProperty("JMSXGroupFirstForConsumer")) {
            // do processing for new group
        }
    }
}
