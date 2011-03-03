package org.apache.activemq.book.ch7.xbean;

import java.net.URI;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class Factory {

    public static void main(String[] args) throws Exception {
        System.setProperty("activemq.base", System.getProperty("user.dir"));
        BrokerService broker = BrokerFactory.createBroker(
                new URI("xbean:target/classes/org/apache/activemq/book/ch7/activemq-simple.xml"));
        broker.start();

        System.out.println();
        System.out.println("Press any key to stop the broker");
        System.out.println();

        System.in.read();
    }

}
