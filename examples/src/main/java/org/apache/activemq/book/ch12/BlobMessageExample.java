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

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
/**
 * A BlobMessageExample
 * 
 */
public class BlobMessageExample {
    private static final String QUEUE_NAME = "test.blob.queue";

    public void writeABlob() throws Exception {
        String brokerURI = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURI);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        ActiveMQSession session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(QUEUE_NAME);
        MessageProducer producer = session.createProducer(destination);
        BlobMessage message = session.createBlobMessage(new URL("http://some.shared.site.com"));
        producer.send(message);
    }

    public void readABlob() throws Exception {
        // destination of our Blob data
        FileOutputStream out = new FileOutputStream("blob.txt");
        String brokerURI = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURI);
        Connection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(destination);
        BlobMessage blobMessage = (BlobMessage) consumer.receive();
        InputStream in = blobMessage.getInputStream();
        // now write the file from ActiveMQ
        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = in.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            out.write(buffer, 0, bytesRead);
        }
        out.close();
    }
}
