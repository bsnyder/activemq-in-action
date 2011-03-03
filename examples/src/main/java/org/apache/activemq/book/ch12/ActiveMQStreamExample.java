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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * Examples of how to use JMS Streams
 *
 */
public class ActiveMQStreamExample {
    private static final String QUEUE_NAME = "test.stream.queue";
    
    public void writeAStream() throws Exception{
        //source of our large data
        FileInputStream in = new FileInputStream("largetextfile.txt");
        
        String brokerURI = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURI);
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(QUEUE_NAME);
        OutputStream out = connection.createOutputStream(destination);
        
        //now write the file on to ActiveMQ
        byte[] buffer = new byte[1024];
        while(true){
            int bytesRead = in.read(buffer);
            if (bytesRead==-1){
                break;
            }
            out.write(buffer,0,bytesRead);
        }
        out.close();
    }
    
    public void readAStream() throws Exception{
        //destination of our large data
        FileOutputStream out = new FileOutputStream("copied.txt");
        
        String brokerURI = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURI);
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //we want be be an exclusive consumer
        String exclusiveQueueName= QUEUE_NAME + "?consumer.exclusive=true";
        Queue destination = session.createQueue(exclusiveQueueName);
        
        InputStream in = connection.createInputStream(destination);
        
        //now write the file from ActiveMQ
        byte[] buffer = new byte[1024];
        while(true){
            int bytesRead = in.read(buffer);
            if (bytesRead==-1){
                break;
            }
            out.write(buffer,0,bytesRead);
        }
        out.close();
    }
}
