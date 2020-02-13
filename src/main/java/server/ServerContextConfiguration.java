package server;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class is used for initializing and destroying the context
 */
@WebListener
public class ServerContextConfiguration implements ServletContextListener {

    private static final Logger LOG;

    static {
        LOG = Logger.getLogger(ServerContextConfiguration.class.getName());
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("BEGIN INITIALIZING APPLICATION - musalasoft");

        LOG.info("FINISHED INITIALIZING APPLICATION - musalasoft");
    }

    /**
     * Deregisters JDBC driver and shuts down a thread
     *
     * @param sce the Servlet Context object
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("BEGIN DESTROYING CONTEXT - musalasoft");

        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = (Driver) drivers.nextElement();

            try {

                DriverManager.deregisterDriver(driver);
                AbandonedConnectionCleanupThread.checkedShutdown();

            } catch (Exception e) {

                LOG.log(Level.SEVERE, "ERROR: {0}", e.toString());
                e.printStackTrace();
            }
        }

        LOG.info("FINISHED DESTROYING CONTEXT - musalasoft");
    }

}
