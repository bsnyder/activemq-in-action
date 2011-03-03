package org.apache.activemq.book.ch8.jms.delegate;

import org.apache.log4j.Logger;

/**
 * 
 * @author bsnyder
 *
 */
public class JmsMessageDelegate {

    private static final Logger LOG = Logger.getLogger(JmsMessageDelegate.class);
    
	public void handleMessage(String message) {
        LOG.info("Consumed message with payload: " + message);
	}

}
