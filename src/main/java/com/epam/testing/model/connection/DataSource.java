package com.epam.testing.model.connection;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * DataSource configuration
 * Uses webapp/META-INF/context.xml for configuration
 *
 * @author rom4ik
 */

public class DataSource {
    private static final Logger LOGGER = LogManager.getLogger(DataSource.class);
    private static javax.sql.DataSource ds;
    private static final DataSource instance;

    static {
        LOGGER.debug("Datasource initialization starts");
        try {
            Context initContext = new InitialContext();
            ds = (javax.sql.DataSource)initContext.lookup("java:/comp/env/jdbc/web_testing");
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        instance = new DataSource();
        LOGGER.debug("Datasource initialization finished");
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private DataSource() {}

    /**
     * Singleton.
     */
    public static synchronized DataSource getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}