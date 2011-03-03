SpringBroker demonstrates how to configure broker using Spring 1.0, XBean and Spring 2.0 configuration syntax.

For Spring 1.0 example run:

 mvn exec:java -Dexec.mainClass=org.apache.activemq.book.ch6.spring.SpringBroker -Dlog4j.configuration=file:src/main/java/log4j.properties  -Dexec.args="src/main/resources/org/apache/activemq/book/ch6/spring-1.0.xml"

For Spring 2.0 example run: 

 mvn exec:java -Dexec.mainClass=org.apache.activemq.book.ch6.spring.SpringBroker -Dlog4j.configuration=file:src/main/java/log4j.properties  -Dexec.args="src/main/resources/org/apache/activemq/book/ch6/spring-2.0.xml"
 
For XBean example run:

 mvn exec:java -Dexec.mainClass=org.apache.activemq.book.ch6.spring.SpringBroker -Dlog4j.configuration=file:src/main/java/log4j.properties  -Dexec.args="src/main/resources/org/apache/activemq/book/ch5/activemq-simple.xml"
 
 
SpringClient demonstrates how to use Spring JMS support to connect to the ActiveMQ broker. Run it with:

 mvn exec:java -Dexec.mainClass=org.apache.activemq.book.ch6.spring.SpringClient
