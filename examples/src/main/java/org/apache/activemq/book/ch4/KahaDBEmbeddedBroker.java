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
package org.apache.activemq.book.ch4;

import java.io.File;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.kahadb.KahaDBStore;

public class KahaDBEmbeddedBroker {

public void createEmbeddedBroker() throws Exception {
        
        BrokerService broker = new BrokerService();
        File dataFileDir = new File("target/amq-in-action/kahadb");

        KahaDBStore kaha = new KahaDBStore();
        kaha.setDirectory(dataFileDir);

        // Using a bigger journal file
        kaha.setJournalMaxFileLength(1024*1204*100);
        
        // small batch means more frequent and smaller writes
        kaha.setIndexWriteBatchSize(100);
        // do the index write in a separate thread
        kaha.setEnableIndexWriteAsync(true);
        
        broker.setPersistenceAdapter(kaha);
        //create a transport connector
        broker.addConnector("tcp://localhost:61616");
        //start the broker
        broker.start();
    }
}
