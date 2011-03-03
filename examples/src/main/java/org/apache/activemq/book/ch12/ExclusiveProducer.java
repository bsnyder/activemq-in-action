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

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
/**
 * A ExclusiveProducer This example uses an exclusive consumer to ensure only one
 * of these is active at a time.
 * 
 * The event of consuming a message from a Queue identifies this as being the
 * selected producer - to read from a real time data feed and send the data over
 * JMS
 * 
 */
public class ExclusiveProducer implements MessageListener {
    private final ConnectionFactory factory;
    private final String queueName;
    private Connection connection;
    private Session session;
    private boolean active;

    /**
     * Constructor
     * @param f
     * @param name
     */
    public ExclusiveProducer(ConnectionFactory f, String name) {
        this.factory = f;
        this.queueName = name;
        
    }

    /**
     * @throws JMSException
     */
    public void start() throws JMSException {
        this.connection = this.factory.createConnection();
        this.connection.start();
        this.session = this.connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = this.session.createQueue(this.queueName + "?consumer.exclusive=true");
        Message message = this.session.createMessage();
        MessageProducer producer = this.session.createProducer(destination);
        producer.send(message);
        MessageConsumer consumer = this.session.createConsumer(destination);
        consumer.setMessageListener(this);
    }

    /**
     * @throws JMSException
     */
    public void stop() throws JMSException {
        if (this.connection != null) {
            this.connection.stop();
        }
    }

    /**
     * this will start reading real-time data feed
     */
    public void startProducing() {
        // start reading the real-time data feed and start sending ...
    }

    /** 
     * @param message
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message message) {
        if (message != null && this.active==false) {
            this.active=true;
            startProducing();
        }
    }
}
