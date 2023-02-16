package com.epam.testing.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Context listener class. Is used for Logging
 * and I18n initialization
 *
 * @author rom4ik
 */

@WebListener
public class ContextListener implements ServletContextListener {
  private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LOGGER.debug("Servlet context initialization starts");
    ServletContext servletContext = sce.getServletContext();
    initI18N(servletContext);
    LOGGER.debug("Servlet context initialization finished");
  }

  /**
   * Initializes i18n subsystem.
   */
  private void initI18N(ServletContext servletContext) {
    LOGGER.debug("I18N subsystem initialization started");

    String localesValue = servletContext.getInitParameter("locales");
    if (localesValue == null || localesValue.isEmpty()) {
      LOGGER.warn("'locales' init parameter is empty, the default locale will be used");
    } else {
      List<String> locales = new ArrayList<>();
      StringTokenizer st = new StringTokenizer(localesValue);
      while (st.hasMoreTokens()) {
        String localeName = st.nextToken();
        locales.add(localeName);
      }

      LOGGER.debug("Application attribute set: locales --> {}", locales);
      servletContext.setAttribute("locales", locales);
    }

    LOGGER.debug("I18N subsystem initialization finished");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    LOGGER.debug("Servlet context destruction starts");
    // do nothing
    LOGGER.debug("Servlet context destruction finished");
  }
}
