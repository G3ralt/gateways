package server;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import entity.Gateway;
import entity.PeripheralDevice;
import facades.FacadeFactory;
import facades.GatewayFacade;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Date;
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
    private static final GatewayFacade GATEWAY_FACADE;

    static {
        LOG = Logger.getLogger(ServerContextConfiguration.class.getName());
        GATEWAY_FACADE = FacadeFactory.getGatewayFacade();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("BEGIN INITIALIZING APPLICATION - musalasoft");
        PeripheralDevice pd = new PeripheralDevice(3244l, "Initial vendor", new Date(), true);
        Gateway gw = null;
        try {
            gw = new Gateway("123456789", "Initial Gateway", "69.69.69.69");
            gw.addDevice(pd);
        } catch (Exception ex) {
            System.out.println("FAIL initialing data");
        }

        GATEWAY_FACADE.createGateway(gw);
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
