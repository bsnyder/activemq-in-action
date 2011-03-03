/***************************************
 *                                     *
 *  JBoss: The OpenSource J2EE WebOS   *
 *                                     *
 *  Distributable under LGPL license.  *
 *  See terms of license at gnu.org.   *
 *                                     *
 ***************************************/
package org.jboss.repositoryselectorexample;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RepositorySelector;
import org.apache.log4j.spi.RootLogger;
import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Document;

/**
 * This RepositorySelector is for use with web applications. It assumes that
 * your log4j.xml file is in the WEB-INF directory.
 * 
 * @author Stan Silvert
 */
public class AppRespositorySelector implements RepositorySelector {
    private static boolean initialized = false;
    private static Object guard = LogManager.getRootLogger();
    private static Map repositories = new HashMap();
    private static LoggerRepository defaultRepository;

    public static synchronized void init(ServletConfig servletConfig)
            throws ServletException {
        init(servletConfig.getServletContext());
    }

    public static synchronized void init(ServletContext servletContext)
            throws ServletException {
        if (!initialized) // set the global RepositorySelector
        {
            defaultRepository = LogManager.getLoggerRepository();
            RepositorySelector theSelector = new AppRespositorySelector();
            LogManager.setRepositorySelector(theSelector, guard);
            initialized = true;
        }

        Hierarchy hierarchy = new Hierarchy(new RootLogger(Level.DEBUG));
        loadLog4JConfig(servletContext, hierarchy);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        repositories.put(loader, hierarchy);
    }

    public static synchronized void removeFromRepository() {
        repositories.remove(Thread.currentThread().getContextClassLoader());
    }

    // load log4j.xml from WEB-INF
    private static void loadLog4JConfig(ServletContext servletContext,
            Hierarchy hierarchy) throws ServletException {
        try {
            String log4jFile = "/WEB-INF/log4j.xml";
            InputStream log4JConfig = servletContext.getResourceAsStream(log4jFile);
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(log4JConfig);
            DOMConfigurator conf = new DOMConfigurator();
            conf.doConfigure(doc.getDocumentElement(), hierarchy);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private AppRespositorySelector() {
    }

    public LoggerRepository getLoggerRepository() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        LoggerRepository repository = (LoggerRepository) repositories.get(loader);
        if (repository == null) {
            return defaultRepository;
        } else {
            return repository;
        }
    }
}
