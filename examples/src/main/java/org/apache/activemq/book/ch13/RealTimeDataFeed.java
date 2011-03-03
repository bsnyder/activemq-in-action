package org.apache.activemq.book.ch13;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;

public class RealTimeDataFeed {
    
    public void startBroker() throws Exception{
       //By default a broker always listens on vm://<broker name>
        BrokerService broker = new BrokerService();
        broker.setBrokerName("fast");
        broker.getSystemUsage().getMemoryUsage().setLimit(64*1024*1024);
        //Set the Destination policies
        PolicyEntry policy = new PolicyEntry();
        //set a memory limit of 4mb for each destination
        policy.setMemoryLimit(4 * 1024 *1024);
        //disable flow control
        policy.setProducerFlowControl(false);

        PolicyMap pMap = new PolicyMap();
        //configure the policy
        pMap.setDefaultEntry(policy);

        broker.setDestinationPolicy(pMap);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }
    
    public void dataFeed() throws Exception{
                
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://fast");
        cf.setCopyMessageOnSend(false);
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test.topic");
        final MessageProducer producer = session.createProducer(topic);
        //send non-persistent messages
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        for (int i =0; i < 1000000;i++) {
            TextMessage message = session.createTextMessage("Test:"+i);
            producer.send(message);  
        }
        System.err.println("SENT MESSAGES");
        
    }
    
    public void consumer() throws Exception{
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("failover://(tcp://localhost:61616)");
        cf.setAlwaysSessionAsync(false);
        cf.setOptimizeAcknowledge(true);
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test.topic?consumer.prefetchSize=32766");
        MessageConsumer consumer = session.createConsumer(topic);
        //setup a counter - so we don't print every message
        final AtomicInteger count = new AtomicInteger();
        consumer.setMessageListener(new MessageListener() {

            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage)message;
                try {
                    if (count.incrementAndGet()%10000==0)
                   System.err.println("Got = " + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                
            }
            
        });
        
    }
    
    public static void main(String[] args) throws Exception {
        RealTimeDataFeed rtdf = new RealTimeDataFeed();
        rtdf.startBroker();
        rtdf.consumer();
        rtdf.dataFeed();
       
        Thread.sleep(5000);
    }
}
