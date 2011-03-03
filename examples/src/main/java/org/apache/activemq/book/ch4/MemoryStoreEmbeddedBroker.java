package org.apache.activemq.book.ch4;

import org.apache.activemq.broker.BrokerService;

public class MemoryStoreEmbeddedBroker {
    
    public void createEmbeddedBroker() throws Exception {
        
        BrokerService broker = new BrokerService();
        //configure the broker to use the Memory Store
        broker.setPersistent(false);
          
        //Add a transport connector
        broker.addConnector("tcp://localhost:61616");

        //now start the broker
        broker.start();
      }
}
