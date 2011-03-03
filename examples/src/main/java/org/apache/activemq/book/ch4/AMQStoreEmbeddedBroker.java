package org.apache.activemq.book.ch4;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.amq.AMQPersistenceAdapterFactory;

public class AMQStoreEmbeddedBroker {
    
    public void createEmbeddedBroker() throws Exception {
        
        BrokerService broker = new BrokerService();
        //initialize the PersistenceAdaptorFactory
        AMQPersistenceAdapterFactory persistenceFactory = new AMQPersistenceAdapterFactory();
        
        //set some properties on the factory
        persistenceFactory.setMaxFileLength(1024*16);
        persistenceFactory.setPersistentIndex(true);
        broker.setPersistenceFactory(persistenceFactory);
        
        //create a transport connector
        broker.addConnector("tcp://localhost:61616");
        //start the broker
        broker.start();
    }
}
