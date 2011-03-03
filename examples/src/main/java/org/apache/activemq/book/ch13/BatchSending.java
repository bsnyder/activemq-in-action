package org.apache.activemq.book.ch13;

import java.util.Properties;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;

public class BatchSending {
    
    public void setOptimizeAcknowledge() throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setOptimizeAcknowledge(true);
    }
    public void setSessionAsyncOff() throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setAlwaysSessionAsync(false);
    }
    
    public void setNoCopyOnSend() throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setAlwaysSyncSend(true);
    }
    
    public void setAsyncSend() throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setCopyMessageOnSend(false);
    }
    public void setDeliveryMode() throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("Test.Transactions");
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }
    public void sendTransacted() throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
        Topic topic = session.createTopic("Test.Transactions");
        MessageProducer producer = session.createProducer(topic);
        int count =0;
        for (int i =0; i < 1000; i++) {
           Message message = session.createTextMessage("message " + i);
           producer.send(message);
           if (i!=0 && i%10==0){
              session.commit();
           }
        }
    }
    public void setPrefetchProperties() throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        Properties props = new Properties();
        props.setProperty("prefetchPolicy.queuePrefetch", "1000");
        props.setProperty("prefetchPolicy.queueBrowserPrefetch", "500");
        props.setProperty("prefetchPolicy.topicPrefetch", "60000");
        props.setProperty("prefetchPolicy.durableTopicPrefetch", "100");
        cf.setProperties(props);
       
    }

    public void sendNonTransacted() throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("Test.Transactions");
        MessageProducer producer = session.createProducer(topic);
        int count =0;
        for (int i =0; i < 1000; i++) {
           Message message = session.createTextMessage("message " + i);
           producer.send(message);
        }
    }
}
