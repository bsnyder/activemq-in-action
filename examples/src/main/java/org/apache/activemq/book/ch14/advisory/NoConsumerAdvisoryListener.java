package org.apache.activemq.book.ch14.advisory;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMapMessage;

public class NoConsumerAdvisoryListener implements MessageListener {

	public void onMessage(Message message) {
		try {
			System.out.println("Message " + ((ActiveMQMapMessage)message).getContentMap() + " not consumed by any consumer");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
