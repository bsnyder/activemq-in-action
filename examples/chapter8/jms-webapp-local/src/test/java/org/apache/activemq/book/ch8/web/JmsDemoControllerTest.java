package org.apache.activemq.book.ch8.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.activemq.book.ch8.jms.web.controller.JmsMessageSenderController;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;


public class JmsDemoControllerTest {

	@Test
	public void testHandleRequestView() throws Exception{		
		/*
        JmsDemoController controller = new JmsDemoController();
        ModelAndView modelAndView = controller.handleRequest(null, null);		
        assertEquals("index", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        String nowValue = (String) modelAndView.getModel().get("now");
        assertNotNull(nowValue);
        */
    }
}
