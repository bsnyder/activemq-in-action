package org.apache.activemq.book.ch14.jmx;

import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		JMXServiceURL url = new JMXServiceURL(
				"service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
		JMXConnector connector = JMXConnectorFactory.connect(url, null);
		connector.connect();
		MBeanServerConnection connection = connector.getMBeanServerConnection();
		String brokerName = "localhost";
		String connectionName = "ID_dejan-bosanacs-macbook-pro.local-50462-1236596709533-3_0";
		ObjectName query = new ObjectName("org.apache.activemq:BrokerName="
				+ brokerName + ",Type=Connection,*,Connection="
				+ connectionName);
		Set<ObjectName> queryResult = connection.queryNames(query, null);
		System.out.println(queryResult);
	}

}
