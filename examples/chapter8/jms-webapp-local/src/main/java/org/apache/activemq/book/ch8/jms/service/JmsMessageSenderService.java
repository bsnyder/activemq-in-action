package org.apache.activemq.book.ch8.jms.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.book.ch8.jms.domain.JmsMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @author bsnyder
 * 
 */
public class JmsMessageSenderService {

    private JmsTemplate jmsTemplate;
    
    public void sendMessage(final JmsMessage bean) throws JMSException {

        if (bean.isPersistent()) {
            jmsTemplate.setDeliveryPersistent(bean.isPersistent());
        }

        if (0 != bean.getTimeToLive()) {
            jmsTemplate.setTimeToLive(bean.getTimeToLive());
        }

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(bean.getMessagePayload());
                
                if (bean.getReplyTo() != null && !bean.getReplyTo().equals("")) {
                    ActiveMQQueue replyToQueue = new ActiveMQQueue(bean.getReplyTo());
                    message.setJMSReplyTo(replyToQueue);
                }
                
                return message;
            }
        });
    }
    
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
