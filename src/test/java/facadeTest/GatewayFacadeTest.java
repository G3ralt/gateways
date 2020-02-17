package facadeTest;

import entity.Gateway;
import entity.PeripheralDevice;
import facades.FacadeFactory;
import facades.GatewayFacade;
import java.util.Date;
import javax.persistence.NoResultException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GatewayFacadeTest {

    private static final GatewayFacade GATEWAY_FACADE;
    private static Gateway gateway;
    private static PeripheralDevice device;

    static {
        GATEWAY_FACADE = FacadeFactory.getGatewayFacade();
    }

    public GatewayFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        gateway = new Gateway("123456789", "Test gateway", "1.1.1.1");
        device = new PeripheralDevice(696969l, "Vendor", new Date(), true);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void t1_testFF() {
        assertNotNull(GATEWAY_FACADE.getEM());
    }

    @Test
    public void t2_testCreateGateway() {
        try {
            GATEWAY_FACADE.createGateway(gateway);
            assertNotNull(gateway.getId());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void t3_testGetAllGateway() {
        assertFalse(GATEWAY_FACADE.getAllGateways().isEmpty());
    }

    @Test
    public void t4_testGetGatewayById() {
        try {
            assertEquals(gateway, GATEWAY_FACADE.getGatewayById(gateway.getId()));
        } catch (NoResultException e) {
            fail("No result from DB!");
        }
    }

    @Test
    public void t5_testAddDevice() {
        try {
            assertTrue(gateway.addDevice(device));
            assertFalse(gateway.addDevice(device));

        } catch (Exception ex) {
            fail(ex.toString());
        }
    }

    @Test
    public void t6_testGatewayCollectionSize() {
        try {
            for (int i = 0; i < 15; i++) {
                gateway.addDevice(new PeripheralDevice(((Integer) i).longValue(), "Vendor", new Date(), true));
            }
        } catch (Exception exception) {
            assertEquals("Can't add more than 10 devices to a gateway!", exception.getMessage());
        }
    }

    @Test
    public void t7_testRemoveDevice() {
        assertTrue(gateway.removeDevice(device));
        assertFalse(gateway.removeDevice(device));
    }

}
