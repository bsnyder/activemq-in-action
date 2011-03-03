package org.apache.activemq.book.ch13;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueRequestor;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

public class EmbeddedService {
    
    public void service() throws Exception{
        //By default a broker always listens on vm://<broker name>
        BrokerService broker = new BrokerService();
        broker.setBrokerName("service");
        broker.setPersistent(false);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
        
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("vm://service");
        Connection connection = cf.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //we will need to respond to multiple destinations - so use null
        //as the destination this producer is bound to
        final MessageProducer producer = session.createProducer(null);
        //create a Consumer to listen for requests to service
        Queue queue = session.createQueue("service.queue");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message msg) {
                try {
                    TextMessage textMsg = (TextMessage)msg;
                    String payload = "REPLY: " + textMsg.getText();
                    Destination replyTo;
                    replyTo = msg.getJMSReplyTo();
                    textMsg.clearBody();
                    textMsg.setText(payload);
                    producer.send(replyTo, textMsg);
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        
    }
    
    public void requestor() throws Exception{
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
        QueueConnection connection = cf.createQueueConnection();
        connection.start();
        QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("service.queue");
        QueueRequestor requestor = new QueueRequestor(session,queue);
        for(int i =0; i < 10; i++) {
            TextMessage msg = session.createTextMessage("test msg: " + i);
            TextMessage result = (TextMessage)requestor.request(msg);
            System.err.println("Result = " + result.getText());
        }
    }
    
    public static void main(String[] args) throws Exception {
        EmbeddedService service = new EmbeddedService();
        service.service();
        service.requestor();
        Thread.sleep(5000);
    }
}
