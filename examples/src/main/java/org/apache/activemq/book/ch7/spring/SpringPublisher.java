package org.apache.activemq.book.ch7.spring;

import java.util.HashMap;

import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;


public class SpringPublisher {
	
    private JmsTemplate template;
    private int count = 10;
    private int total;
    private Destination[] destinations;
    private HashMap<Destination,StockMessageCreator> creators = new HashMap<Destination,StockMessageCreator>(); 
    
    public void start() {
        while (total < 1000) {
            for (int i = 0; i < count; i++) {
                sendMessage();
            }
            total += count;
            System.out.println("Published '" + count + "' of '" + total + "' price messages");
            try {
              Thread.sleep(1000);
            } catch (InterruptedException x) {
            }
          }	
    }
    
    protected void sendMessage() {
        int idx = 0;
        while (true) {
            idx = (int)Math.round(destinations.length * Math.random());
            if (idx < destinations.length) {
                break;
            }
        }
        Destination destination = destinations[idx];
        template.send(destination, getStockMessageCreator(destination));
    }
    
    private StockMessageCreator getStockMessageCreator(Destination dest) {
    	if (creators.containsKey(dest)) {
    		return creators.get(dest);
    	} else {
    		StockMessageCreator creator = new StockMessageCreator(dest);
    		creators.put(dest, creator);
    		return creator;
    	}
    }

	public JmsTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public Destination[] getDestinations() {
		return destinations;
	}

	public void setDestinations(Destination[] destinations) {
		this.destinations = destinations;
	}
    

	
}
